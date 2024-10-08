package com.e203.accountbook.domain.asset.entity;

import com.e203.accountbook.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "cards")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
@Getter
public class Card {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "card_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String nickname;

    private String name;

    @Column(unique = true)
    private String cardNumber;

    private String cvc;

    @Enumerated(EnumType.STRING)
    private Issuer issuer;

    @Enumerated(EnumType.STRING)
    private HideState hideState;

    @Enumerated(EnumType.STRING)
    private DepositState depositState;

    public void modifyHideState(HideState hideState) {
        this.hideState = hideState;
    }
    public void modifyNickname(String nickname) {
        this.nickname = nickname;
    }

}