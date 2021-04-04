package com.microservices.ecommerce.payment.listener;

import com.microservices.ecommerce.payment.entity.PaymentEntity;
import com.microservices.ecommerce.payment.service.PaymentService;
import com.microservices.ecommerce.payment.streaming.CheckoutProcessor;
import com.microservices.ecommerce.checkout.event.CheckoutCreatedEvent;
import com.microservices.ecommerce.payment.event.PaymentCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class CheckoutCreatedListener {

    private final CheckoutProcessor checkoutProcessor;

    private final PaymentService paymentService;

    @StreamListener(CheckoutProcessor.INPUT)
    public void handler(CheckoutCreatedEvent checkoutCreatedEvent) {
        //Processa pagamento gateway
        // salvar os dados de pagamento no bd
        // enviar o evento do pgto processado
        log.info("checkoutCreatedEvent={}", checkoutCreatedEvent);
        final PaymentEntity paymentEntity = paymentService.create(checkoutCreatedEvent).orElseThrow();
        final PaymentCreatedEvent paymentCreatedEvent = PaymentCreatedEvent.newBuilder()
                .setCheckoutCode(paymentEntity.getCheckoutCode())
                .setPaymentCode(paymentEntity.getCode())
                .build();
        checkoutProcessor.output().send(MessageBuilder.withPayload(paymentCreatedEvent).build());
    }
}
