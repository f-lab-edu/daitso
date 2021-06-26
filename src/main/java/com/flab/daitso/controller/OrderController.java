package com.flab.daitso.controller;

import com.flab.daitso.dto.order.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "daitso/order")
public class OrderController {


    @PostMapping(path = "/normal")
    public void orderNormal(@Valid @RequestBody OrderDto orderDto){

    }

    @PostMapping(path = "/rocket")
    public void orderRocket(@Valid @RequestBody OrderDto orderDto){

    }

    @PostMapping(path = "/wow")
    public void orderWow(@Valid @RequestBody OrderDto orderDto){

    }


}
