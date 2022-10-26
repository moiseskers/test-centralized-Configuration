package com.gingapay.splitcoreapplicationapi.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Purchase implements Serializable {
    private String id;
    private String externalId;
    private String customerName;
    private String customerEmail;
    private String customerIdentity;
    private String phoneNumber;
    private String salesChannel;
    private String storeName;
    private String storeIdentity;
    private String storeId;
    private Integer agentId;
    private String agentEnrollment;
    private String agentName;
    private BigDecimal totalCharged;
    private String status;
    private String returnUrl;
    private String orderId;
    private List<Transaction> transactions;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}
