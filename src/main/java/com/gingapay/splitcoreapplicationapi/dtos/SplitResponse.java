package com.gingapay.splitcoreapplicationapi.dtos;

import com.gingapay.splitcoreapplicationapi.entity.OrderResponse;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class SplitResponse implements Serializable {
    private final UUID id;
    private final String orderId;
    private final String organizationIdentity;
    private final BigDecimal marketPlacePercentage;
    private final BigDecimal total;
    private final List<OrderResponse> orders;
}
