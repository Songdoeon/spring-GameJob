package com.e203.accountbook.domain.payment.service;

import com.e203.accountbook.domain.asset.entity.Account;
import com.e203.accountbook.domain.asset.entity.HideState;
import com.e203.accountbook.domain.asset.repository.AccountRepository;
import com.e203.accountbook.domain.asset.repository.CardRepository;
import com.e203.accountbook.domain.category.entity.Category;
import com.e203.accountbook.domain.payment.entity.Payment;
import com.e203.accountbook.domain.payment.repository.PaymentRepository;
import com.e203.accountbook.global.ssafyapi.payment.dto.account.ApiAccountPaymentRequest;
import com.e203.accountbook.global.ssafyapi.payment.dto.card.ApiCardPaymentRequest;
import com.e203.accountbook.global.ssafyapi.payment.service.ApiPaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Objects;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PaymentAsyncService {
    private final PaymentRepository paymentRepository;
    private final AccountRepository accountRepository;
    private final CardRepository cardRepository;
    private final ApiPaymentService apiPaymentService;

    @Value("${ssafys.admin.account}")
    private String adminAccount;

    public String update(Long userId) {
        log.info("test");
        LocalDate startDate = LocalDate.from(paymentRepository.findTopByUser_IdOrderByPaymentTimeDesc(userId)
                .map(Payment::getPaymentTime)
                .orElse(LocalDate.now().minusMonths(10).atStartOfDay()));
        log.info("startDate: {}", startDate);
        Category remittanceCategory = Category.builder().id(315172518223872L).build();
        log.info("userId : {}", userId);

        accountRepository.findAllByUser_IdAndHideState(userId, HideState.NONE)
                .forEach(account -> {
                    log.info("계좌 하나 나옴");
                    log.info("accountAccountNumber: {}", account.getAccountNumber());
                    apiPaymentService.getAccountPayment(ApiAccountPaymentRequest.of(account, startDate), account.getUser().getUserKey())
                            .getRec().getList().forEach(
                                    rep -> {
                                        log.info("accountAccountNumber: {}", rep.getTransactionAccountNo());
                                        if (!paymentRepository.existsByTransactionUniqueNo(rep.getTransactionUniqueNo())) {
                                            log.info(account.getUser().getName());
                                            log.info(account.getAccountNumber());
                                            log.info(rep.getTransactionAccountNo());
                                            Account acc = accountRepository.findByAccountNumber(rep.getTransactionAccountNo())
                                                    .orElse(Account.builder().accountNumber(rep.getTransactionAccountNo()).build());

                                            String name = "";

                                            if(Objects.equals(adminAccount, acc.getAccountNumber())){
                                                name = "돈그랑땡";
                                            }
                                            else name = acc.getUser().getName();
                                            paymentRepository.save(Payment.accountOf(account.getId(), rep, userId, remittanceCategory, name));
                                        }
                                    }
                            );
                        }
                );

        cardRepository.findAllByUser_IdAndHideState(userId, HideState.NONE)
                .forEach(card ->
                        apiPaymentService.getCardPayment(ApiCardPaymentRequest.of(card, startDate), card.getUser().getUserKey())
                                .getRec().getTransactionList().forEach(
                                        transaction -> {
                                            log.info("카드내역 하나 나옴");
                                            if(!paymentRepository.existsByTransactionUniqueNo(transaction.getTransactionUniqueNo())){
                                                paymentRepository.save(Payment.cardOf(card, transaction, userId, Category.builder().id(transaction.getCategoryId()).build()));
                                            }
                                        }
                                )
                );
        log.info("끝났어요");
        return "OK";
    }
}
