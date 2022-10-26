package com.gingapay.splitcoreapplicationapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Data
@Entity
@Table(name = "distributions")
public class DistributionEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(name = "store_id")
    private String storeId;

    @Column(name = "store_identity")
    private String storeIdentity;

    @OneToMany(mappedBy = "distribution", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResultEntity> results = new ArrayList<>();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "split_distribution_id")
    private SplitDistributionEntity splitDistribution;

}
