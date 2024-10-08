package com.e203.accountbook.domain.user.repository;

import com.e203.accountbook.domain.user.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    @Query("SELECT f FROM Friend f WHERE f.user.id = :userId")
    List<Friend> findAllByUserId(@Param("userId") Long userId);

    @Query("SELECT f.friendId FROM Friend f WHERE f.user.id = :userId")
    List<Long> findAllFriendIdByUserId(@Param("userId") Long userId);

    boolean existsByUserIdAndFriendId(Long userId, Long friendId);
}
