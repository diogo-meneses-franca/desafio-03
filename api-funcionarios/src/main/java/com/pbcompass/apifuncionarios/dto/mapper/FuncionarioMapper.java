package com.pbcompass.apifuncionarios.dto.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FuncionarioMapper {

    public <T, D> D toDto(T entity, Class<D> dtoClass) {
        return new ModelMapper().map(entity, dtoClass);
    }

    public <T, D> T toEntity(D dto, Class<T> entityClass) {
        return new ModelMapper().map(dto, entityClass);
    }

    public <T, D> List<D> toDtoList(List<T> entityList, Class<D> dtoClass) {
        return entityList.stream()
                .map(entity -> toDto(entity, dtoClass))
                .collect(Collectors.toList());
    }

}
