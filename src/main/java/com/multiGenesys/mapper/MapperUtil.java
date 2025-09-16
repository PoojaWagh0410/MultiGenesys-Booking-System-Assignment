package com.multiGenesys.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MapperUtil {

      private final ModelMapper modelMapper;

      public <D, E> D toDto(E entity, Class<D> dtoClass) {
            return modelMapper.map(entity, dtoClass);
      }

      public <D, E> E toEntity(D dto, Class<E> entityClass) {
            return modelMapper.map(dto, entityClass);
      }

      public <D, E> List<D> toDtoList(List<E> entities, Class<D> dtoClass) {
            return entities.stream()
                       .map(entity -> modelMapper.map(entity, dtoClass))
                       .collect(Collectors.toList());
      }

      public <D, E> List<E> toEntityList(List<D> dtos, Class<E> entityClass) {
            return dtos.stream()
                       .map(dto -> modelMapper.map(dto, entityClass))
                       .collect(Collectors.toList());
      }

      public <D, E> Set<D> toDtoSet(Set<E> entities, Class<D> dtoClass) {
            return entities.stream()
                       .map(entity -> modelMapper.map(entity, dtoClass))
                       .collect(Collectors.toSet());
      }

      public <D, E> Set<E> toEntitySet(Set<D> dtos, Class<E> entityClass) {
            return dtos.stream()
                       .map(dto -> modelMapper.map(dto, entityClass))
                       .collect(Collectors.toSet());
      }
}
