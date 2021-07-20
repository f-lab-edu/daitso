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

    void removeCategory(Long categoryId);

    Category findByCategoryId(Long categoryId);

    List<ProductDto> findProductListByCategoryId(Long productId);

    Category findByName(String name);

}
