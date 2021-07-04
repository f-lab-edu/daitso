package com.flab.daitso.service;

import com.flab.daitso.dto.product.Category;
import com.flab.daitso.dto.product.ProductDto;
import com.flab.daitso.error.exception.product.DuplicateProductNameException;
import com.flab.daitso.error.exception.product.NotFoundException;
import com.flab.daitso.mapper.ProductMapper;
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
    ProductMapper productMapper;

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Test
    @DisplayName("상품_등록_테스트")
    public void 상품_등록_테스트() {
        ProductDto productDto = new ProductDto.Builder()
                .name("test")
                .price(10000)
                .content("test 상품입니다.")
                .build();

        Long saveProductId = productService.registerProduct(productDto);
        ProductDto product = productService.findProductById(saveProductId);
        assertThat(saveProductId).isEqualTo(product.getProductId());
    }

    @Test
    @DisplayName("이미 존재하는 상품 테스트")
    public void 존재하는_상품_테스트() {
        ProductDto productDto = new ProductDto.Builder()
                .name("test")
                .price(10000)
                .content("test 상품입니다.")
                .build();
        productService.registerProduct(productDto);

        ProductDto productDto1 = new ProductDto.Builder()
                .name("test")
                .price(10000)
                .content("test 상품입니다.")
                .build();
        assertThrows(DuplicateProductNameException.class, () -> productService.registerProduct(productDto1));
    }

    @Test
    @DisplayName("상품에 카테고리 지정")
    public void 상품에_카테고리_지정_테스트() {
        ProductDto productDto1 = new ProductDto.Builder()
                .name("test")
                .price(10000)
                .content("test 상품입니다.")
                .build();

        productService.registerProduct(productDto1);

        Category sportsCategory = new Category("sports");
        categoryService.saveCategory(sportsCategory);

        Category soccerCategory = new Category("soccer");
        sportsCategory.addChildCategory(soccerCategory);

        Long categoryId = categoryService.saveCategory(soccerCategory);
        Category category = productService.saveProductInCategory(categoryId, productDto1.getProductId());

        System.out.println("##" + category.getProducts().get(0).getProductId());

        assertThat(category.getProducts().get(0).getProductId()).isEqualTo(productDto1.getProductId());
    }



//    @Test
//    @DisplayName("상품 아이디로 해당 상품을 제대로 검색하는지 테스트")
//    public void 상품_아이디로_검색_테스트() {
//        ProductDto productDto = new ProductDto.Builder()
//                .categoryId(1)
//                .name("test1")
//                .price(20000)
//                .content("test1 상품입니다.")
//                .build();
//        productService.registerProduct(productDto);
//
//        ProductDto product = productService.findProductByName("test1");
//
//        // 상품 아이디를 이용해 상품을 제대로 검색하는지 테스트
//        ProductDto findProduct = productService.findProductById(product.getPid());
//        assertThat(product.getPid()).isEqualTo(findProduct.getPid());
//    }

    @Test
    @DisplayName("상품 이름로 해당 상품을 제대로 검색하는지 테스트")
    public void 상품_이름으로_검색_테스트() {
        ProductDto productDto = new ProductDto.Builder()
                .name("test1")
                .price(20000)
                .content("test1 상품입니다.")
                .build();
        productService.registerProduct(productDto);

        ProductDto product = productService.findProductByName("test1");
        assertThat(product.getName()).isEqualTo(productDto.getName());
    }

    @Test
    @DisplayName("없는 상품 아이디로 검색했을 때 테스트")
    public void 존재하지_않는_상품_테스트() {
        assertThrows(NotFoundException.class, () -> productService.findProductById(-2L));
    }
}
