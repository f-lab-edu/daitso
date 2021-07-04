package com.flab.daitso.service;

import com.flab.daitso.dto.product.Category;
import com.flab.daitso.dto.product.ProductDto;
import com.flab.daitso.error.exception.product.DuplicateProductNameException;
import com.flab.daitso.mapper.ProductMapper;
import com.flab.daitso.error.exception.product.SoldOutException;
import com.flab.daitso.error.exception.product.NotFoundException;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

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
        ProductDto findName = productMapper.findProductByName(productDto.getName());
        if (findName != null) {
            throw new DuplicateProductNameException();
        }
    }

    public void deleteProduct(Long productId) {
        Optional<ProductDto> product = Optional.ofNullable(productMapper.findProductById(productId));
        if (product.isEmpty()) {
            throw new NotFoundException();
        } else {
            productMapper.delete(productId);
        }
    }

    public Category saveProductInCategory(Long categoryId, Long productId) {
        Category findCategory = categoryService.findById(categoryId);
        ProductDto findProduct = findProductById(productId);
        findCategory.addProduct(findProduct);
        System.out.println("#####" + findCategory.getProducts().get(0).getName());

        productMapper.saveProductInCategory(categoryId, productId);

        return findCategory;
    }
}
