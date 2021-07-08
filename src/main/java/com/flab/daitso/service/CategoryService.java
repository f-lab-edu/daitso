package com.flab.daitso.service;

import com.flab.daitso.dto.product.Category;
import com.flab.daitso.dto.product.ProductDto;
import com.flab.daitso.error.exception.category.NotFoundCategoryException;
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

    public void deleteCategory(Long categoryId) {
        findById(categoryId);
        categoryMapper.removeCategory(categoryId);
    }

    public Category findById(Long categoryId) {
        Category findCategory = categoryMapper.findByCategoryId(categoryId);
        if (findCategory == null) {
            throw new NotFoundCategoryException();
        }
        return findCategory;
    }

    public Category findByName(String name) {
        Category findCategory = categoryMapper.findByName(name);
        if (findCategory == null) {
            throw new NotFoundCategoryException();
        }
        return findCategory;
    }

    public List<ProductDto> findCategoryListByName(String name) {
        return categoryMapper.findCategoryListByName(name);
    }

    public List<ProductDto> findCategoryListById(Long categoryId) {
        return categoryMapper.findCategoryListById(categoryId);
    }
}
