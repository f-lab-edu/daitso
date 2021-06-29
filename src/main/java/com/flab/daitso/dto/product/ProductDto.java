package com.flab.daitso.dto.product;

import com.flab.daitso.dto.order.OrderDto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class ProductDto {

    private int pid;

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


    public ProductDto(){

    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "categoryId=" + categoryId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", content='" + content + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", quantity=" + quantity +
                '}';
    }

}
