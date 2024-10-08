package com.e203.accountbook.domain.game.entity;

import com.e203.accountbook.domain.asset.entity.Account;
import com.e203.accountbook.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "participants",
        uniqueConstraints = {
                @UniqueConstraint(
                        name="PARTICIPANT_UNIQUE",
                        columnNames={"game_id","user_id"}
                )})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class Participant {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "participant_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private ParticipantStatus status = ParticipantStatus.WAITING;

    @Builder.Default
    private int count = 0;

    public void updateCount(int count){
        this.count = count;
    }
    public void acceptParticipant(Account account) {
        this.account = account;
        status = ParticipantStatus.JOINER;
    }

    public void declineParticipant(){
        this.account = null;
        this.count = 0;
        this.status = ParticipantStatus.DECLINER;
    }
}
