package com.microservices.ecommerce.checkout.services;

import com.microservices.ecommerce.checkout.entity.CheckoutEntity;
import com.microservices.ecommerce.checkout.enums.Status;
import com.microservices.ecommerce.checkout.resource.checkout.CheckoutRequest;

import java.util.Optional;

public interface CheckoutService {

    Optional<CheckoutEntity> create(CheckoutRequest checkoutRequest);

    Optional<CheckoutEntity> updateStatus(String checkoutCode, Status status);
}
