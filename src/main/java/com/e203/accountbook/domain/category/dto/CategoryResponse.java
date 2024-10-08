package com.e203.accountbook.domain.category.dto;

import com.e203.accountbook.domain.category.entity.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
    private long categoryId;
    private String name;
    private CategoryType categoryType;
    private Integer imageNumber;
}
