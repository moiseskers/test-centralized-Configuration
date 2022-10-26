package com.gingapay.splitcoreapplicationapi.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Product {
    private String productId;
    private String quantity;
    private BigDecimal unityPrice = BigDecimal.ZERO;
    private BigDecimal totalAmount = BigDecimal.ZERO;
    private BigDecimal shippingCost = BigDecimal.ZERO;
    private BigDecimal shippingCommission = BigDecimal.ZERO;
    private BigDecimal productCommission = BigDecimal.ZERO;
}
