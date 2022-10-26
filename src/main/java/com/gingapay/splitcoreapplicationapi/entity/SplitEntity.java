package com.gingapay.splitcoreapplicationapi.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "splits")
public class SplitEntity extends BaseEntity {

    private static final long serialVersionUID = 3103135728014215715L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "organization_identity")
    private String organizationIdentity;

    @Column(name = "platform")
    private String platform;

    @Column(name = "left_for_rounding_percentage")
    private BigDecimal leftForRoundingPercentage = BigDecimal.ZERO;

    @Column(name = "market_place_percentage")
    private BigDecimal marketPlacePercentage = BigDecimal.ZERO;

    @Column(name = "total")
    private BigDecimal total = BigDecimal.ZERO;

    @OneToMany(mappedBy = "split", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderEntity> orders = new ArrayList<>();

}
