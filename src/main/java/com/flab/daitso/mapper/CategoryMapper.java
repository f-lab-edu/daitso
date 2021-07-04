package com.flab.daitso.mapper;

import com.flab.daitso.dto.product.Category;
import com.flab.daitso.dto.product.ProductDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface CategoryMapper {

    Long saveRootCategory(Category category);

    Long saveChildCategory(HashMap<String, Object> map);

    Category findByCategoryId(Long categoryId);

    /**
     * 카테고리 이름으로 product list 반환
     */
    List<ProductDto> findCategoryListByName(String name);

    Category findByName(String name);

}
