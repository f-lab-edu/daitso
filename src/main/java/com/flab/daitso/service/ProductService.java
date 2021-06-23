package com.flab.daitso.service;

import com.flab.daitso.dto.product.ProductDto;
import com.flab.daitso.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductMapper productMapper;

    public List<ProductDto> getProducts(int categoryId) throws Exception {
        Optional<List<ProductDto>> products = Optional.ofNullable(productMapper.getProducts(categoryId));
        if (products.isEmpty()){
            throw new Exception("현재 카테고리에 해당하는 상품이 없습니다.");
        }
        return products.get();
    }

    public ProductDto getProduct(String productName) throws Exception {
        Optional<ProductDto> product = Optional.ofNullable(productMapper.getProduct(productName));

        if (product.isEmpty()){
            throw new Exception("존재하지 않는 상품입니다.");
        }
        if (product.get().getQuantity() < 1){
            throw new Exception("품절 상품 입니다.");
        }

        return product.get();
    }

    public void registerProduct(ProductDto productDto) throws Exception {
        Optional<ProductDto> product = Optional.ofNullable(productMapper.getProduct(productDto.getName()));
        if (product.isPresent()){
            productMapper.increaseQuantity(product.get().getName());
        }
        else {
        productMapper.registerProduct(productDto);
        }
    }

    public void deleteProduct(String productName) throws Exception {
        Optional<ProductDto> product = Optional.ofNullable(productMapper.getProduct(productName));
        if (product.isEmpty()){
            throw new Exception("해당 상품이 존재하지 않습니다.");
        }
        else{
            productMapper.deleteProduct(productName);
        }

    }
}
