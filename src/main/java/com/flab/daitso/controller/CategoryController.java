package com.flab.daitso.controller;

import com.flab.daitso.dto.product.Category;
import com.flab.daitso.dto.product.ProductDto;
import com.flab.daitso.service.CategoryService;
import com.flab.daitso.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    private final ProductService productService;

    @Autowired
    public CategoryController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @PostMapping
    public void registerCategory(@RequestBody @Valid Category category) {
        categoryService.saveCategory(category);
    }

//    @GetMapping("/np/categories/{categoryId}")
//    public List<ProductDto> getProductsInCategory(@PathVariable Long categoryId) {
//        return
//        categoryService.findProductListByCategoryId(categoryId);
//    }

    @GetMapping("/{categoryId}")
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
