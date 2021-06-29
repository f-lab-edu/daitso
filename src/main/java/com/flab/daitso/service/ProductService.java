package com.flab.daitso.service;

import com.flab.daitso.dto.product.ProductDto;
import com.flab.daitso.mapper.ProductMapper;
import com.flab.daitso.error.exception.product.SoldOutException;
import com.flab.daitso.error.exception.product.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductMapper productMapper;

    public List<ProductDto> getProducts(int categoryId) throws Exception {
        List<ProductDto> products = productMapper.getProducts(categoryId);

        if (products.isEmpty()){
            throw new NotFoundException("현재 카테고리에 해당하는 상품이 없습니다.");
        }
        return products;
    }

    public ProductDto getProduct(int pid) throws Exception {
        Optional<ProductDto> product = Optional.ofNullable(productMapper.getProduct(pid));

        if (product.isEmpty()){
            throw new NotFoundException("존재하지 않는 상품입니다.");
        }
        if (product.get().getQuantity() < 1){
            throw new SoldOutException("품절 상품 입니다.");
        }

        return product.get();
    }

    public void registerProduct(ProductDto productDto) throws Exception {

        Optional<ProductDto> product = Optional.ofNullable(productMapper.getProductByCidName(productDto.getCategoryId(), productDto.getName()));

        if (product.isPresent()){
            productMapper.increaseQuantity(product.get().getPid());
        }
        else {
            productMapper.registerProduct(productDto);
        }
    }

    public void deleteProduct(int pid) throws Exception {
        Optional<ProductDto> product = Optional.ofNullable(productMapper.getProduct(pid));
        if (product.isEmpty()){
            throw new NotFoundException("해당 상품이 존재하지 않습니다.");
        }
        else{
            productMapper.deleteProduct(pid);
        }

    }
}
