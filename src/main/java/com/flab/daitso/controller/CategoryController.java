package com.flab.daitso.controller;

import com.flab.daitso.dto.product.Category;
import com.flab.daitso.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
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
}
