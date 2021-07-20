package com.flab.daitso.controller;

import com.flab.daitso.dto.product.Category;
import com.flab.daitso.dto.product.ProductDto;
import com.flab.daitso.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/v2/providers/api/v1/seller-products/category")
    public void registerCategory(@RequestBody @Valid Category category) {
        categoryService.saveCategory(category);
    }

//    @GetMapping("/np/categories/{categoryId}")
//    public List<ProductDto> getProductsInCategory(@PathVariable Long categoryId) {
//        return categoryService.findProductListByCategoryId(categoryId);
//    }
}
