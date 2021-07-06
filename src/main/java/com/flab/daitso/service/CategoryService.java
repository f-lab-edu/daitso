package com.flab.daitso.service;

import com.flab.daitso.dto.product.Category;
import com.flab.daitso.dto.product.ProductDto;
import com.flab.daitso.mapper.CategoryMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    public Long saveCategory(Category category) {
        if (category.getParent() == null) {
            categoryMapper.saveRootCategory(category);
            return category.getCategoryId();
        } else {
            HashMap<String, Object> map = new HashMap<>();
            map.put("name", category.getName());
            map.put("parentId", category.getParent().getCategoryId());
            categoryMapper.saveChildCategory(map);
            Category findCategory = categoryMapper.findByName(category.getName());

            return findCategory.getCategoryId();
        }
    }

    public Category findById(Long categoryId) {
        return categoryMapper.findByCategoryId(categoryId);
    }

    public Category findByName(String name) {
        return categoryMapper.findByName(name);
    }

    public List<ProductDto> findCategoryListByName(String name) {
        return categoryMapper.findCategoryListByName(name);
    }
}
