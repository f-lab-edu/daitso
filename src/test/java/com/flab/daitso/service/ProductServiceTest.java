package com.flab.daitso.service;


import com.flab.daitso.dto.product.ProductDto;
import com.flab.daitso.error.exception.product.NotFoundException;
import com.flab.daitso.error.exception.product.SoldOutException;
import com.flab.daitso.mapper.ProductMapper;
import com.flab.daitso.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class ProductServiceTest {

    @Autowired
    ProductService productService;

    @Autowired
    ProductMapper productMapper;

    @Test
    @DisplayName("해당 카테고리에 속하는 모든 상품 목록을 제대로 검색하는지 테스트")
    public void getProducts() throws Exception {
        // ?? 어떻게 태스트함 뭔 기준??
    }

    @Test
    @DisplayName("상품 아이디로 해당 상품을 제대로 검색하는지 테스트")
    public void getProduct() throws Exception {

        // 상품 아이디를 이용해 상품을 제대로 검색하는지 테스트
        ProductDto product = productService.getProduct(4);
        assertThat(product.getPid()).isEqualTo(4);

        // 없는 상품 아이디로 검색했을때 NotFoundException 을 제대로 반환 하는지 테스트
        assertThrows(NotFoundException.class, () -> productService.getProduct(-2));

        // 품절된 상품을 검색했을때 SoldOutException 을 제대로 반환 하는지 테스트
        assertThrows(SoldOutException.class, () -> productService.getProduct(5));

    }

    @Test
    @DisplayName("하나의 상품을 제대로 등록하는지 테스트")
    public void registerProduct() throws Exception{

    }

}