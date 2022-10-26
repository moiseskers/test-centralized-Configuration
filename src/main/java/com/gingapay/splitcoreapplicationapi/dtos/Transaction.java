package com.gingapay.splitcoreapplicationapi.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Transaction implements Serializable {
    private String id;
    private Integer purchaseId;
    private String paymentType;
    private Boolean isCancellable;
    private String paymentTypeDescription;
    private String externalId;
    private String transactionId;
    private String date;
    private String cardBrand;
    private String bin;
    private String holder;
    private String userReference;
    private String terminalSerialNumber;
    private BigDecimal amount;
    private BigDecimal chargedAmount;
    private String cardApplication;
    private String holderName;
    private String extendedHolderName;
    private Integer installments;
    private String status;
    private String acquirer;
    private String pixCode;
    private String pixQrCodeUrl;
    private String pixQrCodeImageUrl;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private String bankSlipBarCodeNumber;
    private String bankSlipDigitableLine;
    private String bankSlipUrl;
    private String bankSlipDueDate;
    private String purchaseCustomerName;
}
