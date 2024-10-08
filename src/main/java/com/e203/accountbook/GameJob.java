package com.e203.accountbook;

import com.e203.accountbook.domain.alarm.Alarm;
import com.e203.accountbook.domain.alarm.TypeStatus;
import com.e203.accountbook.domain.alarm.repository.JpaAlarmRepository;
import com.e203.accountbook.domain.game.entity.Game;
import com.e203.accountbook.domain.game.entity.GameStatus;
import com.e203.accountbook.domain.game.entity.Participant;
import com.e203.accountbook.domain.game.entity.ParticipantStatus;
import com.e203.accountbook.domain.game.repository.ParticipantCategoryRepository;
import com.e203.accountbook.domain.game.repository.ParticipantRepository;
import com.e203.accountbook.domain.payment.repository.PaymentRepository;
import com.e203.accountbook.domain.payment.service.PaymentAsyncService;
import com.e203.accountbook.domain.user.entity.User;
import com.e203.accountbook.global.alarm.dto.FcmSendDto;
import com.e203.accountbook.global.alarm.service.FcmService;
import com.e203.accountbook.global.ssafyapi.remittance.service.TransferService;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.time.format.DateTimeFormatter.ofPattern;

@RequiredArgsConstructor
@Configuration
@Slf4j
public class GameJob {
    public static final String TITLE = "내기 종료";
    public static final String MESSAGE = "종료된 내기가 있습니다!";
    private final int CHUNK_SIZE = 3;

    @Value("${ssafys.admin.account}")
    private String adminAccount;
    @Value("${ssafys.admin.userKey}")
    private String adminUserKey;

    private final ParticipantRepository participantRepository;
    private final EntityManagerFactory entityManagerFactory;
    private final PaymentAsyncService paymentAsyncService;
    private final ParticipantCategoryRepository participantCategoryRepository;
    private final PaymentRepository paymentRepository;
    private final FcmService fcmService;
    private final TransferService transferService;
    private final JpaAlarmRepository alarmRepository;

    @Bean
    public Job job(JobRepository jobRepository, Step step1) {

        return new JobBuilder("gameJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step1)
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {

        return new StepBuilder("step1", jobRepository)
                .<Game, Game>chunk(CHUNK_SIZE, transactionManager)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .build();
    }

    @Bean
    public ItemWriter<? super Game> itemWriter() {
        log.info("writer 야호");
        return new JpaItemWriterBuilder<>()
                .entityManagerFactory(entityManagerFactory)
                .usePersist(false)
                .clearPersistenceContext(true)
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<Game> itemReader() {
        log.info("reader 야호");
        Map<String, Object> params = new HashMap<>();
        params.put("gameStatus", GameStatus.ING);

        return new JpaPagingItemReaderBuilder<Game>()
                .name("itemReader")
                .pageSize(CHUNK_SIZE)
                .queryString("select g " +
                        "from Game g " +
                        "where g.gameStatus = :gameStatus")
                .parameterValues(params)
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    public ItemProcessor<? super Game, ? extends Game> itemProcessor(){
        log.info("processor 야호");
        return (ItemProcessor<Game, Game>) game -> {
            log.info("게임하나 처리중..");
            Long gameId = game.getId();

            LocalDateTime startDate = game.getStartDate().atStartOfDay();
            LocalDateTime endDate = game.getEndDate().atStartOfDay();

            // 게임에 참여한 참여자 검색
            participantRepository.findByGame(gameId).forEach(participant -> {
                Long userId = participant.getUser().getId();
                Long participantId = participant.getId();

                if(participant.getStatus() == ParticipantStatus.OWNER ||
                    participant.getStatus() == ParticipantStatus.JOINER){
                    paymentAsyncService.update(userId);

                    // 사용자별 커스텀 카테고리
                    List<Long> categoryList = participantCategoryRepository.findByParticipantId(participantId)
                            .stream()
                            .map(c -> c.getCategory().getId())
                            .toList();

                    // 카테고리별 횟수 검색
                    participant.updateCount(paymentRepository.countByCategoryId(categoryList, startDate, endDate));
                }

            });

            // 끝나는날 로직
            log.info("endDay!!!! : {} ",LocalDate.now().atStartOfDay().minusDays(1));
            if(endDate.isEqual(LocalDate.now().atStartOfDay().minusDays(1))){
                log.info("끝나는 게임 하나 발견!!");
                List<Participant> participants = participantRepository.findByGame(gameId);

                Participant winnerInfo = participants.getFirst();

                // 우승자 송금
                transferService.transfer(adminUserKey, winnerInfo.getAccount().getAccountNumber(), adminAccount,
                        (long) participantRepository.countParticipantByGameExcludeDecliner(game) * game.getFee());

                for (Participant participant : participants) {
                    FcmSendDto fcmSendDto = FcmSendDto.builder()
                            .title("내기결과")
                            .body("내기결과가 나왔습니당")
                            .token(participant.getUser().getFcmTokenKey())
                            .build();
                    fcmService.sendMessageTo(fcmSendDto);

                    User user = participant.getUser();

                    Alarm alarm = Alarm.of(user.getId(), TITLE, MESSAGE, TypeStatus.GAME_RESULT, participant.getId());

                    alarmRepository.save(alarm);
                }
                game.endGame();
            }

            return game;
        };
    }
}
