package com.flab.daitso.controller;

import com.flab.daitso.dto.product.ProductDto;
import com.flab.daitso.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(path="daitso/product/category")
public class ProductController {

    @Autowired
    ProductService productService;

    /**
     * 하나의 특정 카테고리에 속한 모든 상품 검색 기능
     * @param categoryId 카테고리 아이디
     */
    @GetMapping(path = "{categoryId}")
    public List<ProductDto> getProducts(@PathVariable int categoryId) throws Exception {
        return productService.getProducts(categoryId);
    }

    /**
     * 하나의 특정 상품을 상품명으로 검색하는 기능
     * @param productName 상품명
     */
    @GetMapping(path = "/find")
    public ProductDto getProduct(@RequestParam String productName) throws Exception {
        return productService.getProduct(productName);
    }

    /**
     * 특정 상품을 등록하는 기능
     * @param productDto 상품 정보
     */
    @PostMapping
    public void registerProduct(@Valid @RequestBody ProductDto productDto) throws Exception {
        System.out.println(productDto.toString());
        productService.registerProduct(productDto);
    }

    /**
     * 특정 상품을 등록하는 기능
     * @param productName 상품명
     */
    @DeleteMapping(path = "/delete")
    public void deleteProduct(@RequestParam String productName) throws Exception {
        productService.deleteProduct(productName);
    }

}
