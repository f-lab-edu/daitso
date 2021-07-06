package com.flab.daitso.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.daitso.dto.product.Category;
import com.flab.daitso.dto.product.ProductDto;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@Transactional
@AutoConfigureMockMvc
@SpringBootTest
public class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    ProductService productService;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    @DisplayName("상품 등록 테스트")
    public void 상품_등록_테스트() throws Exception {
        ProductDto productDto = new ProductDto.Builder()
                .name("test1")
                .price(10000L)
                .content("test 상품입니다.")
                .build();

        mvc.perform(post("/manage/register")
                .content(objectMapper.writeValueAsString(productDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("카테고리별 상품 목록 조회 테스트")
    public void 카테고리별_상품_목록_조회_테스트() throws Exception {
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

        mvc.perform(get("/np/categories/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }


    @Test
    @DisplayName("상품 아이디 해당 상품을 제대로 검색하는지 테스트")
    public void 상품_아이디로_검색_테스트() throws Exception {
        ProductDto productDto = new ProductDto.Builder()
                .name("test1")
                .price(20000L)
                .content("test1 상품입니다.")
                .build();
        productService.registerProduct(productDto);

        ProductDto product = productService.findProductByName("test1");

        mvc.perform(get("/vp/products/" + product.getProductId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }


    @Test
    @DisplayName("상품 아이디 해당 상품을 제대로 검색하는지 테스트")
    public void 존재하지_않는_상품_테스트() throws Exception {
        mvc.perform(get("/vp/products/" + -1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("NotFoundException"))
                .andDo(print());
    }

    @Test
    @DisplayName("상품 아이디로 상품 삭제")
    public void 상품_아이디로_상품_삭제() throws Exception {
        ProductDto product = new ProductDto.Builder()
                .name("test1")
                .price(20000L)
                .content("test1 상품입니다.")
                .build();
        productService.registerProduct(product);

        mvc.perform(delete("/manage/delete/" + product.getProductId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
