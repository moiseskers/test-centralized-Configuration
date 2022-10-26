package com.gingapay.splitcoreapplicationapi.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
public class OrderResponse implements Serializable {
    private final UUID id;
    private final String storesId;
    private final List<ItemResponse> items;
}
