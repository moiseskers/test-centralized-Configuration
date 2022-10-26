package com.gingapay.splitcoreapplicationapi.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SplitDistribution {

    private String id;
    private String orderId;
    private String organizationId;
    private String organizationIdentity;
    private ZonedDateTime orderPaymentDate;
    private List<Distribution> distributions;

}
