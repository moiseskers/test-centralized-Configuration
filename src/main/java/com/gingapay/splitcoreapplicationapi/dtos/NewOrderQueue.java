package com.gingapay.splitcoreapplicationapi.dtos;

import lombok.Data;

@Data
public class NewOrderQueue {

    private String orderId;
    private String event;
    private String platform;
    private String organizationsId;
    private String organizationsIdentity;
}
