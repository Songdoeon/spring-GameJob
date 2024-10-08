package com.e203.accountbook.domain.game.entity;


import com.e203.accountbook.domain.category.entity.Category;
import com.e203.accountbook.domain.user.entity.User;
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

import java.time.LocalDate;

@Entity
@Table(name = "games")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    private LocalDate startDate;
    private LocalDate endDate;

    private int fee;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private GameStatus gameStatus = GameStatus.BEFORE_START;



    public void cancelGame(){
        this.gameStatus = GameStatus.CANCEL;
    }

    public void startGame(){
        this.gameStatus = GameStatus.ING;
    }

    public void endGame() {this.gameStatus = GameStatus.END;}
}
