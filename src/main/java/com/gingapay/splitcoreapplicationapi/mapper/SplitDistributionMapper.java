package com.gingapay.splitcoreapplicationapi.mapper;

import com.gingapay.splitcoreapplicationapi.dtos.*;
import com.gingapay.splitcoreapplicationapi.entity.SplitDistributionEntity;
import com.gingapay.splitcoreapplicationapi.entity.SplitEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Mapper
public interface SplitDistributionMapper {

    SplitDistributionMapper INSTANCE = Mappers.getMapper(SplitDistributionMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "distributions", source = "paymentOrderResponse.purchase", qualifiedByName = "toListDistribution")
    @Mapping(target = "orderId", source = "paymentOrderResponse.id")
    @Mapping(target = "orderPaymentDate", source = "paymentOrderResponse.purchase", qualifiedByName = "toOrderPaymentDate")
    @Mapping(target = "organizationId", source = "splitDistributionRequest.organizationId")
    @Mapping(target = "organizationIdentity", source = "splitDistributionRequest.organizationIdentity")
    SplitDistribution toSplitDistribution(PaymentOrderResponse paymentOrderResponse, SplitDistributionRequest splitDistributionRequest);

    @Mapping(target = "id", source = "entity.id", qualifiedByName = "toId")
    SplitDistribution toSplitDistribution(SplitDistributionEntity entity);

    SplitDistributionRequest toSplitDistributionRequest(SplitEntity entity);

    @Mapping(target = "id", ignore = true)
    SplitDistributionEntity toEntity(SplitDistribution splitDistribution);

    @Mapping(target = "splitDistributionId", source = "splitDistribution.id", qualifiedByName = "toId")
    @Mapping(target = "plataform", constant = "vtex")
    @Mapping(target = "event", constant = "new-split-distribution")
    NewSplitDistribution toNewSplitDistribution(SplitDistributionEntity splitDistribution);

    @Named("toId")
    default String toId(UUID id) {
        return id.toString();
    }

    @Named("toOrderPaymentDate")
    default ZonedDateTime toOrderPaymentDate(Purchase purchase){
        return purchase.getUpdatedAt();
    }

    @Named("toListDistribution")
    default List<Distribution> toListDistribution(Purchase purchase){
        List<Distribution> distributions = new ArrayList<>();
        if(Objects.nonNull(purchase) && Objects.nonNull(purchase.getTransactions())){
            for (Transaction t:purchase.getTransactions()) {
                List<Result> list = new ArrayList<>();
                list.add(Result
                        .builder()
                        .transactionId(t.getTransactionId())
                        .flag(t.getCardBrand())
                        .installments(t.getInstallments())
                        .paymentType(t.getPaymentType())
                        .transactionDate(t.getCreatedAt())
                        .transactionInstallmentAmount(getTransactionInstallment(t))
                        .transactionAmount(t.getAmount())
                        .amountDifference(getAmountDifference(t))
                        .paymentSolutionName(t.getAcquirer())
                        .build());

                distributions.add(Distribution
                        .builder()
                        .storeId(purchase.getStoreId())
                        .storeIdentity(purchase.getStoreIdentity())
                        .results(list)
                        .build());
            }
        }
        return distributions;
    }

    default BigDecimal getAmountDifference(Transaction transaction){
        BigDecimal transactionInstallment = getTransactionInstallment(transaction);
        if(Objects.nonNull(transactionInstallment)) {
            return transaction.getAmount().subtract(transactionInstallment.multiply(new BigDecimal(transaction.getInstallments())));
        }
        return null;
    }

    default BigDecimal getTransactionInstallment(Transaction t){
        if(Objects.nonNull(t.getAmount()) && Objects.nonNull(t.getInstallments())) {
            return t.getAmount().divide(new BigDecimal(t.getInstallments()),2, RoundingMode.HALF_UP);
        }
        return null;
    }
}
