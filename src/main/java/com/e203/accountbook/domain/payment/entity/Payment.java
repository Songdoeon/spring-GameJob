package com.e203.accountbook.domain.payment.entity;


import com.e203.accountbook.domain.asset.entity.Card;
import com.e203.accountbook.domain.category.entity.Category;
import com.e203.accountbook.domain.user.entity.User;
import com.e203.accountbook.global.common.BaseTime;
import com.e203.accountbook.global.ssafyapi.payment.dto.account.MyPaymentAccountDto;
import com.e203.accountbook.global.ssafyapi.payment.dto.card.MyPaymentCardDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "payments")
public class Payment extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false)
    private Long assetId;

    @Column(name = "payment_name")
    private String name;

    private String cardIssuerName;

    private String merchantName;

    private String memo;

    @Column(nullable = false)
    private LocalDateTime paymentTime;

    @Column(nullable = false)
    private Long balance;

    private Long transactionUniqueNo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentState state;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentAsset asset;

    public void modifyCategory(Category category){
        this.category = category;
    }

    public void modifyState(PaymentState state){
        this.state = state;
    }

    public void modifyMemo(String memo){
        this.memo = memo;
    }

    public static Payment cardOf(Card card, MyPaymentCardDto dto, Long userId, Category category){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime localDateTime = LocalDateTime.parse(dto.getTransactionDate() + dto.getTransactionTime(), formatter);

        return Payment.builder()
                .user(User.builder().id(userId).build())
                .category(category)
                .merchantName(dto.getMerchantName())
                .name(card.getName())
                .balance(dto.getTransactionBalance())
                .transactionUniqueNo(dto.getTransactionUniqueNo())
                .paymentTime(localDateTime)
                .cardIssuerName(card.getIssuer().name())
                .state(PaymentState.INCLUDE)
                .type(PaymentType.EXPENSE)
                .assetId(card.getId())
                .asset(PaymentAsset.CARD)
                .build();
    }
    public static Payment accountOf(Long assetId, MyPaymentAccountDto dto, Long userId, Category category, String name) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime localDateTime = LocalDateTime.parse(dto.getTransactionDate() + dto.getTransactionTime(), formatter);

        return Payment.builder()
                .user(User.builder().id(userId).build())
                .category(category)
                .assetId(assetId)
                .asset(PaymentAsset.ACCOUNT)
                .merchantName(name)
                .balance(dto.getTransactionBalance())
                .memo(dto.getTransactionMemo())
                .transactionUniqueNo(dto.getTransactionUniqueNo())
                .paymentTime(localDateTime)
                .state(PaymentState.INCLUDE)
                .type(PaymentType.fromString(dto.getTransactionTypeName()))
                .build();
    }
}
