package com.example.footgate.service;


import com.example.footgate.entities.Order;
import com.example.footgate.response.PaymentResponse;
import com.stripe.exception.StripeException;

public interface PaymentService {
    public PaymentResponse createPaymentLink(Order order) throws StripeException;
}
