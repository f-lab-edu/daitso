package com.flab.daitso.mapper;

import com.flab.daitso.dto.product.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

@Mapper
public interface CategoryMapper {

    Long saveRootCategory(Category category);

    Long saveChildCategory(HashMap<String, Object> map);

    void removeCategory(Long categoryId);

    Category findByCategoryId(Long categoryId);

    Category findByName(String name);

}
