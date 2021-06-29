package com.flab.daitso.service;

import com.flab.daitso.dto.order.OrderDto;
//import com.flab.daitso.mapper.OrderMapper;
import com.flab.daitso.dto.order.OrderRequestDto;
import com.flab.daitso.dto.product.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    //@Autowired
    //OrderMapper orderMapper;

    public OrderDto orderNormal(ProductDto productDto, OrderRequestDto orderRequestDto){
        //charge nothing more
        double productPrice = productDto.getPrice();
        if (orderRequestDto.getCoupon() != null){
            productPrice *= orderRequestDto.getCoupon().getAmount();
        }


        return null;

    }

    public void orderRocket(OrderDto orderDto){
        //charge extra for the faster delivery
    }

    public void orderWow(OrderDto orderDto){
        //charge more than above
    }
}
