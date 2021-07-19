package com.flab.daitso.dto.product;

import com.flab.daitso.dto.delivery.DeliveryChargeType;
import com.flab.daitso.error.exception.product.NotEnoughStockException;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class ProductDto {

    private Long productId;

    @NotBlank(message = "이름을 입력하세요.")
    private String name;

    @NotNull(message = "가격을 입력하세요.")
    @Min(value = 0, message = "가격은 0원 이상이여야 합니다.")
    private Long price;

    @NotBlank(message = "상품 내용을 입력하세요.")
    private String content;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();

    private Long quantity;

    private Long score;

    private String mainImage;

    private String detailImage;

    private DeliveryChargeType deliveryChargeType;

    public ProductDto() {
    }

    public ProductDto(Long productId, String name, Long price, String content, LocalDateTime createdAt,
                      LocalDateTime updatedAt, Long quantity, Long score, String mainImage, String detailImage, DeliveryChargeType deliveryChargeType) {
        this.productId = 1L;
        this.name = name;
        this.price = price;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.quantity = quantity;
        this.score = score;
        this.mainImage = mainImage;
        this.detailImage = detailImage;
        this.deliveryChargeType = deliveryChargeType;
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public Long getPrice() {
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

    public Long getQuantity() {
        return quantity;
    }

    public Long getScore() {
        return score;
    }

    public String getMainImage() {
        return mainImage;
    }

    public String getDetailImage() {
        return detailImage;
    }

    public DeliveryChargeType getDeliveryChargeType() {
        return deliveryChargeType;
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
    public void removeStock(Long quantity) {
        Long restStock = this.quantity - quantity;
        if (restStock < 0) {
            this.quantity = 0L;
            throw new NotEnoughStockException();
        }
        this.quantity = restStock;
    }

    /**
     * 별점 업데이트
     */
    public void updateScore(Long score) {
        this.score = score;
    }

    /**
     * Builder 패턴 구현
     */
    public static class Builder {
        private String name;
        private Long price;
        private String content;
        private LocalDateTime createAt;
        private LocalDateTime updateAt;
        private Long quantity;
        private Long score;
        private String mainImage;
        private String detailImage;
        private DeliveryChargeType deliveryChargeType;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder price(Long price) {
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

        public Builder quantity(Long quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder score(Long score) {
            this.score = score;
            return this;
        }

        public Builder mainImage(String mainImage) {
            this.mainImage = mainImage;
            return this;
        }

        public Builder detailImage(String detailImage) {
            this.detailImage = detailImage;
            return this;
        }

        public Builder deliveryChargeType(DeliveryChargeType deliveryChargeType) {
            this.deliveryChargeType = deliveryChargeType;
            return this;
        }

        public ProductDto build() {
            return new ProductDto(1L, name, price, content, createAt, updateAt, quantity, score, mainImage, detailImage, deliveryChargeType);
        }
    }
}
