package com.flab.daitso.service;

import com.flab.daitso.dto.product.Category;
import com.flab.daitso.dto.product.Product;
import com.flab.daitso.error.exception.product.DuplicateProductNameException;
import com.flab.daitso.error.exception.product.NotFoundException;
import com.flab.daitso.mapper.ProductMapper;
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
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class ProductServiceTest {

    private final static int PAGE = 1;
    private final static int LISTSIZE = 60;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Test
    @DisplayName("상품_등록_테스트")
    public void 상품_등록_테스트() {
        Product productDto = new Product.Builder()
                .name("test")
                .price(10000L)
                .content("test 상품입니다.")
                .build();

        Long saveProductId = productService.registerProduct(productDto);
        Product product = productService.findProductById(saveProductId);
        assertThat(saveProductId).isEqualTo(product.getProductId());
    }

    @Test
    @DisplayName("이미 존재하는 상품 테스트")
    public void 존재하는_상품_테스트() {
        Product product = new Product.Builder()
                .name("test")
                .price(10000L)
                .content("test 상품입니다.")
                .build();
        productService.registerProduct(product);

        Product product1 = new Product.Builder()
                .name("test")
                .price(10000L)
                .content("test 상품입니다.")
                .build();
        assertThrows(DuplicateProductNameException.class, () -> productService.registerProduct(product1));
    }

    @Test
    @DisplayName("상품에 카테고리 지정")
    public void 상품에_카테고리_지정_테스트() {
        List<Product> products = new ArrayList<>();

        Product product1 = new Product.Builder()
                .name("test")
                .price(10000L)
                .content("test 상품입니다.")
                .build();

        productService.registerProduct(product1);
        products.add(product1);

        Category sportsCategory = new Category("sports");
        categoryService.saveCategory(sportsCategory);

        Category soccerCategory = new Category("soccer");
        sportsCategory.addChildCategory(soccerCategory);

        Long categoryId = categoryService.saveCategory(soccerCategory);

        Category category = productService.saveProductInCategory(categoryId, products);

        assertThat(category.getProducts().get(0).getProductId()).isEqualTo(product1.getProductId());
    }


    @Test
    @DisplayName("상품 아이디로 해당 상품을 제대로 검색하는지 테스트")
    public void 상품_아이디로_검색_테스트() {
        Product productDto = new Product.Builder()
                .name("test1")
                .price(20000L)
                .content("test1 상품입니다.")
                .build();
        productService.registerProduct(productDto);

        Product product = productService.findProductByName("test1");

        // 상품 아이디를 이용해 상품을 제대로 검색하는지 테스트
        Product findProduct = productService.findProductById(product.getProductId());
        assertThat(product.getProductId()).isEqualTo(findProduct.getProductId());
    }

    @Test
    @DisplayName("상품 이름로 해당 상품을 제대로 검색하는지 테스트")
    public void 상품_이름으로_검색_테스트() {
        Product productDto = new Product.Builder()
                .name("test1")
                .price(20000L)
                .content("test1 상품입니다.")
                .build();
        productService.registerProduct(productDto);

        Product product = productService.findProductByName("test1");
        assertThat(product.getName()).isEqualTo(productDto.getName());
    }

    @Test
    @DisplayName("없는 상품 아이디로 검색했을 때 테스트")
    public void 존재하지_않는_상품_테스트() {
        assertThrows(NotFoundException.class, () -> productService.findProductById(-2L));
    }

    @Test
    @DisplayName("상품 아이디로 상품 삭제")
    public void 상품_아이디로_상품_삭제() {
        Product product = new Product.Builder()
                .name("test1")
                .price(20000L)
                .content("test1 상품입니다.")
                .build();
        productService.registerProduct(product);

        Long productId = product.getProductId();
        productService.deleteProduct(productId);

        assertThrows(NotFoundException.class, () -> productService.findProductById(productId));
    }

    @Test
    @DisplayName("카테고리 내에 존재하는 상품 삭제")
    public void 카테고리_내_존재하는_상품_삭제() {
        List<Product> products = new ArrayList<>();

        Product product1 = new Product.Builder()
                .name("test")
                .price(10000L)
                .content("test 상품입니다.")
                .build();

        productService.registerProduct(product1);
        products.add(product1);

        Category sportsCategory = new Category("sports");
        categoryService.saveCategory(sportsCategory);

        Category soccerCategory = new Category("soccer");
        sportsCategory.addChildCategory(soccerCategory);

        Long categoryId = categoryService.saveCategory(soccerCategory);

        productService.saveProductInCategory(categoryId, products);

        Long productId = product1.getProductId();
        productService.deleteProduct(productId);

        assertThrows(NotFoundException.class, () -> productService.findProductById(productId));
    }

    @Test
    @DisplayName("가격으로 상품 리스트 가져오기")
    public void 가격으로_상품_리스트_가져오기() {
        List<Product> products = new ArrayList<>();

        Category carGoods = new Category("car goods");
        Category interior = new Category("interior");

        carGoods.addChildCategory(interior);

        Long carGoodsId = categoryService.saveCategory(carGoods);
        Long interiorId = categoryService.saveCategory(interior);

        Product product1 = new Product.Builder()
                .name("test1")
                .price(10000L)
                .content("test1 상품입니다.")
                .score(4L)
                .build();

        Product product2 = new Product.Builder()
                .name("test2")
                .price(20000L)
                .content("test2 상품입니다.")
                .score(4L)
                .build();

        productService.registerProduct(product1);
        productService.registerProduct(product2);

        products.add(product1);
        products.add(product2);

        productService.saveProductInCategory(interiorId, products);

        List<Product> productList = productService.findProductListByPriceRange(interiorId, PAGE, LISTSIZE, 10000L, 20000L);

        assertThat(productList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("최신순으로 상품 리스트 가져오기")
    public void 최신순으로_상품_리스트_가져오기() throws InterruptedException {
        List<Product> products = new ArrayList<>();

        Category carGoods = new Category("car goods");
        Category interior = new Category("interior");

        carGoods.addChildCategory(interior);

        Long carGoodsId = categoryService.saveCategory(carGoods);
        Long interiorId = categoryService.saveCategory(interior);

        Product product1 = new Product.Builder()
                .name("test1")
                .price(10000L)
                .content("test1 상품입니다.")
                .score(4L)
                .build();

        Product product2 = new Product.Builder()
                .name("test2")
                .price(20000L)
                .content("test2 상품입니다.")
                .score(4L)
                .build();

        productService.registerProduct(product1);
        Thread.sleep(2000);
        productService.registerProduct(product2);

        products.add(product1);
        products.add(product2);

        productService.saveProductInCategory(interiorId, products);

        List<Product> productList = productService.findProductListBySort(interiorId, PAGE, LISTSIZE, "latestOrder");

        assertThat(productList.get(0).getProductId()).isEqualTo(product2.getProductId());
        assertThat(productList.get(1).getProductId()).isEqualTo(product1.getProductId());
    }
}
