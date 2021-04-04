package com.microservices.ecommerce.checkout.services;

import com.microservices.ecommerce.checkout.entity.CheckoutEntity;
import com.microservices.ecommerce.checkout.enums.Status;
import com.microservices.ecommerce.checkout.event.CheckoutCreatedEvent;
import com.microservices.ecommerce.checkout.repository.CheckoutRepository;
import com.microservices.ecommerce.checkout.resource.checkout.CheckoutRequest;
import com.microservices.ecommerce.checkout.streaming.CheckoutCreatedSource;
import com.microservices.ecommerce.checkout.util.UUIDUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CheckoutServiceImpl implements CheckoutService {

    private final CheckoutRepository checkoutRepository;
    private final CheckoutCreatedSource checkoutCreatedSource;
    private final UUIDUtil uuidUtil;

    @Override
    public Optional<CheckoutEntity> create(CheckoutRequest checkoutRequest) {
        log.info("M=create, checkoutRequest={}", checkoutRequest);
        final CheckoutEntity checkoutEntity = CheckoutEntity.builder()
                .code(uuidUtil.createUUID().toString())
                .status(Status.CREATED)
                .saveAddress(checkoutRequest.getSaveAddress())
                .saveInformation(checkoutRequest.getSaveInfo())
//                .shipping(ShippingEntity.builder()
//                        .address(checkoutRequest.getAddress())
//                        .complement(checkoutRequest.getComplement())
//                        .country(checkoutRequest.getCountry())
//                        .state(checkoutRequest.getState())
//                        .cep(checkoutRequest.getCep())
//                        .build())
                .build();
//        checkoutEntity.setItems(checkoutRequest.getProducts()
//                .stream()
//                .map(product -> CheckoutItemEntity.builder()
//                        .checkout(checkoutEntity)
//                        .product(product)
//                        .build())
//                .collect(Collectors.toList()));
        final CheckoutEntity entity = checkoutRepository.save(checkoutEntity);
        final CheckoutCreatedEvent checkoutCreatedEvent = CheckoutCreatedEvent.newBuilder()
                .setCheckoutCode(entity.getCode())
                .setStatus(entity.getStatus().name())
                .build();
        checkoutCreatedSource.output().send(MessageBuilder.withPayload(checkoutCreatedEvent).build());
        return Optional.of(entity);
    }

    @Override
    public Optional<CheckoutEntity> updateStatus(String checkoutCode, Status status) {
        final CheckoutEntity checkoutEntity = checkoutRepository.findByCode(checkoutCode).orElse(CheckoutEntity.builder().build());
        checkoutEntity.setStatus(Status.APPROVED);
        return Optional.of(checkoutRepository.save(checkoutEntity));
    }
}