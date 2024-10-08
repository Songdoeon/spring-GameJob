package com.e203.accountbook.domain.category.entity;

import com.e203.accountbook.domain.category.dto.CategoryRequest;
import com.e203.accountbook.domain.category.dto.CategoryResponse;
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
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
public class Category{
    @Id
    @GenericGenerator(name ="snowflake_id", strategy = "com.e203.accountbook.global.common.SnowFlakeGenerator")
    @GeneratedValue(generator = "snowflake_id")
    @Column(name = "category_id")
    private Long id;

    @Column(name = "category_name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryType type;

    @Column(name = "image_number", nullable = false)
    private Integer imageNumber;

    public static CategoryResponse toResponse(Category category) {
        return CategoryResponse.builder()
                .categoryId(category.getId())
                .name(category.getName())
                .imageNumber(category.getImageNumber())
                .categoryType(category.getType())
                .build();
    }

    public void update(CategoryRequest categoryRequest){
        this.name = categoryRequest.getCategoryName();
        this.imageNumber = categoryRequest.getImageNumber();
    }
}
