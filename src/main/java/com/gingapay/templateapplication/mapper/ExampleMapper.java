package com.gingapay.templateapplication.mapper;

import org.mapstruct.Mapper;

/*
    Map structs docs URL:
        https://mapstruct.org/documentation/reference-guide/

    A mapper interface example:

        @Mapper
        public interface ExampleMapper {

            ExampleMapper INSTANCE = Mappers.getMapper(ExampleMapper.class);

            ExampleEntity toEntity(ExampleResponse response);

            ExampleResponse toResponse(ExampleEntity entity);

            ExampleResponse create(ExampleEntity entity);

            @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
            SplitEntity update(ExampleResponse splitResponse, @MappingTarget ExampleEntity entity);
        }
* */

@Mapper
public interface ExampleMapper { }
