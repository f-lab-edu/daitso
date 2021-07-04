package com.flab.daitso.dto.product;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

public class Category {

    private Long categoryId;

    @NotBlank(message = "이름을 입력해주세요")
    private String name;

    private List<ProductDto> products = new ArrayList<>();

    private Category parent;

    private List<Category> child = new ArrayList<>();

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }

    public Category getParent() {
        return parent;
    }

    public List<ProductDto> getProducts() {
        return products;
    }

    public List<Category> getChild() {
        return child;
    }

    public void addProduct(ProductDto productDto) {
        this.products.add(productDto);
    }
    private void setParent(Category parent) {
        this.parent = parent;
    }

    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this);
    }
}
