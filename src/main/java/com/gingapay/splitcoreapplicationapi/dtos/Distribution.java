package com.gingapay.splitcoreapplicationapi.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Distribution {
    private String storeId;
    private String storeIdentity;
    private List<Result> results;
}
