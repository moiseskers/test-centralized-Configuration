package com.gingapay.splitcoreapplicationapi.mapper;

import com.gingapay.splitcoreapplicationapi.dtos.SplitResponse;
import com.gingapay.splitcoreapplicationapi.entity.SplitEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SplitMapper {

    SplitMapper INSTANCE = Mappers.getMapper(SplitMapper.class);

    SplitEntity splitResponseToSplitEntity(SplitResponse splitResponse);

    SplitResponse splitEntityToSplitResponse(SplitEntity splitEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    SplitEntity updateSplitEntityFromSplitResponse(SplitResponse splitResponse, @MappingTarget SplitEntity splitEntity);

    @AfterMapping
    default void linkOrders(@MappingTarget SplitEntity splitEntity) {
        splitEntity.getOrders().forEach(order -> order.setSplit(splitEntity));
    }
}
