package com.flab.daitso.mapper;

import com.flab.daitso.dto.product.ProductDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper {

    ProductDto findProductByName(String name);

    // 상품을 상품 id로 찾는다.
    ProductDto findProductById(Long productId);

    // 새 상품을 dto 째로 insert 한다.
    void register(ProductDto productDto);

    // 하나의 상품을 상품아이디로 삭제한다.
    void delete(Long productId);

    void saveProductInCategory(@Param("categoryId") Long categoryId, @Param("productId") Long productId);

    List<ProductDto> findProductListByScoreRange(@Param("categoryId") Long categoryId, @Param("score") Long score);

    List<ProductDto> findProductListByPriceRange(@Param("categoryId") Long categoryId, @Param("minPrice") Long minPrice, @Param("maxPrice") Long maxPrice);

    List<ProductDto> findProductListByLatestOrder(Long categoryId);

}

