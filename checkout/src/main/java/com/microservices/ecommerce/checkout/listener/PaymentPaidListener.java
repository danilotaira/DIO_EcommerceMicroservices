package com.microservices.ecommerce.checkout.listener;

import com.microservices.ecommerce.checkout.entity.CheckoutEntity;
import com.microservices.ecommerce.checkout.enums.Status;
import com.microservices.ecommerce.checkout.repository.CheckoutRepository;
import com.microservices.ecommerce.checkout.streaming.PaymentPaidSink;
import com.microservices.ecommerce.payment.event.PaymentCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.annotation.StreamRetryTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentPaidListener {

    private final CheckoutRepository checkoutRepository;

    @StreamListener(PaymentPaidSink.INPUT)
    public void handler(PaymentCreatedEvent event){
        log.info("PaymentCreatedEvent={}", event);
        final CheckoutEntity checkoutEntity = checkoutRepository.findByCode(event.getCheckoutCode().toString()).orElseThrow();
        checkoutEntity.setStatus(Status.APPROVED);
        checkoutRepository.save(checkoutEntity);
    }
}
