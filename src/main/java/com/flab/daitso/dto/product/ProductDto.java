package com.flab.daitso.dto.product;

import com.flab.daitso.error.exception.product.NotEnoughStockException;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProductDto {

    private Long productId;

    @NotBlank(message = "name can't be null")
    private String name;

    @NotBlank(message = "price can't be null")//   how to restrain..?
    @Min(value = 0, message = "price can't be smaller than 0")
    private int price;

    @NotBlank(message = "content can't be null")
    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private int quantity;

    public ProductDto() {
    }

    public ProductDto(Long productId, String name, int price, String content, LocalDateTime createdAt,
                      LocalDateTime updatedAt, int quantity) {
        this.productId = 1L;
        this.name = name;
        this.price = price;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public int getQuantity() {
        return quantity;
    }

    /**
     * stock 증가
     */
    public void addStock(int quantity) {
        this.quantity += quantity;
    }

    /**
     * stock 감소
     */
    public void removeStock(int quantity) {
        int restStock = this.quantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException();
        }
        this.quantity = restStock;
    }

    /**
     * Builder 패턴 구현
     */
    public static class Builder {
        private String name;
        private int price;
        private String content;
        private LocalDateTime createAt;
        private LocalDateTime updateAt;
        private int quantity;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder price(int price) {
            this.price = price;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder createAt(LocalDateTime createAt) {
            this.createAt = createAt;
            return this;
        }

        public Builder updateAt(LocalDateTime updateAt) {
            this.updateAt = updateAt;
            return this;
        }

        public Builder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public ProductDto build() {
            return new ProductDto(1L, name, price, content, createAt, updateAt, quantity);
        }
    }
}
