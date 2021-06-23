package com.flab.daitso.mapper;

import com.flab.daitso.dto.product.ProductDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface ProductMapper {
    List<ProductDto> getProducts(int id);

    ProductDto getProduct(String productName);

    void registerProduct(ProductDto productDto);

    void deleteProduct(String productName);

    void increaseQuantity(String productName);
}
