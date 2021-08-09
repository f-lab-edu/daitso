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
     *
     * @param product 상품 정보
     */
    @PostMapping
    public void registerProduct(@RequestBody @Valid Product product) {
        productService.registerProduct(product);
    }

    /**
     * 하나의 특정 상품을 상품명으로 검색하는 기능
     *
     * @param productId 상품아이디
     */
    @GetMapping("/{productId}")
    public Product searchById(@PathVariable Long productId) {
        return productService.findProductById(productId);
    }

    /**
     * 특정 상품을 목록에서 삭제하는 기능
     *
     * @param productId 상품아이디
     */
    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
    }

    @GetMapping("/categories/{categoryId}")
    public List<Product> getProductListBySort(@PathVariable Long categoryId,
                                              @RequestParam(required = false, defaultValue = "1") int page,
                                              @RequestParam(required = false, defaultValue = "60") int listSize,
                                              @RequestParam(required = false) String name,
                                              @RequestParam(required = false, defaultValue = "0") Long minPrice,
                                              @RequestParam(required = false, defaultValue = "999999999999") Long maxPrice,
                                              @RequestParam(required = false, defaultValue = "0") Long score,
                                              @RequestParam(required = false, defaultValue = "latestOrder") String sorter) {
        if (!sorter.isEmpty()) {
            return getProductListBySort(categoryId, page, listSize, sorter);
        }
        if (name == null) {
            return productService.findProductListByPriceAndScoreRange(categoryId, page, listSize, minPrice, maxPrice, score);
        }
        return productService.findProductListByNamePriceAndScoreRange(categoryId, page, listSize, name, minPrice, maxPrice, score);

    }

    private List<Product> getProductListBySort(Long categoryId, int page, int listSize, String sorter) {
        return productService.findProductListBySort(categoryId, page, listSize, sorter);
    }

}
