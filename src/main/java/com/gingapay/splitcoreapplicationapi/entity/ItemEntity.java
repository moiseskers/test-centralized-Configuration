package com.gingapay.splitcoreapplicationapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Table(name = "items")
public class ItemEntity extends BaseEntity {

    private static final long serialVersionUID = 3103135728014215715L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(name = "product_commission")
    private BigDecimal productCommission;

    @Column(name = "shipping_commission")
    private BigDecimal shippingCommission;

    @Column(name = "gross_commission")
    private BigDecimal grossCommission;

    @Column(name = "gross_order_value")
    private BigDecimal grossOrderValue;

    @Column(name = "split_percentage")
    private BigDecimal splitPercentage;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "orders_id")
    private OrderEntity order;

}
