package com.gingapay.splitcoreapplicationapi.service;

import com.gingapay.splitcoreapplicationapi.dtos.PaymentOrderResponse;
import com.gingapay.splitcoreapplicationapi.dtos.Purchase;
import com.gingapay.splitcoreapplicationapi.dtos.SplitDistributionRequest;
import com.gingapay.splitcoreapplicationapi.dtos.Transaction;
import com.gingapay.splitcoreapplicationapi.entity.DistributionEntity;
import com.gingapay.splitcoreapplicationapi.entity.ResultEntity;
import com.gingapay.splitcoreapplicationapi.entity.SplitDistributionEntity;
import com.gingapay.splitcoreapplicationapi.queue.producer.SplitDistributionSqsProducer;
import com.gingapay.splitcoreapplicationapi.repository.SplitDistributionRepository;
import com.gingapay.splitcoreapplicationapi.resource.GingapayPaymentFeignClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class SplitDistributionServiceImplTest {

    @InjectMocks
    SplitDistributionServiceImpl splitDistributionService;

    @Mock
    AuthService authorizationService;

    @Mock
    GingapayPaymentFeignClient paymentFeignClient;

    @Mock
    SplitDistributionSqsProducer splitDistributionSqsProducer;

    @Mock
    SplitDistributionRepository splitDistributionRepository;

    @Test
    public void processSuccess(){
        Mockito.when(authorizationService.getAuthorizationHeaderValue()).thenReturn("authorization");
        Mockito.when(paymentFeignClient.findPaymentByOrderId(Mockito.anyString(),Mockito.anyString())).thenReturn(getPaymentOrderResponse());
        Mockito.when(splitDistributionRepository.save(any())).thenReturn(getSplitDistributionEntity());
        SplitDistributionRequest request = new SplitDistributionRequest();
        request.setOrderId("orderId");
        request.setEvent("event");
        request.setOrganizationId("organizationId");
        request.setOrganizationIdentity("organizationIdentity");
        request.setPlatform("plataform");
        request.setMarketPlacePercentage(BigDecimal.ONE);
        request.setTotal(BigDecimal.ONE);

        splitDistributionService.process(request);

        Mockito.verify(paymentFeignClient).findPaymentByOrderId(Mockito.anyString(),Mockito.anyString());
        Mockito.verify(splitDistributionRepository).save(any());
        Mockito.verify(splitDistributionSqsProducer).sendSplitDistributionQueue(any());

    }

    private SplitDistributionEntity getSplitDistributionEntity() {
        SplitDistributionEntity splitDistribution = new SplitDistributionEntity();
        splitDistribution.setId(UUID.randomUUID());
        splitDistribution.setOrderId("orderId");
        splitDistribution.setOrderPaymentDate(ZonedDateTime.now());
        splitDistribution.setUpdatedAt(ZonedDateTime.now());
        splitDistribution.setCreatedAt(ZonedDateTime.now());
        splitDistribution.setDistributions(getListDistribution());
        return splitDistribution;
    }

    private List<DistributionEntity> getListDistribution() {
        List<DistributionEntity> list = new ArrayList<>();
        DistributionEntity entity = new DistributionEntity();
        entity.setId(UUID.randomUUID());
        entity.setStoreId("storeId");
        entity.setResults(getListResult());
        list.add(entity);
        return list;
    }

    private List<ResultEntity> getListResult() {
        List<ResultEntity> list = new ArrayList<>();
        ResultEntity entity = new ResultEntity();
        entity.setInstallments(1);
        entity.setAmountDifference(BigDecimal.ZERO);
        entity.setPaymentSolutionName("payment solution");
        entity.setPaymentType("payment type");
        entity.setTransactionAmount(BigDecimal.TEN);
        entity.setTransactionDate(ZonedDateTime.now());
        list.add(entity);
        return list;
    }

    private PaymentOrderResponse getPaymentOrderResponse() {
        PaymentOrderResponse response = new PaymentOrderResponse();
        response.setId("id");
        response.setPlatform("plataform");
        response.setExternalId("externalId");
        response.setPurchase(getPurchase());
        return response;
    }

    private Purchase getPurchase() {
        Purchase purchase = new Purchase();
        purchase.setCreatedAt(ZonedDateTime.now());
        purchase.setOrderId("orderId");
        purchase.setStoreId("storeId");
        purchase.setExternalId("externalId");
        purchase.setUpdatedAt(ZonedDateTime.now());
        purchase.setAgentEnrollment("agentEnrollment");
        purchase.setAgentId(1);
        purchase.setAgentName("agentName");
        purchase.setCustomerEmail("customerEmail");
        purchase.setTransactions(getTransactions());
        return purchase;
    }

    private List<Transaction> getTransactions() {
        List<Transaction> list = new ArrayList<>();
        list.add(getTransaction());
        return list;
    }

    private Transaction getTransaction() {
        Transaction transaction = new Transaction();
        transaction.setTransactionId("id");
        transaction.setAmount(BigDecimal.TEN);
        transaction.setCreatedAt(ZonedDateTime.now());
        transaction.setInstallments(6);
        transaction.setPaymentType("CARD");
        transaction.setStatus("PAID");
        return transaction;
    }

//    @InjectMocks
//    SplitDistributionServiceImpl orderDetailsService;
//
//    @Mock
//    VtexFeignClient vtexFeignClient;
//
//    @Mock
//    OrderDetailsRepository repository;
//
//    @Mock
//    SplitDistributionSqsProducer splitDistributionSqsProducer;
//
//    @Mock
//    AccountFeignClient accountFeignClient;
//    @Mock
//    AuthServiceImpl authorizationService;
//
//    @Before
//    public void init() {
//        ReflectionTestUtils.setField(orderDetailsService, "appKey", "appKey");
//        ReflectionTestUtils.setField(orderDetailsService, "appToken", "appToken");
//    }
//
//    @Test
//    public void createSucess() {
//        Mockito.when(repository.save(Mockito.any())).thenReturn(getOrderDetails());
//        OrderDetails orderDetails = orderDetailsService.create(getDocumentResponse());
//        Assert.assertNotNull(orderDetails);
//        Assert.assertNotNull(orderDetails.getOrganizationsIdentity());
//        Mockito.verify(repository).save(Mockito.any());
//    }
//
//    @Test
//    public void createAndSendSucess() {
//        Mockito.when(authorizationService.getAuthorizationHeaderValue()).thenReturn("authorization");
//        Mockito.when(accountFeignClient.getEcommerceStore(Mockito.anyString(),Mockito.anyString(),Mockito.anyString())).thenReturn(getEcommerceStoreResponse());
//        Mockito.when(vtexFeignClient.getOrderIdVtex(Mockito.any(), Mockito.anyString(),Mockito.anyString(),Mockito.anyString())).thenReturn(getResponse1());
//
//        orderDetailsService.process(getOrderRequest());
//        Mockito.verify(repository).save(Mockito.any());
//        Mockito.verify(splitDistributionSqsProducer).sendSplitDistributionQueue(Mockito.any());
//    }
//
//    @Test
//    public void getOrderSucess() {
//        Mockito.when(authorizationService.getAuthorizationHeaderValue()).thenReturn("authorization");
//        Mockito.when(accountFeignClient.getEcommerceStore(Mockito.anyString(),Mockito.anyString(),Mockito.anyString())).thenReturn(getEcommerceStoreResponse());
//        Mockito.when(vtexFeignClient.getOrderIdVtex(Mockito.any(), Mockito.anyString(),Mockito.anyString(),Mockito.anyString())).thenReturn(getResponse1());
//
//        orderDetailsService.process(getOrderRequest());
////        Assert.assertNotNull(order);
////        Assert.assertNotNull(order.getOrganizationsIdentity());
////        Assert.assertEquals("1",order.getOrganizationsIdentity());
//    }
//
//    private ECommerceStoreResponse getEcommerceStoreResponse() {
//        ECommerceStoreResponse response = new ECommerceStoreResponse();
//        response.setEcommerceId(1L);
//        response.setId(1L);
//        response.setEcommerceName("VTEX TESTE");
//        response.setStoreId(1L);
//        response.setAccountId(1L);
//        Map<String,String> map = new HashMap<>();
//        map.put("XVTEXAPIAppKey","appKey");
//        map.put("XVTEXAPIAppToken","appToken");
//        response.setProperties(map);
//        return response;
//    }
//
//    private ResponseEntity<Map> getResponse1() {
//        return ResponseEntity.ok(getResponse());
//    }
//
//    @Test
//    public void getOrderItemsSucess() {
//        Mockito.when(repository.findById(Mockito.any())).thenReturn(getOptional());
//        OrderItemsResponse orderItems = orderDetailsService.getOrderItems("6320d9b456b2c145389098fa");
//        Mockito.verify(repository).findById(Mockito.any());
//    }
//
//    private Optional<OrderDetails> getOptional() {
//        return Optional.of(getOrderDetails());
//    }
//
//    private DocumentResponse getDocumentResponse() {
//        return DocumentResponse.builder()
//                .storesIdentity("1")
//                .organizationsIdentity("1")
//                .body(getResponse())
//                .build();
//    }
//
//    private Map getResponse() {
//        Map<String,Object> map = new HashMap();
//        map.put("orderId","1254623444173-01");
//        map.put("items",getItems());
//        return map;
//    }
//
//    private List<VtexItemsResponse> getItems() {
//        List<VtexItemsResponse> list = new ArrayList<>();
//        VtexItemsResponse item = new VtexItemsResponse();
//        item.setOrderId("1");
//        list.add(item);
//        return list;
//    }
//
//
//    private SplitDistributionRequest getOrderRequest() {
//        SplitDistributionRequest request = new SplitDistributionRequest();
//        request.setOrderExternalId("1");
//        request.setStoresIdentity("1");
//        request.setOrganizationsIdentity("1");
//        request.setPlatform("vtex");
//        return request;
//    }
//
//    private OrderDetails getOrderDetails() {
//        OrderDetails orderDetails = new OrderDetails();
//        orderDetails.setBody(getResponse());
//        orderDetails.setOrganizationsIdentity("1");
//        orderDetails.setStoresIdentity("1");
//        orderDetails.setId(new ObjectId("6320d9b456b2c145389098fa"));
//        return orderDetails;
//    }
}
