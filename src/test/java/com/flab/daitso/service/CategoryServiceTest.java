package com.flab.daitso.service;

import com.flab.daitso.dto.product.Category;
import com.flab.daitso.dto.product.Product;
import com.flab.daitso.error.exception.category.NotFoundCategoryException;
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

    private final static int PAGE = 1;
    private final static int LISTSIZE = 60;

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
        Category carGoods = new Category("car goods");
        Category interior = new Category("interior");

        carGoods.addChildCategory(interior);

        categoryService.saveCategory(carGoods);
        Long interiorId = categoryService.saveCategory(interior);

        Product product1 = new Product.Builder()
                .name("test1")
                .price(10000L)
                .content("test1 상품입니다.")
                .build();

        Product product2 = new Product.Builder()
                .name("test2")
                .price(20000L)
                .content("test2 상품입니다.")
                .build();

        productService.registerProduct(product1);
        productService.registerProduct(product2);

       productService.saveProductInCategory(interiorId, product1.getProductId());
       productService.saveProductInCategory(interiorId, product2.getProductId());

        assertThat(productService.findProductListByCategoryId(interiorId, 1, 10).size()).isEqualTo(2);
    }

    @Test
    @DisplayName("카테고리 id로 상품 목록 반환")
    public void categoryID로_상품_목록_반환() {
        Category carGoods = new Category("car goods");
        Category interior = new Category("interior");

        carGoods.addChildCategory(interior);

        Long carGoodsId = categoryService.saveCategory(carGoods);
        Long interiorId = categoryService.saveCategory(interior);

        Product product1 = new Product.Builder()
                .name("test1")
                .price(10000L)
                .content("test1 상품입니다.")
                .build();

        Product product2 = new Product.Builder()
                .name("test2")
                .price(20000L)
                .content("test2 상품입니다.")
                .build();

        productService.registerProduct(product1);
        productService.registerProduct(product2);

        productService.saveProductInCategory(interiorId, product1.getProductId());
        productService.saveProductInCategory(interiorId, product2.getProductId());

        assertThat(productService.findProductListByCategoryId(interiorId, PAGE, LISTSIZE).get(0).getName()).isEqualTo(product1.getName());
        assertThat(productService.findProductListByCategoryId(interiorId, PAGE, LISTSIZE).get(1).getName()).isEqualTo(product2.getName());
    }

    @Test
    @DisplayName("카테고리 삭제")
    public void 카테고리_삭제() {
        Category carGoods = new Category("car goods");
        Category interior = new Category("interior");

        carGoods.addChildCategory(interior);

        Long carGoodsId = categoryService.saveCategory(carGoods);
        Long interiorId = categoryService.saveCategory(interior);

        Product product1 = new Product.Builder()
                .name("test1")
                .price(10000L)
                .content("test1 상품입니다.")
                .build();

        Product product2 = new Product.Builder()
                .name("test2")
                .price(20000L)
                .content("test2 상품입니다.")
                .build();

        productService.registerProduct(product1);
        productService.registerProduct(product2);

        productService.saveProductInCategory(interiorId, product1.getProductId());

        categoryService.deleteCategory(interiorId);

        assertThrows(NotFoundCategoryException.class, () -> categoryService.findById(interiorId));
    }
}