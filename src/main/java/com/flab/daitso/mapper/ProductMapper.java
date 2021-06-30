package com.flab.daitso.mapper;

import com.flab.daitso.dto.product.ProductDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ProductMapper {

    // 특정 카테고리에 속하는 모든 상품을 카테고리 아이디를 사용해 찾는다.
    List<ProductDto> findProductsByCategoryId(int categoryId);

    // 상품을 상품 id로 찾는다.
    ProductDto findProductById(Long pid);

    // 상품을 카테고리 아이디와 상품이름 두가지를 매칭하여 찾아온다.
    ProductDto findProductByCategoryIdAndName(@Param("categoryId") int categoryId, @Param("productName") String productName);

    // 새 상품을 dto 째로 insert 한다.
    void registerProduct(ProductDto productDto);

    // 하나의 상품을 상품아이디로 삭제한다.
    void deleteProduct(Long pid);

    // 해당 상품 아이디에 해당하는 상품의 수량을 1 증가시킨다.
    void increaseQuantity(Long pid);
}

