package com.microservices.ecommerce.checkout.entity;


import com.microservices.ecommerce.checkout.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column
    private String code;

    @Column
    @Enumerated(value = EnumType.STRING)
    private Status status;

    @Column
    private Boolean saveAddress;

    @Column
    private Boolean saveInformation;

    @Column
    @CreatedDate
    private LocalDateTime createdAt;

    @Column
    @LastModifiedDate
    private LocalDateTime updatedAt;

//    @OneToOne(cascade = CascadeType.ALL)
//    private ShippingEntity shipping;
//
//    @OneToMany(mappedBy = "checkout", cascade = CascadeType.ALL)
//    private List<CheckoutItemEntity> items;

}
