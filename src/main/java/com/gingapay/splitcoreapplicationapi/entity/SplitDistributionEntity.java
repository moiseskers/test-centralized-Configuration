package com.gingapay.splitcoreapplicationapi.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "split_distributions")
public class SplitDistributionEntity extends BaseEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "organization_id")
    private String organizationId;

    @Column(name = "organization_identity")
    private String organizationIdentity;

    @Column(name = "order_payment_date")
    private ZonedDateTime orderPaymentDate;

    @OneToMany(mappedBy = "splitDistribution", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DistributionEntity> distributions = new ArrayList<>();
}
