package com.gingapay.splitcoreapplicationapi.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result implements Serializable {
    private String transactionId;
    private String flag;
    private BigDecimal transactionAmount;
    private BigDecimal transactionInstallmentAmount;
    private BigDecimal amountDifference;
    private ZonedDateTime transactionDate;
    private String paymentType;
    private String paymentSolutionName;
    private Integer installments;
}
