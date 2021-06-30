package com.flab.daitso.dto.product;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class ProductDto {

    private Long pid;

    @NotNull(message = "categoryId can't be null")//  how to restrain..?
    private int categoryId;

    @NotNull(message = "name can't be null")
    private String name;

    @NotNull(message = "price can't be null")//   how to restrain..?
    @Min(value = 0, message = "price can't be smaller than 0")
    private int price;

    @NotNull(message = "content can't be null")
    private String content;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int quantity;

    public ProductDto(Long pid, int categoryId, String name, int price, String content, LocalDateTime createdAt, LocalDateTime updatedAt, int quantity) {
        this.pid = 1L;
        this.categoryId = categoryId;
        this.name = name;
        this.price = price;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.quantity = quantity;
    }

    public Long getPid() {
        return pid;
    }

    public int getCategoryId() {
        return categoryId;
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
     * Builder 패턴 구현
     */
    public static class Builder {
        private int categoryId;
        private String name;
        private int price;
        private String content;
        private LocalDateTime createAt;
        private LocalDateTime updateAt;
        private int quantity;

        public Builder categoryId(int categoryId) {
            this.categoryId = categoryId;
            return this;
        }

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
            return new ProductDto(1L, categoryId, name, price, content, createAt, updateAt, quantity);
        }
    }
}
