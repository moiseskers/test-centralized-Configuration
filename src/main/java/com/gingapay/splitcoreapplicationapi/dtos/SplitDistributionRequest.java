package com.gingapay.splitcoreapplicationapi.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gingapay.splitcoreapplicationapi.entity.OrderResponse;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SplitDistributionRequest implements Serializable {

    private UUID id;
    private String orderId;
    private String event;
    private String platform;
    private String organizationId;
    private String organizationIdentity;
    private BigDecimal marketPlacePercentage;
    private BigDecimal total;
    private List<OrderResponse> orders;
}
