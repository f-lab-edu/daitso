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

    public ProductDto findProductByName(String name) {
        ProductDto product = productMapper.findProductByName(name);
        if (product == null) {
            throw new NotFoundException();
        }

        return product;
    }

    public Long registerProduct(ProductDto productDto) {
        DuplicateProductName(productDto);
        productMapper.register(productDto);

        return productDto.getProductId();
    }

    private void DuplicateProductName(ProductDto productDto) {
        ProductDto findProduct = productMapper.findProductByName(productDto.getName());
        if (findProduct != null) {
            throw new DuplicateProductNameException();
        }
    }

    public void deleteProduct(Long productId) {
        ProductDto findProduct = productMapper.findProductById(productId);

        if (findProduct == null) {
            throw new NotFoundException();
        }
        productMapper.delete(productId);
    }

    public Category saveProductInCategory(Long categoryId, List<ProductDto> products) {
        Category findCategory = categoryService.findById(categoryId);
        for (ProductDto product : products) {
            ProductDto findProduct = findProductById(product.getProductId());
            findCategory.addProduct(findProduct);
            productMapper.saveProductInCategory(categoryId, findProduct.getProductId());
        }

        return findCategory;
    }
}
