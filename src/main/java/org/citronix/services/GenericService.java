package org.citronix.services;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.citronix.exceptions.custom.EntityNotFound;
import org.citronix.exceptions.custom.ListIsEmpty;
import org.citronix.utils.mappers.GenericMapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

public interface GenericService<T, REQ, RES> {

    RES save(REQ entity);

    List<RES> saveAll(List<REQ> entities);

    RES update(String id, REQ entity);

    RES updateExistingEntity(REQ entity, T foundEntity);

    void delete(String id);

    RES findById(String id);

    T findEntityById(String id);

    List<RES> findAll();

    <R> R executeWithUUID(String id, Function<UUID, R> function);

    <R> R findAndExecute(String id, Function<T, R> function);




//    JpaRepository<T, UUID> getRepository();
//    GenericMapper<T, REQ, RES> getMapper();

//    default RES save(REQ entity) {
//        return getMapper().toDTO(
//                getRepository().save(
//                        getMapper().toEntity(entity)));
//    }
//
//    default List<RES> saveAll(List<REQ> entities) {
//        return getMapper().toDTOs(
//                getRepository().saveAll(
//                        getMapper().toEntities(entities)));
//    }
//
//    default RES update(String id, REQ entity) {
//        T foundEntity = findEntityById(id);
//        getMapper().updateEntityFromDto(entity, foundEntity);
//        return getMapper().toDTO(
//                getRepository().save(foundEntity));
//    }
//
//    default void delete(String id) {
//        T entity = findEntityById(id);
//        getRepository().delete(entity);
//    }
//
//    default RES findById(String id) {
//        return getMapper().toDTO(findEntityById(id));
//    }
//
//    default T findEntityById(String id) {
//        Optional<T> entity = getRepository().findById(UUID.fromString(id));
//        return entity.orElseThrow(() -> new EntityNotFound(id));
//    }
//
//    default List<RES> findAll() {
//        List<T> entities = getRepository().findAll();
//        if(entities.isEmpty()) {
//            throw new ListIsEmpty("entitie");
//        }
//        return getMapper().toDTOs(getRepository().findAll());
//    }
//
//    default <R> R executeWithUUID(String id, Function<UUID, R> function) {
//        UUID uuid = UUID.fromString(id);
//        return function.apply(uuid);
//    }
}
