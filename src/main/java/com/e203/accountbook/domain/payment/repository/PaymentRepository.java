package com.e203.accountbook.domain.payment.repository;

import com.e203.accountbook.domain.payment.entity.Payment;
import com.e203.accountbook.domain.payment.entity.PaymentAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("SELECT p FROM Payment p JOIN FETCH p.category c " +
            "JOIN FETCH p.user u " +
            "WHERE u.id = :userId " +
            "AND p.paymentTime >= :start AND p.paymentTime < :end " +
            "AND ((p.asset = 'CARD' AND p.assetId IN :cardList) " +
            "Or (p.asset = 'ACCOUNT' AND p.assetId IN :accountList))" +
            "ORDER BY p.paymentTime DESC")
    List<Payment> findListByUserId(@Param("userId") Long userId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end, @Param("cardList") List<Long> cardList, @Param("accountList") List<Long> accountList);

    Optional<Payment> findTopByUser_IdOrderByPaymentTimeDesc(Long userId);

    @Query("SELECT p FROM Payment p JOIN FETCH p.category c " +
            "JOIN FETCH p.user u " +
            "WHERE u.id = :userId " +
            "AND p.paymentTime >= :start AND p.paymentTime < :end " +
            "AND p.asset = :asset " +
            "AND p.assetId = :assetId " +
            "ORDER BY p.paymentTime DESC")
    List<Payment> findListByCard(@Param("userId") Long userId, @Param("asset") PaymentAsset asset, @Param("assetId") Long assetId,
                                 @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT p FROM Payment p JOIN FETCH p.category c " +
            "JOIN FETCH p.user u " +
            "WHERE u.id = :userId " +
            "AND p.asset = :asset " +
            "AND p.assetId = :assetId " +
            "ORDER BY p.paymentTime DESC")
    List<Payment> findListByAccount(@Param("userId") Long userId, @Param("asset") PaymentAsset asset, @Param("assetId") Long assetId);


    @Query("SELECT EXISTS (" +
            "    SELECT 1" +
            "    FROM Payment p" +
            "    where p.transactionUniqueNo = :transactionUniqueNo" +
            ")")
    boolean existsByTransactionUniqueNo(Long transactionUniqueNo);

    @Query("SELECT count(p) FROM Payment p " +
            "JOIN p.category c " +
            "JOIN p.user u " +
            "WHERE u.id = :userId " +
            "AND c.id IN :categoryList " +
            "AND p.paymentTime >= :start AND p.paymentTime <= :end " +
            "AND ((p.asset = 'CARD' AND p.assetId IN :cardList) " +
            "Or (p.asset = 'ACCOUNT' AND p.assetId IN :accountList))")
    int countByUserIdAndCategoryList(@Param("userId") Long userId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end, @Param("cardList") List<Long> cardList, @Param("accountList") List<Long> accountList, @Param("categoryList") List<Long> categoryList);

    @Query("SELECT count(p) FROM Payment p " +
            "JOIN p.category c " +
            "WHERE c.id IN :categoryList " +
            "AND p.paymentTime >= :start AND p.paymentTime < :end " +
            "AND c.id IN :categoryList")
    int countByCategoryId(List<Long> categoryList, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

}
