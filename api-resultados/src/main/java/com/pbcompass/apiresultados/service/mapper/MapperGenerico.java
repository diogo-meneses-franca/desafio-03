package com.pbcompass.apiresultados.service.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MapperGenerico {

    public static <T, D> D toDto(T entity, Class<D> dtoClass) {
        return new ModelMapper().map(entity, dtoClass);
    }

    public static <T, D> T toEntity(D dto, Class<T> entityClass) {
        return new ModelMapper().map(dto, entityClass);
    }

}
