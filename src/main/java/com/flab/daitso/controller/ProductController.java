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
     * 하나의 특정 카테고리에 속한 모든 상품 검색 기능
     * @param categoryId 카테고리 아이디
     */
    @GetMapping("/np/categories/{categoryId}")
    public List<ProductDto> searchByCategoryId(@PathVariable int categoryId) {
        return productService.findProductsByCategoryId(categoryId);
    }

    /**
     * 하나의 특정 상품을 상품명으로 검색하는 기능
     * @param pid 상품아이디
     */
    @GetMapping("/vp/products/{pid}")
    public ProductDto searchById(@PathVariable Long pid) {
        return productService.findProductById(pid);
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
     * 특정 상품을 목록에서 삭제하는 기능
     * @param pid 상품아이디
     */
    @DeleteMapping("/manage/delete/{pid}")
    public void deleteProduct(@PathVariable Long pid) {
        productService.deleteProduct(pid);
    }
}
