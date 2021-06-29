package com.flab.daitso.controller;

import com.flab.daitso.dto.order.OrderDto;
import com.flab.daitso.dto.order.OrderRequestDto;
import com.flab.daitso.dto.product.ProductDto;
import com.flab.daitso.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("daitso/order")
public class OrderController {

    @Autowired
    OrderService orderService;


    @PostMapping("/normal")
    public OrderDto orderNormal(@RequestBody ProductDto productDto, @RequestBody OrderRequestDto orderRequestDto){

        return orderService.orderNormal(productDto, orderRequestDto);

    }

    @PostMapping("/rocket")
    public void orderRocket(@RequestBody OrderDto orderDto){

    }

    @PostMapping("/wow")
    public void orderWow(@RequestBody OrderDto orderDto){

    }


}
