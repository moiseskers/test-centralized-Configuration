package com.gingapay.splitcoreapplicationapi.service;

import com.gingapay.splitcoreapplicationapi.dtos.SplitDistributionRequest;
import com.gingapay.splitcoreapplicationapi.dtos.SplitDistribution;

import java.util.UUID;

public interface SplitDistributionService {

    void process(SplitDistributionRequest splitDistributionRequest);

    SplitDistribution findById(UUID id);

}
