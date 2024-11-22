package org.citronix.utils.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

public interface GenericMapper<T, REQ, RES> {
    RES toDTO(T entity);
    T toEntity(REQ req);
    List<RES> toDTOs(List<T> entity);
    List<T> toEntities(List<REQ> req);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(REQ dto, @MappingTarget T entity);
}
