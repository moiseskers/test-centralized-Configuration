package com.gingapay.splitcoreapplicationapi.service;

import com.gingapay.splitcoreapplicationapi.dtos.*;
import com.gingapay.splitcoreapplicationapi.entity.ItemEntity;
import com.gingapay.splitcoreapplicationapi.entity.OrderEntity;
import com.gingapay.splitcoreapplicationapi.entity.SplitEntity;
import com.gingapay.splitcoreapplicationapi.mapper.SplitDistributionMapper;
import com.gingapay.splitcoreapplicationapi.mapper.SplitMapper;
import com.gingapay.splitcoreapplicationapi.repository.SplitRepository;
import com.gingapay.splitcoreapplicationapi.resource.GingapayOrderDetailsClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MotorSplitServiceImpl implements MotorSplitService {

    SplitMapper mapper = SplitMapper.INSTANCE;
    SplitDistributionMapper splitDistributionMapper = SplitDistributionMapper.INSTANCE;
    @Autowired private GingapayOrderDetailsClient orderDetailsClient;
    @Autowired private SplitRepository repository;
    @Autowired AuthService authService;
    @Autowired SplitDistributionService splitDistributionService;

    @Transactional
    @Override
    public void newOrderEvent(NewOrderQueue event) {
        try {
            this.repository.findByOrderId(event.getOrderId()).ifPresentOrElse(
                    entity -> log.info("Split for orderId: {} was already processed", event.getOrderId()),
                    () -> {
                        var response = orderDetailsClient.getOrderDetails(authService.getAuthorizationHeaderValue(), event.getOrderId());
                        var splitEntity = this.generateSplitEntity(response, event);
                        var entity = repository.save(splitEntity);
                        startDistributionProcess(entity);
                    });
        } catch (ResponseStatusException responseStatusException) {
            log.info("unable to get /api/v1/order-details/find/{}, {}", event.getOrderId(), responseStatusException);
        } catch (Exception e) {
            log.info("an error has occurred while trying to fetch /api/v1/order-details/find/{}, ex: {}", event.getOrderId(), e);
        }
    }

    void startDistributionProcess(SplitEntity split) {
        SplitDistributionRequest request = splitDistributionMapper.toSplitDistributionRequest(split);
        splitDistributionService.process(request);
    }

    @Override
    public SplitResponse findSplitByOrderId(String orderId) {
        var splitEntity = this.repository.findByOrderId(orderId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "SplitEntity not found on orderId:" + orderId));
        return this.mapper.splitEntityToSplitResponse(splitEntity);
    }

    @Override
    public SplitEntity generateSplitEntity(SaleDetailsRequest response, NewOrderQueue newOrderQueue) {
        var organizationId = response.getOrganizationsIdentity();
        var orders = response.getOrders();
        var splitEntity = new SplitEntity();

        splitEntity.setOrderId(newOrderQueue.getOrderId());
        splitEntity.setOrganizationIdentity(organizationId);
        splitEntity.setPlatform(newOrderQueue.getPlatform());

        var splitDetails = orders.stream().map(order -> process(order, splitEntity)).collect(Collectors.toList());

        var items = splitDetails.stream().flatMap(orderEntity -> orderEntity.getItems().stream()).collect(Collectors.toList());
        var grossCommission = items.stream().map(ItemEntity::getGrossCommission).reduce(BigDecimal.ZERO, BigDecimal::add);
        var grossOrderCommission = splitDetails.stream().flatMap(orderEntity -> orderEntity.getItems().stream()).map(ItemEntity::getGrossOrderValue).reduce(BigDecimal.ZERO, BigDecimal::add).add(grossCommission);

        items.stream().peek(itemEntity -> itemEntity.setSplitPercentage(getSplitPercentage(itemEntity.getGrossOrderValue(), grossOrderCommission))).collect(Collectors.toList());

        splitEntity.setMarketPlacePercentage(getMarketPlaceSplitPercentage(grossCommission, grossOrderCommission));
        splitEntity.setTotal(grossOrderCommission);

        splitEntity.setOrders(splitDetails);
        return splitEntity;
    }

    @Override
    public OrderEntity process(Order order, SplitEntity split) {
        var orderEntity = new OrderEntity();
        orderEntity.setSplit(split);

        orderEntity.setStoresId(order.getStoreIdentity());

        var products = order.getProducts();

        var items = products.stream().map(product -> {
            var item = new ItemEntity();
            item.setOrder(orderEntity);
            item.setProductCommission(getProductCommission(product.getTotalAmount(), product.getProductCommission()));
            item.setShippingCommission(getShippingCommission(product.getShippingCost(), product.getShippingCommission()));
            item.setGrossCommission(item.getProductCommission().add(item.getShippingCommission()));
            item.setGrossOrderValue(getNetOrderValue(product, item.getGrossCommission()));
            return item;
        }).collect(Collectors.toList());

        orderEntity.getItems().addAll(items);
        return orderEntity;
    }

    BigDecimal getMarketPlaceSplitPercentage(BigDecimal marketPlace, BigDecimal totalNetOrderValue) {
        return marketPlace.multiply(new BigDecimal("100")).divide(totalNetOrderValue, 2, RoundingMode.HALF_UP);
    }

    BigDecimal getSplitPercentage(BigDecimal grossOrderValue, BigDecimal totalNetGrossValue) {
        return grossOrderValue.multiply(new BigDecimal("100")).divide(totalNetGrossValue, 2, RoundingMode.HALF_UP);
    }

    BigDecimal getNetOrderValue(Product request, BigDecimal netCommission) {
        var totalAmountPlusSippingCost = request.getTotalAmount().add(request.getShippingCost());
        return totalAmountPlusSippingCost.subtract(netCommission);
    }

    BigDecimal getShippingCommission(BigDecimal amount, BigDecimal commission) {
        return amount.multiply(commission).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
    }

    BigDecimal getProductCommission(BigDecimal amount, BigDecimal commission) {
        return amount.multiply(commission).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
    }
}
