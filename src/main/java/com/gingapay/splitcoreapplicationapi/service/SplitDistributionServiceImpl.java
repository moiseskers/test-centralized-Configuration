package com.gingapay.splitcoreapplicationapi.service;

import com.gingapay.splitcoreapplicationapi.dtos.*;
import com.gingapay.splitcoreapplicationapi.entity.DistributionEntity;
import com.gingapay.splitcoreapplicationapi.entity.ResultEntity;
import com.gingapay.splitcoreapplicationapi.entity.SplitDistributionEntity;
import com.gingapay.splitcoreapplicationapi.mapper.SplitDistributionMapper;
import com.gingapay.splitcoreapplicationapi.queue.producer.SplitDistributionSqsProducer;
import com.gingapay.splitcoreapplicationapi.repository.SplitDistributionRepository;
import com.gingapay.splitcoreapplicationapi.resource.GingapayPaymentFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SplitDistributionServiceImpl implements SplitDistributionService {


    private final SplitDistributionMapper mapper = SplitDistributionMapper.INSTANCE;

    @Autowired
    private AuthService authorizationService;

    @Autowired
    private GingapayPaymentFeignClient paymentFeignClient;

    @Autowired
    private SplitDistributionSqsProducer splitDistributionSqsProducer;

    @Autowired
    private SplitDistributionRepository splitDistributionRepository;

    public void process(SplitDistributionRequest request) {
        PaymentOrderResponse paymentOrderResponse = paymentFeignClient.findPaymentByOrderId(getAuthorizationHeaderValue(), request.getOrderId());
        List<Transaction> paidTransactions = filterPaidTransactions(paymentOrderResponse);
        paymentOrderResponse.getPurchase().setTransactions(paidTransactions);
        SplitDistribution splitDistribution = this.mapper.toSplitDistribution(paymentOrderResponse, request);
        SplitDistributionEntity splitDistributionEntity = saveSplitDistribution(splitDistribution);
        splitDistributionSqsProducer.sendSplitDistributionQueue(this.mapper.toNewSplitDistribution(splitDistributionEntity));
    }

    private List<Transaction> filterPaidTransactions(PaymentOrderResponse paymentOrderResponse) {
        return paymentOrderResponse.getPurchase().getTransactions().stream().filter(a -> a.getStatus().equals("PAID")).collect(Collectors.toList());
    }

    @Override
    public SplitDistribution findById(UUID id) {
        return this.mapper.toSplitDistribution(splitDistributionRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "SplitDistributionEntity not found on id:" + id)));
    }

    @Transactional
    SplitDistributionEntity saveSplitDistribution(SplitDistribution splitDistribution) {
        SplitDistributionEntity entity = this.mapper.toEntity(splitDistribution);
        setSplitDistributionEntityList(entity);
        return splitDistributionRepository.save(entity);
    }

    private void setSplitDistributionEntityList(SplitDistributionEntity entity) {
        for (DistributionEntity d:entity.getDistributions()) {
            d.setSplitDistribution(entity);
            for (ResultEntity r:d.getResults()) {
                r.setDistribution(d);
            }
        }
    }

    private String getAuthorizationHeaderValue() {
        return authorizationService.getAuthorizationHeaderValue();
    }
}
