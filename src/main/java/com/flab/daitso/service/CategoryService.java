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

    /**
     * 카테고리 만들기 (부모 카테고리 생성 후, 자식 카테고리 생성하기)
     */
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

    /**
     * 카테고리 삭제하기
     */
    public void deleteCategory(Long categoryId) {
        findById(categoryId);
        categoryMapper.removeCategory(categoryId);
    }

    /**
     * 카테고리 아이디로 카테고리 찾기
     */
    public Category findById(Long categoryId) {
        Category findCategory = categoryMapper.findByCategoryId(categoryId);
        if (findCategory == null) {
            throw new NotFoundCategoryException();
        }
        return findCategory;
    }

    /**
     * 카테고리 이름으로 카테고리 찾기
     */
    public Category findByName(String name) {
        Category findCategory = categoryMapper.findByName(name);
        if (findCategory == null) {
            throw new NotFoundCategoryException();
        }
        return findCategory;
    }

    /**
     * 카테고리 아이디로 카테고리 안의 상품 목록 반환
     */
    public List<ProductDto> findProductListByCategoryId(Long categoryId) {
        return categoryMapper.findProductListByCategoryId(categoryId);
    }
}
