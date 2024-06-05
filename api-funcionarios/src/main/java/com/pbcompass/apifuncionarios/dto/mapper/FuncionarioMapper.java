package com.pbcompass.apifuncionarios.dto.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class FuncionarioMapper {

    private ModelMapper modelMapper;

    public <T, D> D toDto(T entity, Class<D> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }

    public <T, D> T toEntity(D dto, Class<T> entityClass) {
        return modelMapper.map(dto, entityClass);
    }

    public <T, D> List<D> toDtoList(List<T> entityList, Class<D> dtoClass) {
        return entityList.stream()
                .map(entity -> toDto(entity, dtoClass))
                .collect(Collectors.toList());
    }
    
}
