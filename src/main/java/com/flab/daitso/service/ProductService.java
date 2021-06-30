package com.flab.daitso.service;

import com.flab.daitso.dto.product.ProductDto;
import com.flab.daitso.mapper.ProductMapper;
import com.flab.daitso.error.exception.product.SoldOutException;
import com.flab.daitso.error.exception.product.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductMapper productMapper;

    public ProductService(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public List<ProductDto> findProductsByCategoryId(int categoryId) {
        List<ProductDto> products = productMapper.findByCategoryId(categoryId);

        if (products.isEmpty()) {
            throw new NotFoundException();
        }
        return products;
    }

    public ProductDto findProductById(Long pid) {
        Optional<ProductDto> product = Optional.ofNullable(productMapper.findById(pid));

        if (product.isEmpty()) {
            throw new NotFoundException();
        }
        if (product.get().getQuantity() < 1) {
            throw new SoldOutException();
        }

        return product.get();
    }

    public ProductDto findProductByName(String name) {
        Optional<ProductDto> product = Optional.ofNullable(productMapper.findByName(name));

        if (product.isEmpty()) {
            throw new NotFoundException();
        }
        return product.get();
    }

    public void registerProduct(ProductDto productDto) {
        Optional<ProductDto> product = Optional.ofNullable(
                productMapper.findByCategoryIdAndName(productDto.getCategoryId(), productDto.getName()));
        if (product.isPresent()) {
            productMapper.increaseQuantity(product.get().getPid());
        } else {
            productMapper.register(productDto);
        }
    }

    public void deleteProduct(Long pid) {
        Optional<ProductDto> product = Optional.ofNullable(productMapper.findById(pid));
        if (product.isEmpty()) {
            throw new NotFoundException();
        } else {
            productMapper.delete(pid);
        }
    }
}
