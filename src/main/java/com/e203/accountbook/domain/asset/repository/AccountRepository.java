package com.e203.accountbook.domain.asset.repository;

import com.e203.accountbook.domain.asset.entity.Account;
import com.e203.accountbook.domain.asset.entity.HideState;
import com.e203.accountbook.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByIdAndUserId(Long accountId, Long userId);

    @Query("SELECT a FROM Account a JOIN FETCH a.user u WHERE a.accountNumber = :accountNumber")
    Optional<Account> findByAccountNumber(String accountNumber);
    Optional<Account> findByAccountNumberAndUser(String accountNumber, User user);

    @Query("SELECT a FROM Account a Join Fetch a.user u " +
            "WHERE a.user.id = :userId " +
            "AND a.hideState IN :hideStates")
    List<Account> findAllByUser_IdAndHideState(Long userId, List<HideState> hideStates);

    @Query("SELECT a FROM Account a JOIN FETCH a.user u WHERE u.id = :userId AND a.hideState = :hideState")
    List<Account> findAllByUser_IdAndHideState(Long userId, HideState hideState);

    @Query("SELECT a FROM Account a JOIN FETCH a.user u WHERE u.id = :userId")
    List<Account> findAllByUser_Id(Long userId);

}
