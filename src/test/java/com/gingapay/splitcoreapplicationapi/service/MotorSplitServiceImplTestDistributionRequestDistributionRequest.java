package com.gingapay.splitcoreapplicationapi.service;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.gingapay.splitcoreapplicationapi.dtos.NewOrderQueue;
import com.gingapay.splitcoreapplicationapi.dtos.SaleDetailsRequest;
import com.gingapay.splitcoreapplicationapi.entity.SplitEntity;
import com.gingapay.splitcoreapplicationapi.helper.JsonHelper;
import com.gingapay.splitcoreapplicationapi.helper.LoggerHelper;
import com.gingapay.splitcoreapplicationapi.helper.MemoryAppender;
import com.gingapay.splitcoreapplicationapi.repository.SplitRepository;
import com.gingapay.splitcoreapplicationapi.resource.GingapayOrderDetailsClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@Slf4j
class MotorSplitServiceImplTestDistributionRequestDistributionRequest {

    @Mock
    GingapayOrderDetailsClient gingapayOrderDetailsClient;

    @Mock AuthService authService;

    @Mock
    SplitRepository repository;

    @InjectMocks
    MotorSplitServiceImpl service;

    private static MemoryAppender memoryAppender;
    private static final String LOGGER_NAME = "com.gingapay.splitcoreapplicationapi.service";

    @BeforeEach
    public void setup() {
        Logger logger = (Logger) LoggerFactory.getLogger(LOGGER_NAME);
        memoryAppender = new MemoryAppender();
        memoryAppender.setContext((LoggerContext) LoggerFactory.getILoggerFactory());
        logger.setLevel(Level.DEBUG);
        logger.addAppender(memoryAppender);
        memoryAppender.start();
    }

    @AfterEach
    public void cleanUp() {
        memoryAppender.reset();
        memoryAppender.stop();
    }

    @Test
    void newOrderEvent()    {
        Mockito.when(repository.findByOrderId("existing external id")).thenReturn(Optional.of(new SplitEntity()));
        Mockito.when(gingapayOrderDetailsClient.getOrderDetails(null, "not-found")).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        // case if order already exits
        var newOrderQueueEventResponseOrderAlreadyExists = JsonHelper.convertToObject(NewOrderQueue.class, "{\"orderId\":\"existing external id\", \"organizationsIdentity\":\"333\", \"organizationsId\":\"1\", \"event\":\"new-order\", \"platform\":\"vtex\"}");
        service.newOrderEvent(newOrderQueueEventResponseOrderAlreadyExists);
        Assertions.assertEquals( 1, memoryAppender.search("Split for orderId: "+newOrderQueueEventResponseOrderAlreadyExists.getOrderId()+" was already processed", Level.INFO).size());

        // case if order was not found
        var newOrderQueueEventResponseOrderNotFound = JsonHelper.convertToObject(NewOrderQueue.class, "{\"orderId\": \"not-found\", \"event\": \"new-order\", \"platform\": \"vtex\", \"organizationsId\": \"1\"}");
        service.newOrderEvent(newOrderQueueEventResponseOrderNotFound);

        Assertions.assertEquals(1, memoryAppender.searchByMessage("unable to get /api/v1/order-details/find/{}, {}").size());
    }

    @Test
    void process()  {
        var detailsJson = "{\"organizationsIdentity\": \"vtex\", \"orders\": [{\"storeIdentity\": \"Seller A\", \"products\": [{\"totalAmount\": 500, \"shippingCost\": 50, \"quantity\": 1, \"productId\": \"Monitor\", \"unityPrice\": 500, \"shippingCommission\": 15, \"productCommission\": 15}]},{\"storeIdentity\": \"Seller B\", \"products\": [{\"totalAmount\": 300, \"shippingCost\": 20, \"quantity\": 1, \"productId\": \"Teclado\", \"unityPrice\": 300, \"shippingCommission\": 15, \"productCommission\": 15}]},{\"storeIdentity\": \"Seller C\", \"products\": [{\"totalAmount\": 200, \"shippingCost\": 30, \"quantity\": 2, \"productId\": \"Mouse\", \"unityPrice\": 100, \"shippingCommission\": 0, \"productCommission\": 10}]}]}";

        var response = JsonHelper.convertToObject(SaleDetailsRequest.class, detailsJson);
        log.info("sales response input {}", LoggerHelper.json(response));
        assert response != null;

        var splitEntity =  this.service.generateSplitEntity(response, getNewOrderQueue());

        log.info("splitEntity {}", LoggerHelper.json(splitEntity));

        // seller A
        var sellerA = splitEntity.getOrders().stream().filter(orderEntity -> orderEntity.getStoresId().equals("Seller A")).findFirst().orElse(null);
        Assertions.assertEquals(new BigDecimal("42.50"), sellerA.getItems().get(0).getSplitPercentage());

        // seller B
        var sellerB = splitEntity.getOrders().stream().filter(orderEntity -> orderEntity.getStoresId().equals("Seller B")).findFirst().orElse(null);
        Assertions.assertEquals(new BigDecimal("24.73"), sellerB.getItems().get(0).getSplitPercentage());

        // seller C
        var sellerC = splitEntity.getOrders().stream().filter(orderEntity -> orderEntity.getStoresId().equals("Seller C")).findFirst().orElse(null);
        Assertions.assertEquals(new BigDecimal("19.09"), sellerC.getItems().get(0).getSplitPercentage());

        Assertions.assertEquals(new BigDecimal("1100.00"), splitEntity.getTotal());
    }

    NewOrderQueue getNewOrderQueue () {
        var newOrderQueue = new NewOrderQueue();
        newOrderQueue.setOrderId("order-id");
        newOrderQueue.setEvent("new-order-queue");
        newOrderQueue.setPlatform("vtex");
        newOrderQueue.setOrganizationsId("1");
        newOrderQueue.setOrganizationsIdentity("1234");
        return newOrderQueue;
    }


}


