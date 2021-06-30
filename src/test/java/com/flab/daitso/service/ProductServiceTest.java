package com.flab.daitso.service;

import com.flab.daitso.dto.product.ProductDto;
import com.flab.daitso.error.exception.product.NotFoundException;
import com.flab.daitso.mapper.ProductMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class ProductServiceTest {

    @Autowired
    ProductMapper productMapper;

    @Autowired
    ProductService productService;

    @Test
    @DisplayName("상품_등록_테스트")
    public void 상품_등록_테스트() {
        ProductDto productDto = new ProductDto.Builder()
                .categoryId(1)
                .name("test")
                .price(10000)
                .content("test 상품입니다.")
                .build();

        productService.registerProduct(productDto);
        ProductDto product = productService.findByName("test");
        assertThat(product.getName()).isEqualTo(productDto.getName());
    }

    @Test
    @DisplayName("해당 카테고리에 속하는 모든 상품 목록을 제대로 검색하는지 테스트")
    public void 카테고리별_상품_목록_테스트() {
        ProductDto productDto1 = new ProductDto.Builder()
                .categoryId(1)
                .name("test")
                .price(10000)
                .content("test 상품입니다.")
                .build();

        ProductDto productDto2 = new ProductDto.Builder()
                .categoryId(1)
                .name("test1")
                .price(20000)
                .content("test1 상품입니다.")
                .build();

        productService.registerProduct(productDto1);
        productService.registerProduct(productDto2);

        List<ProductDto> productAll = productService.findProductAll(1);
        assertThat(productAll.size()).isEqualTo(2);
        assertThat(productAll.get(0).getName()).isEqualTo("test");
    }

    @Test
    @DisplayName("상품 아이디로 해당 상품을 제대로 검색하는지 테스트")
    public void 상품_아이디로_검색_테스트() {
        ProductDto productDto = new ProductDto.Builder()
                .categoryId(1)
                .name("test1")
                .price(20000)
                .content("test1 상품입니다.")
                .build();
        productService.registerProduct(productDto);

        // 상품 아이디를 이용해 상품을 제대로 검색하는지 테스트
        ProductDto product = productService.findProductById(1L);
        assertThat(product.getPid()).isEqualTo(1L);
    }

    @Test
    @DisplayName("상품 아이디로 해당 상품을 제대로 검색하는지 테스트")
    public void 상품_이름으로_검색_테스트() {
        ProductDto productDto = new ProductDto.Builder()
                .categoryId(1)
                .name("test1")
                .price(20000)
                .content("test1 상품입니다.")
                .build();
        productService.registerProduct(productDto);

        ProductDto product = productService.findByName("test1");
        assertThat(product.getName()).isEqualTo(productDto.getName());
    }

    @Test
    @DisplayName("없는 상품 아이디로 검색했을 때 테스트")
    public void 존재하지_않는_상품_테스트() {
        assertThrows(NotFoundException.class, () -> productService.findProductById(-2L));
    }
}
