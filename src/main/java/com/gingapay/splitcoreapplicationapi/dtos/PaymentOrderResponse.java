package com.gingapay.splitcoreapplicationapi.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentOrderResponse implements Serializable {
    private String id;
    private String externalId;
    private String status;
    private Purchase purchase;
    private String platform;
}
