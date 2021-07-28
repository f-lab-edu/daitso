package com.flab.daitso.controller;

import com.flab.daitso.dto.product.Product;
import com.flab.daitso.service.ProductService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * 특정 상품을 등록하는 기능
     * @param product 상품 정보
     */
    @PostMapping
    public void registerProduct(@RequestBody @Valid Product product) {
        productService.registerProduct(product);
    }

    /**
     * 하나의 특정 상품을 상품명으로 검색하는 기능
     * @param productId 상품아이디
     */
    @GetMapping("/{productId}")
    public Product searchById(@PathVariable Long productId) {
        return productService.findProductById(productId);
    }

    /**
     * 특정 상품을 목록에서 삭제하는 기능
     * @param productId 상품아이디
     */
    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
    }

    /*
    @GetMapping("/v2/providers/api/v1/seller-products/category/{categoryId}")
    public void registerProductInCategory(@PathVariable Long categoryId, @RequestBody List<ProductDto> products) {
        productService.saveProductInCategory(categoryId, products);
    }
     */

    @GetMapping("/categories/{categoryId}")
    public List<Product> getProductListBySort(@PathVariable Long categoryId,
                                              @RequestParam(required = false, defaultValue = "1") int page,
                                              @RequestParam(required = false, defaultValue = "60") int listSize,
                                              @RequestParam(required = false, defaultValue = "0") Long score,
                                              @RequestParam(required = false, defaultValue = "0") Long minPrice,
                                              @RequestParam(required = false, defaultValue = "0") Long maxPrice,
                                              @RequestParam(required = false, defaultValue = "latestOrder") String sorter) {
        if (score > 0) {
            return getProductListByScoreRange(categoryId, page, listSize, score);
        }
        if (minPrice > 0 && (minPrice <= maxPrice)) {
            return getProductListByPriceRange(categoryId, page, listSize, minPrice, maxPrice);
        }
        if (!sorter.isEmpty()) {
            return getProductListBySort(categoryId, page, listSize, sorter);
        }
        return productService.findProductListByScoreRange(categoryId, page, listSize,0L);
    }

    private List<Product> getProductListByScoreRange(Long categoryId, int page, int listSize, Long score) {
        return productService.findProductListByScoreRange(categoryId, page, listSize,0L);
    }

    private List<Product> getProductListByPriceRange(Long categoryId, int page, int listSize, Long minPrice, Long maxPrice) {
        return productService.findProductListByPriceRange(categoryId, page, listSize, minPrice, maxPrice);
    }

    private List<Product> getProductListBySort(Long categoryId, int page, int listSize, String sorter) {
        return productService.findProductListBySort(categoryId, page, listSize, sorter);
    }
}
