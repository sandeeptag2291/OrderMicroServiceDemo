package com.javatechie.service;

import com.javatechie.common.Payment;
import com.javatechie.common.TransactionRequest;
import com.javatechie.common.TransactionResponse;
import com.javatechie.entity.Order;
import com.javatechie.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate template;

    public TransactionResponse saveOrder(TransactionRequest request) {
        String response="";
        Order order=request.getOrder();
        Payment payment=request.getPayment();
        payment.setOrderId(order.getId());
        payment.setAmount(order.getPrice());
        //rest call
        Payment paymentResponse= template.postForObject("http://localhost:9192/payment/doPayment",payment,Payment.class);
        response= paymentResponse.getPaymentStatus().equals("success")?"payment processing successful and order placed":"there is a failure in payment api";

        orderRepository.save(order);
        return new TransactionResponse(order,paymentResponse.getAmount(), paymentResponse.getTransactionId(), response);
    }

}
