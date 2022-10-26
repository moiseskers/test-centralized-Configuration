package com.gingapay.splitcoreapplicationapi.dtos;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SaleDetailsRequest {
    private String organizationsIdentity;
    private List<Order> orders;
}


