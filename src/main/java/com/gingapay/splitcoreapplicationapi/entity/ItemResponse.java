package com.gingapay.splitcoreapplicationapi.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ItemResponse implements Serializable {
    private final UUID id;
    private final BigDecimal productCommission;
    private final BigDecimal shippingCommission;
    private final BigDecimal grossCommission;
    private final BigDecimal grossOrderValue;
    private final BigDecimal splitPercentage;
}
