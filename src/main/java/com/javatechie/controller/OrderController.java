package com.javatechie.controller;

import com.javatechie.entity.Order;
import com.javatechie.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    public Order bookOrder(Order order)
    {
        return orderService.saveOrder(order);
    }


}
