package com.pbcompass.apifuncionarios.dto.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FuncionarioMapper {

    public static <T, D> D toDto(T entity, Class<D> dtoClass) {
        return new ModelMapper().map(entity, dtoClass);
    }

    public static <T, D> T toEntity(D dto, Class<T> entityClass) {
        return new ModelMapper().map(dto, entityClass);
    }

    public static <T, D> List<D> toDtoList(List<T> entityList, Class<D> dtoClass) {
        return entityList.stream()
                .map(entity -> toDto(entity, dtoClass))
                .collect(Collectors.toList());
    }

}
