package com.flab.daitso.service;

import com.flab.daitso.dto.product.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

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
}