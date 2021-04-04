//package com.microservices.ecommerce.checkout.entity;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//
//import javax.persistence.*;
//
//@Entity
//@EntityListeners(AuditingEntityListener.class)
//@Builder
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class CheckoutItemEntity {
//
//    @Id
//    @GeneratedValue(strategy=GenerationType.AUTO)
//    private Long id;
//
//    @Column
//    private String product;
//
//    @ManyToOne
//    private CheckoutEntity checkout;
//}