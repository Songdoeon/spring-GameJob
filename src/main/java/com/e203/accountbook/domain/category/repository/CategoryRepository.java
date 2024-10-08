package com.e203.accountbook.domain.category.repository;

import com.e203.accountbook.domain.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("select c from Category c where c.user.id in (0, :userId)")
    List<Category> findAllByUser_Id(Long userId);

    @Query("select c from Category c where c.user.id in (0, :userId) and c.name = :name")
    Optional<Category> findByNameAndUser_Id(String name, Long userId);

    @Query("SELECT count(c) FROM Category c WHERE c.id IN :categoryIds AND (c.type = 'DEFAULT' OR c.user.id = :userId)")
    int countCategoriesByUserIdOrDefault(@Param("categoryIds") List<Long> categoryIds, @Param("userId") Long userId);

    @Query("SELECT c FROM Category c WHERE c.id IN :categoryIds")
    Optional<List<Category>> findByIds(@Param("categoryIds")List<Long> categoryIds);
}
