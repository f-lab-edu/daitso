package com.flab.daitso.service;

import com.flab.daitso.dto.delivery.DeliveryChargeType;
import com.flab.daitso.dto.product.Category;
import com.flab.daitso.dto.product.Product;
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
    public Product findProductById(Long productId) {
        Product product = productMapper.findProductById(productId);
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
    public Product findProductByName(String name) {
        Product product = productMapper.findProductByName(name);
        if (product == null) {
            throw new NotFoundException();
        }

        return product;
    }

    /**
     * 상품 등록하기
     */
    public Long registerProduct(Product product) {
        DuplicateProductName(product);
        productMapper.register(product);

        return product.getProductId();
    }

    /**
     * 중복된 상품명 검사
     */
    private void DuplicateProductName(Product product) {
        Product findProduct = productMapper.findProductByName(product.getName());
        if (findProduct != null) {
            throw new DuplicateProductNameException();
        }
    }

    /**
     * 상품 삭제하기
     */
    public void deleteProduct(Long productId) {
        Product findProduct = productMapper.findProductById(productId);

        if (findProduct == null) {
            throw new NotFoundException();
        }
        productMapper.delete(productId);
    }

    /**
     * 카테고리에 상품 넣기
     */
    public Category saveProductInCategory(Long categoryId, List<Product> products) {
        Category findCategory = categoryService.findById(categoryId);
        for (Product product : products) {
            Product findProduct = findProductById(product.getProductId());
            findCategory.addProduct(findProduct);
            productMapper.saveProductInCategory(categoryId, findProduct.getProductId());
        }

        return findCategory;
    }

    /**
     * 카테고리 아이디로 카테고리 안의 상품 목록 반환
     */
    public List<Product> findProductListByCategoryId(Long categoryId, int page, int listSize) {
        return productMapper.findProductListByCategoryId(categoryId, listSize * (page - 1), listSize);
    }

    /**
     * 별점 - 1 ~ 별점로 상품 리스트 조회하기
     */
    public List<Product> findProductListByScoreRange(Long categoryId, int page, int listSize, Long score) {
        if (score == 0) {
            return findProductListByCategoryId(categoryId, listSize * (page - 1), listSize);
        }
        return productMapper.findProductListByScoreRange(categoryId, listSize * (page - 1), listSize, score);
    }

    /**
     * 가격으로 상품 리스트 조회하기
     */
    public List<Product> findProductListByPriceRange(Long categoryId, int page, int listSize, Long minPrice, Long maxPrice) {
        return productMapper.findProductListByPriceRange(categoryId, listSize * (page - 1), listSize, minPrice, maxPrice);
    }

    /**
     * 최신순으로 상품 리스트 조회하기
     * sorter
     * 0 --> 최신순
     * 1 -->
     */
    public List<Product> findProductListByLatestOrder(Long categoryId, int page, int listSize, Long sorter) {
        return productMapper.findProductListByLatestOrder(categoryId, listSize * (page - 1), listSize);
    }

    public List<Product> findProductListByDeliveryChargeType(Long categoryId, int page, int listSize, DeliveryChargeType deliveryChargeType) {
        return productMapper.findProductListByDeliveryChargeType(categoryId, listSize * (page - 1), listSize, deliveryChargeType);
    }
}
