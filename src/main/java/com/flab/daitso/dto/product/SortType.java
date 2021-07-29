package com.flab.daitso.dto.product;

import com.flab.daitso.mapper.ProductMapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum SortType {

    LATEST_ORDER("latestOrder") {
        @Override
        public List<Product> sortProducts(ProductMapper productMapper, ParameterBySort parameterBySort) {
            return productMapper.findProductListByLatestOrder(
                    parameterBySort.getCategoryId(), parameterBySort.getPage(), parameterBySort.getListSize());
        }
    },
    LOW_PRICE_ORDER("lowPriceOrder") {
        @Override
        public List<Product> sortProducts(ProductMapper productMapper, ParameterBySort parameterBySort) {
            return productMapper.findProductListByLowPriceOrder(
                    parameterBySort.getCategoryId(), parameterBySort.getPage(), parameterBySort.getListSize());
        }
    },
    HIGH_PRICE_ORDER("highPriceOrder") {
        @Override
        public List<Product> sortProducts(ProductMapper productMapper, ParameterBySort parameterBySort) {
            return productMapper.findProductListByHighPriceOrder(
                    parameterBySort.getCategoryId(), parameterBySort.getPage(), parameterBySort.getListSize());
        }
    },
    SCORE_ORDER("scoreOrder") {
        @Override
        public List<Product> sortProducts(ProductMapper productMapper, ParameterBySort parameterBySort) {
            return productMapper.findProductListByScoreOrder(
                    parameterBySort.getCategoryId(), parameterBySort.getPage(), parameterBySort.getListSize());
        }
    };

    private static final Map<String, SortType> sortTypeMap =
            Collections.unmodifiableMap(Stream.of(values()).collect(Collectors.toMap(sortType -> sortType.getSorter(), sortType -> sortType)));
    private String sorter;

    SortType(String sorter) {
        this.sorter = sorter;
    }

    public String getSorter() {
        return sorter;
    }

    public static String findSortType(String sorter) {
        SortType sortType = sortTypeMap.get(sorter);
        return sortType.toString();
    }

    public abstract List<Product> sortProducts(ProductMapper productMapper, ParameterBySort parameterBySort);
}
