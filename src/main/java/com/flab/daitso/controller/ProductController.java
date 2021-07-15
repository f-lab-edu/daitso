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
    @PostMapping("/v2/providers/api/v1/seller-products")
    public void registerProduct(@RequestBody @Valid ProductDto productDto) {
        productService.registerProduct(productDto);
    }

    /**
     * 하나의 특정 상품을 상품명으로 검색하는 기능
     * @param productId 상품아이디
     */
    @GetMapping("/v2/providers/api/v1/seller-products/{productId}")
    public ProductDto searchById(@PathVariable Long productId) {
        return productService.findProductById(productId);
    }

    /**
     * 특정 상품을 목록에서 삭제하는 기능
     * @param productId 상품아이디
     */
    @DeleteMapping("/v2/providers/api/v1/seller-products/{productId}")
    public void deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
    }

    /*
    @GetMapping("/v2/providers/api/v1/seller-products/category/{categoryId}")
    public void registerProductInCategory(@PathVariable Long categoryId, @RequestBody List<ProductDto> products) {
        productService.saveProductInCategory(categoryId, products);
    }
     */

    @GetMapping("/np/categories/{categoryId}")
    public List<ProductDto> getProductListBySort(@PathVariable Long categoryId,
                                                 @RequestParam(value = "score", required = false, defaultValue = "0") Long score,
                                                 @RequestParam(value = "minPrice", required = false, defaultValue = "0") Long minPrice,
                                                 @RequestParam(value = "maxPrice", required = false, defaultValue = "0") Long maxPrice,
                                                 @RequestParam(value = "sorter", required = false, defaultValue = "0") Long sorter) {
        if (score > 0) {
            return getProductListByScoreRange(categoryId, score);
        }
        if (minPrice > 0 && (minPrice <= maxPrice)) {
            return getProductListByPriceRange(categoryId, minPrice, maxPrice);
        }
        if (sorter >= 0) {
            return getProductListByLatestOrder(categoryId, sorter);
        }
        return productService.findProductListByScoreRange(categoryId, 0L);
    }

    public List<ProductDto> getProductListByScoreRange(Long categoryId, Long score) {
        return productService.findProductListByScoreRange(categoryId, score);
    }

    public List<ProductDto> getProductListByPriceRange(Long categoryId, Long minPrice, Long maxPrice) {
        return productService.findProductListByPriceRange(categoryId, minPrice, maxPrice);
    }

    public List<ProductDto> getProductListByLatestOrder(Long categoryId, Long sorter) {
        return productService.findProductListByLatestOrder(categoryId, sorter);
    }
}
