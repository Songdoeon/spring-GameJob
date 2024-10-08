package com.e203.accountbook.domain.game.repository;

import com.e203.accountbook.domain.game.entity.Game;
import com.e203.accountbook.domain.game.entity.Participant;
import com.e203.accountbook.domain.game.entity.ParticipantStatus;
import com.e203.accountbook.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    Optional<Participant> findByGameAndUser(Game game, User user);
    Optional<Participant> findByIdAndUserId(Long id, Long userId);
    Optional<Participant> findByIdAndUserIdAndStatus(Long id, Long userId, ParticipantStatus status);


    @Query("SELECT p FROM Participant p " +
            "JOIN FETCH p.game g " +
            "JOIN FETCH p.user u " +
            "WHERE g.id = :gameId " +
            "ORDER BY p.count DESC")
    List<Participant> findByGame(Long gameId);

    @Query("select p from Participant p where p.game = :game and p.status != :status")
    List<Participant> findByGameExcludeStatus(@Param("game") Game game, @Param("status") ParticipantStatus status);

    @Query("SELECT p FROM Participant p WHERE p.game = :game AND (p.status = com.e203.accountbook.domain.game.entity.ParticipantStatus.JOINER or p.status = com.e203.accountbook.domain.game.entity.ParticipantStatus.OWNER)")
    List<Participant> findByGameAndStatusIn(@Param("game") Game game);

    @Query("SELECT p FROM Participant p WHERE p.game = :game AND p.status = :status")
    List<Participant> findByGameAndStatus(@Param("game") Game game, @Param("status") ParticipantStatus participant);

    @Query("SELECT count(p) FROM Participant p WHERE p.game = :game AND p.status != com.e203.accountbook.domain.game.entity.ParticipantStatus.DECLINER")
    int countParticipantByGameExcludeDecliner(@Param("game") Game game);

}
