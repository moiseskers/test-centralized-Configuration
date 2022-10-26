package com.gingapay.splitcoreapplicationapi.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Order {
    private String storeIdentity;
    private List<Product> products = new ArrayList<>();
}
