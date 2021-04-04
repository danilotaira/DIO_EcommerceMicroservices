package com.microservices.ecommerce.checkout.streaming;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.converter.MessageConverter;

public interface CheckoutCreatedSource {
    String OUTPUT = "checkout-created-output";

    @Output(OUTPUT)
    MessageChannel output();
}
