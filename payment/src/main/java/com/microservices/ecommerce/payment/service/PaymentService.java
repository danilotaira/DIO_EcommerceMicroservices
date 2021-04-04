package com.microservices.ecommerce.payment.service;

import com.microservices.ecommerce.checkout.event.CheckoutCreatedEvent;
import com.microservices.ecommerce.payment.entity.PaymentEntity;

import java.util.Optional;

public interface PaymentService {
    Optional<PaymentEntity> create(CheckoutCreatedEvent checkoutCreatedEvent);
}
