package com.e203.accountbook.domain.asset.repository;

import com.e203.accountbook.domain.asset.entity.Card;
import com.e203.accountbook.domain.asset.entity.HideState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Card> findByIdAndUserId(Long cardId, Long userId);

    @Query("SELECT c FROM Card c JOIN FETCH c.user u WHERE u.id = :userId AND c.hideState = :hideState")
    List<Card> findAllByUser_IdAndHideState(Long userId, HideState hideState);

    @Query("SELECT c FROM Card c JOIN FETCH c.user u WHERE u.id = :userId " +
            "AND c.hideState IN :hideStates")
    List<Card> findAllByUser_IdByHideState(Long userId, List<HideState> hideStates);

    @Query("SELECT c FROM Card c JOIN FETCH c.user u WHERE u.id = :userId")
    List<Card> findAllByUser_Id(Long userId);
}
