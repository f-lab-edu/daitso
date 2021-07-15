package com.flab.daitso.service;

import com.flab.daitso.dto.product.Category;
import com.flab.daitso.dto.product.ProductDto;
import com.flab.daitso.error.exception.product.DuplicateProductNameException;
import com.flab.daitso.mapper.ProductMapper;
import com.flab.daitso.error.exception.product.SoldOutException;
import com.flab.daitso.error.exception.product.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductMapper productMapper;

    private final CategoryService categoryService;

    @Autowired
    public ProductService(ProductMapper productMapper, CategoryService categoryService) {
        this.productMapper = productMapper;
        this.categoryService = categoryService;
    }

    /**
     * 상품 Id로 product 찾기
     */
    public ProductDto findProductById(Long productId) {
        ProductDto product = productMapper.findProductById(productId);
        if (product == null) {
            throw new NotFoundException();
        }
        if (product.getQuantity() < 1) {
            throw new SoldOutException();
        }

        return product;
    }

    /**
     * 상품명으로 product 찾기
     */
    public ProductDto findProductByName(String name) {
        ProductDto product = productMapper.findProductByName(name);
        if (product == null) {
            throw new NotFoundException();
        }

        return product;
    }

    /**
     * 상품 등록하기
     */
    public Long registerProduct(ProductDto productDto) {
        DuplicateProductName(productDto);
        productMapper.register(productDto);

        return productDto.getProductId();
    }

    /**
     * 중복된 상품명 검사
     */
    private void DuplicateProductName(ProductDto productDto) {
        ProductDto findProduct = productMapper.findProductByName(productDto.getName());
        if (findProduct != null) {
            throw new DuplicateProductNameException();
        }
    }

    /**
     * 상품 삭제하기
     */
    public void deleteProduct(Long productId) {
        ProductDto findProduct = productMapper.findProductById(productId);

        if (findProduct == null) {
            throw new NotFoundException();
        }
        productMapper.delete(productId);
    }

    /**
     * 카테고리에 상품 넣기
     */
    public Category saveProductInCategory(Long categoryId, List<ProductDto> products) {
        Category findCategory = categoryService.findById(categoryId);
        for (ProductDto product : products) {
            ProductDto findProduct = findProductById(product.getProductId());
            findCategory.addProduct(findProduct);
            productMapper.saveProductInCategory(categoryId, findProduct.getProductId());
        }

        return findCategory;
    }

    /**
     * 별점 - 1 ~ 별점로 상품 리스트 조회하기
     */
    public List<ProductDto> findProductListByScoreRange(Long categoryId, Long score) {
        if (score == 0) {
            return categoryService.findProductListByCategoryId(categoryId);
        }
        return productMapper.findProductListByScoreRange(categoryId, score);
    }

    /**
     * 가격으로 상품 리스트 조회하기
     */
    public List<ProductDto> findProductListByPriceRange(Long categoryId, Long minPrice, Long maxPrice) {
        return productMapper.findProductListByPriceRange(categoryId, minPrice, maxPrice);
    }

    /**
     * 최신순으로 상품 리스트 조회하기
     * sorter
     * 0 --> 최신순
     * 1 -->
     */
    public List<ProductDto> findProductListByLatestOrder(Long categoryId, Long sorter) {
        if (sorter == 0) {
            return productMapper.findProductListByLatestOrder(categoryId);
        }
        return productMapper.findProductListByLatestOrder(categoryId);
    }
}
