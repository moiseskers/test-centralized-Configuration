package com.gingapay.splitcoreapplicationapi.controller;

import com.gingapay.splitcoreapplicationapi.dtos.Distribution;
import com.gingapay.splitcoreapplicationapi.dtos.Result;
import com.gingapay.splitcoreapplicationapi.dtos.SplitDistribution;
import com.gingapay.splitcoreapplicationapi.service.SplitDistributionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class SplitDistributionControllerTest {
    @InjectMocks
    SplitDistributionController splitDistributionController;

    @Mock
    SplitDistributionService splitDistributionService;

    @Test
    public void findSplitDistributionsByIdSucess(){
        Mockito.when(splitDistributionService.findById(Mockito.any())).thenReturn(getSplitDistribution());
        ResponseEntity<SplitDistribution> splitDistributionsById = splitDistributionController.findSplitDistributionsById(UUID.randomUUID());
        Assertions.assertNotNull(splitDistributionsById);
        Assertions.assertEquals(splitDistributionsById.getBody().getOrderId(), "orderId");

    }

    private SplitDistribution getSplitDistribution() {
        SplitDistribution splitDistribution = new SplitDistribution();
        splitDistribution.setOrderId("orderId");
        splitDistribution.setId("id");
        splitDistribution.setOrderPaymentDate(ZonedDateTime.now());
        splitDistribution.setDistributions(getDistributions());
        return splitDistribution;
    }

    private List<Distribution> getDistributions() {
        List<Distribution> list = new ArrayList<>();
        list.add(Distribution
                .builder()
                .storeIdentity("storeIdentity").storeId("storeId")
                        .results(getResults())
                .build());
        return list;
    }

    private List<Result> getResults() {
        List<Result> list = new ArrayList<>();
        list.add(Result
                .builder()
                .amountDifference(BigDecimal.ZERO)
                .paymentSolutionName("paymentSolutionName")
                .installments(1)
                .paymentType("paymentType")
                .transactionAmount(BigDecimal.TEN)
                .transactionDate(ZonedDateTime.now())
                .transactionInstallmentAmount(BigDecimal.TEN)
                .build());
        return list;
    }
}
