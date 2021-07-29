package com.flab.daitso.mapper;

import com.flab.daitso.dto.delivery.DeliveryChargeType;
import com.flab.daitso.dto.product.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper {

    Product findProductByName(String name);

    // 상품을 상품 id로 찾는다.
    Product findProductById(Long productId);

    // 새 상품을 dto 째로 insert 한다.
    void register(Product product);

    // 하나의 상품을 상품아이디로 삭제한다.
    int delete(Long productId);

    void saveProductInCategory(@Param("categoryId") Long categoryId, @Param("productId") Long productId);

    /**
     * 카테고리 아이디로 상품 목록 조회
     */
    List<Product> findProductListByCategoryId(@Param("categoryId") Long categoryId, @Param("page") int page, @Param("listSize") int listSize);

    /**
     * 별점 범위로 상품 목록 조회
     */
    List<Product> findProductListByScoreRange(@Param("categoryId") Long categoryId, @Param("page") int page, @Param("listSize") int listSize, @Param("score") Long score);

    /**
     * 가격 범위로 상품 목록 조회
     */
    List<Product> findProductListByPriceRange(@Param("categoryId") Long categoryId, @Param("page") int page, @Param("listSize") int listSize, @Param("minPrice") Long minPrice, @Param("maxPrice") Long maxPrice);

    /**
     * 낮은 가격순으로 상품 목록 조회
     */
    List<Product> findProductListByLowPriceOrder(@Param("categoryId") Long categoryId, @Param("page") int page, @Param("listSize") int listSize);

    /**
     * 높은 가격순으로 상품 목록 조회
     */
    List<Product> findProductListByHighPriceOrder(@Param("categoryId") Long categoryId, @Param("page") int page, @Param("listSize") int listSize);

    /**
     * 별점순으로 상품 목록 조회
     */
    List<Product> findProductListByScoreOrder(@Param("categoryId") Long categoryId, @Param("page") int page, @Param("listSize") int listSize);

    /**
     * 최신순으로 상품 목록 조회
     */
    List<Product> findProductListByLatestOrder(@Param("categoryId") Long categoryId, @Param("page") int page, @Param("listSize") int listSize);

    /**
     * 배송 타입으로 상품 목록 조회
     */
    List<Product> findProductListByDeliveryChargeType(@Param("categoryId") Long categoryId, @Param("page") int page, @Param("listSize") int listSize, DeliveryChargeType deliveryChargeType);
}
