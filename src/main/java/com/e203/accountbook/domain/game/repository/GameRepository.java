package com.e203.accountbook.domain.game.repository;

import com.e203.accountbook.domain.game.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<Game> findByUserIdAndId(Long userId, Long gameId);
}
