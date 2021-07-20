package com.flab.daitso.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.daitso.dto.product.Category;
import com.flab.daitso.dto.product.ProductDto;
import com.flab.daitso.service.CategoryService;
import com.flab.daitso.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CategoryControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    @DisplayName("카테고리별 상품 목록 조회 테스트")
    public void 카테고리별_상품_목록_조회_테스트() throws Exception {
        List<ProductDto> products1 = new ArrayList<>();
        List<ProductDto> products2 = new ArrayList<>();

        Category carGoods = new Category("car goods");
        Category interior = new Category("interior");
        Category seat = new Category("seat");

        carGoods.addChildCategory(interior);
        carGoods.addChildCategory(seat);

        Long carGoodsId = categoryService.saveCategory(carGoods);
        Long interiorId = categoryService.saveCategory(interior);
        Long seatId = categoryService.saveCategory(seat);

        ProductDto productDto1 = new ProductDto.Builder()
                .name("test1")
                .price(10000L)
                .content("test 상품입니다.")
                .build();

        ProductDto productDto2 = new ProductDto.Builder()
                .name("test2")
                .price(20000L)
                .content("test2 상품입니다.")
                .build();

        ProductDto productDto3 = new ProductDto.Builder()
                .name("test3")
                .price(30000L)
                .content("test3 상품입니다.")
                .build();

        productService.registerProduct(productDto1);
        productService.registerProduct(productDto2);
        productService.registerProduct(productDto3);

        products1.add(productDto1);
        products1.add(productDto2);
        products2.add(productDto3);

        productService.saveProductInCategory(interiorId, products1);
        productService.saveProductInCategory(seatId, products2);

        mvc.perform(get("/api/categories/" + interiorId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("별점으로 상품 리스트 반환")
    public void 별점으로_상품_리스트_반환() throws Exception {
        List<ProductDto> products1 = new ArrayList<>();
        List<ProductDto> products2 = new ArrayList<>();

        Category carGoods = new Category("car goods");
        Category interior = new Category("interior");
        Category seat = new Category("seat");

        carGoods.addChildCategory(interior);
        carGoods.addChildCategory(seat);

        Long carGoodsId = categoryService.saveCategory(carGoods);
        Long interiorId = categoryService.saveCategory(interior);
        Long seatId = categoryService.saveCategory(seat);

        ProductDto productDto1 = new ProductDto.Builder()
                .name("test1")
                .price(10000L)
                .content("test 상품입니다.")
                .build();

        ProductDto productDto2 = new ProductDto.Builder()
                .name("test2")
                .price(20000L)
                .content("test2 상품입니다.")
                .build();

        ProductDto productDto3 = new ProductDto.Builder()
                .name("test3")
                .price(30000L)
                .content("test3 상품입니다.")
                .build();

        productService.registerProduct(productDto1);
        productService.registerProduct(productDto2);
        productService.registerProduct(productDto3);

        products1.add(productDto1);
        products1.add(productDto2);
        products2.add(productDto3);

        productService.saveProductInCategory(interiorId, products1);
        productService.saveProductInCategory(seatId, products2);

        mvc.perform(get("/api/categories/" + interiorId)
                .param("score", "0")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
