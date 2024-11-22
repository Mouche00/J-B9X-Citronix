package org.citronix.services;

import org.citronix.exceptions.custom.EntityNotFound;
import org.citronix.utils.mappers.GenericMapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GenericService<T, REQ, RES> {
    JpaRepository<T, UUID> getRepository();
    GenericMapper<T, REQ, RES> getMapper();

    default RES save(REQ farm) {
        return getMapper().toDTO(
                getRepository().save(
                        getMapper().toEntity(farm)));
    }

    default List<RES> saveAll(List<REQ> farms) {
        return getMapper().toDTOs(
                getRepository().saveAll(
                        getMapper().toEntities(farms)));
    }

    default RES update(String id, REQ farm) {
        T entity = findEntityById(id);
        getMapper().updateEntityFromDto(farm, entity);
        return getMapper().toDTO(
                getRepository().save(entity));
    }
    default void delete(String id) {
        T entity = findEntityById(id);
        getRepository().delete(entity);
    }

    default RES findById(String id) {
        return getMapper().toDTO(findEntityById(id));
    }

    default T findEntityById(String id) {
        Optional<T> entity = getRepository().findById(UUID.fromString(id));
        return entity.orElseThrow(() -> new EntityNotFound(id));
    }

    default List<RES> findAll() {
        return getMapper().toDTOs(getRepository().findAll());
    }
}
