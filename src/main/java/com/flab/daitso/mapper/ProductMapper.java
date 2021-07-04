package com.flab.daitso.mapper;

import com.flab.daitso.dto.product.ProductDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductMapper {

    ProductDto findProductByName(String name);

    // 상품을 상품 id로 찾는다.
    ProductDto findProductById(Long productId);

    // 새 상품을 dto 째로 insert 한다.
    void register(ProductDto productDto);

    // 하나의 상품을 상품아이디로 삭제한다.
    void delete(Long pid);

    // 해당 상품 아이디에 해당하는 상품의 수량을 1 증가시킨다.
    void increaseQuantity(Long pid);

    void saveProductInCategory(@Param("categoryId") Long categoryId, @Param("productId") Long productId);
}

