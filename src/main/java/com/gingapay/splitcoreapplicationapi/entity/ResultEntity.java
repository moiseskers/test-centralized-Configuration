package com.gingapay.splitcoreapplicationapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "results")
public class ResultEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(name = "transaction_amount")
    private BigDecimal transactionAmount;

    @Column(name = "transaction_installment_amount")
    private BigDecimal transactionInstallmentAmount;

    @Column(name = "amount_difference")
    private BigDecimal amountDifference;

    @Column(name = "transaction_date")
    private ZonedDateTime transactionDate;

    @Column(name = "payment_type")
    private String paymentType;

    @Column(name = "payment_solution_name")
    private String paymentSolutionName;

    @Column(name = "installments")
    private Integer installments;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "flag")
    private String flag;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "distribution_id")
    private DistributionEntity distribution;
}
