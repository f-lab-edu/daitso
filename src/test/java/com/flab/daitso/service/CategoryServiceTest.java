package com.flab.daitso.service;

import com.flab.daitso.dto.product.Category;
import com.flab.daitso.dto.product.ProductDto;
import com.flab.daitso.error.exception.category.NotFoundCategoryException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Test
    @DisplayName("root_child_루트_카테고리 등록")
    public void root_child_카테고리_등록() {
        Category carGoods = new Category("car goods");
        Category interior = new Category("interior");
        Category chain = new Category("chain");

        carGoods.addChildCategory(interior);
        carGoods.addChildCategory(chain);

        Long carGoodsId = categoryService.saveCategory(carGoods);
        Long interiorId = categoryService.saveCategory(interior);
        Long chainId = categoryService.saveCategory(chain);

        assertThat(carGoodsId).isEqualTo(carGoods.getCategoryId());
        assertThat(interior.getParent().getCategoryId()).isEqualTo(carGoods.getCategoryId());
        assertThat(chain.getParent().getCategoryId()).isEqualTo(carGoods.getCategoryId());
        assertThat(interiorId).isEqualTo(categoryService.findByName(interior.getName()).getCategoryId());
    }

    @Test
    @DisplayName("카테고리 아이디로 카테고리 찾기")
    public void 카테고리_아이디로_카테고리_찾기() {
        Category carGoods = new Category("car goods");
        Long carGoodsId = categoryService.saveCategory(carGoods);

        Category findCategory = categoryService.findById(carGoodsId);

        assertThat(findCategory.getCategoryId()).isEqualTo(carGoodsId);
    }

    @Test
    @DisplayName("카테고리 안에 존재하는 상품 리스트 반환")
    public void 카테고리_안_상품_리스트_반환() {
        List<ProductDto> products = new ArrayList<>();

        Category carGoods = new Category("car goods");
        Category interior = new Category("interior");

        carGoods.addChildCategory(interior);

        categoryService.saveCategory(carGoods);
        Long interiorId = categoryService.saveCategory(interior);

        ProductDto productDto1 = new ProductDto.Builder()
                .name("test1")
                .price(10000L)
                .content("test1 상품입니다.")
                .build();

        ProductDto productDto2 = new ProductDto.Builder()
                .name("test2")
                .price(20000L)
                .content("test2 상품입니다.")
                .build();

        productService.registerProduct(productDto1);
        productService.registerProduct(productDto2);

        products.add(productDto1);
        products.add(productDto2);

        Category category = productService.saveProductInCategory(interiorId, products);

        assertThat(category.getProducts().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("카테고리 id로 상품 목록 반환")
    public void categoryID로_상품_목록_반환() {
        List<ProductDto> products = new ArrayList<>();

        Category carGoods = new Category("car goods");
        Category interior = new Category("interior");

        carGoods.addChildCategory(interior);

        Long carGoodsId = categoryService.saveCategory(carGoods);
        Long interiorId = categoryService.saveCategory(interior);

        ProductDto productDto1 = new ProductDto.Builder()
                .name("test1")
                .price(10000L)
                .content("test1 상품입니다.")
                .build();

        ProductDto productDto2 = new ProductDto.Builder()
                .name("test2")
                .price(20000L)
                .content("test2 상품입니다.")
                .build();

        productService.registerProduct(productDto1);
        productService.registerProduct(productDto2);

        products.add(productDto1);
        products.add(productDto2);

        productService.saveProductInCategory(interiorId, products);

        assertThat(categoryService.findProductListByCategoryId(interiorId).get(0).getName()).isEqualTo(productDto1.getName());
        assertThat(categoryService.findProductListByCategoryId(interiorId).get(1).getName()).isEqualTo(productDto2.getName());
    }

    @Test
    @DisplayName("카테고리 삭제")
    public void 카테고리_삭제() {
        List<ProductDto> products = new ArrayList<>();

        Category carGoods = new Category("car goods");
        Category interior = new Category("interior");

        carGoods.addChildCategory(interior);

        Long carGoodsId = categoryService.saveCategory(carGoods);
        Long interiorId = categoryService.saveCategory(interior);

        ProductDto productDto1 = new ProductDto.Builder()
                .name("test1")
                .price(10000L)
                .content("test1 상품입니다.")
                .build();

        ProductDto productDto2 = new ProductDto.Builder()
                .name("test2")
                .price(20000L)
                .content("test2 상품입니다.")
                .build();

        productService.registerProduct(productDto1);
        productService.registerProduct(productDto2);

        products.add(productDto1);
        products.add(productDto2);

        productService.saveProductInCategory(interiorId, products);

        categoryService.deleteCategory(interiorId);

        assertThrows(NotFoundCategoryException.class, () -> categoryService.findById(interiorId));
    }
}