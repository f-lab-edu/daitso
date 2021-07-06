package com.flab.daitso.controller;

import com.flab.daitso.dto.product.ProductDto;
import com.flab.daitso.service.ProductService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * 특정 상품을 등록하는 기능
     * @param productDto 상품 정보
     */
    @PostMapping("/manage/register")
    public void registerProduct(@RequestBody @Valid ProductDto productDto) {
        productService.registerProduct(productDto);
    }

    /**
     * 하나의 특정 상품을 상품명으로 검색하는 기능
     * @param productId 상품아이디
     */
    @GetMapping("/vp/products/{productId}")
    public ProductDto searchById(@PathVariable Long productId) {
        return productService.findProductById(productId);
    }

    /**
     * 특정 상품을 목록에서 삭제하는 기능
     * @param productId 상품아이디
     */
    @DeleteMapping("/manage/delete/{productId}")
    public void deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
    }

    @GetMapping("/manage/product/category/{categoryId}")
    public void registerProductInCategory(@PathVariable Long categoryId, @RequestBody List<ProductDto> products) {
        productService.saveProductInCategory(categoryId, products);
    }
}
