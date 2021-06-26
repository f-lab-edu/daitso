package com.flab.daitso.controller;

import com.flab.daitso.dto.product.ProductDto;
import com.flab.daitso.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(path="daitso/product/category")
public class ProductController {

    @Autowired
    ProductService productService;

    /**
     * 하나의 특정 카테고리에 속한 모든 상품 검색 기능
     * @param categoryId 카테고리 아이디
     */
    @GetMapping("{categoryId}")
    public List<ProductDto> getProducts(@PathVariable int categoryId) throws Exception {
        return productService.getProducts(categoryId);
    }

    /**
     * 하나의 특정 상품을 상품명으로 검색하는 기능
     * @param pid 상품아이디
     */
    @GetMapping("/find/{pid}")
    public ProductDto getProduct(@PathVariable int pid) throws Exception {
        return productService.getProduct(pid);
    }

    /**
     * 특정 상품을 등록하는 기능
     * @param productDto 상품 정보
     */
    @PostMapping("/add")
    public void registerProduct(@Valid @RequestBody ProductDto productDto) throws Exception {
        System.out.println(productDto.toString());
        productService.registerProduct(productDto);
    }

    /**
     * 특정 상품을 목록에서 삭제하는 기능
     * @param pid 상품아이디
     */
    @DeleteMapping("/delete/{pid}")
    public void deleteProduct(@PathVariable int pid) throws Exception {
        productService.deleteProduct(pid);
    }

}
