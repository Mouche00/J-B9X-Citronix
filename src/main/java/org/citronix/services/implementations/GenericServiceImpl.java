package org.citronix.services.implementations;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.citronix.exceptions.custom.EntityNotFound;
import org.citronix.exceptions.custom.ListIsEmpty;
import org.citronix.services.GenericService;
import org.citronix.utils.mappers.GenericMapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

@RequiredArgsConstructor
public abstract class GenericServiceImpl<T, REQ, RES> implements GenericService<T, REQ, RES> {
    private final JpaRepository<T, UUID> repository;
    private final GenericMapper<T, REQ, RES> mapper;

    @Override
    public RES save(REQ entity) {
        return mapper.toDTO(
                repository.save(
                        mapper.toEntity(entity)));
    }

    @Override
    public List<RES> saveAll(List<REQ> entities) {
        return mapper.toDTOs(
                repository.saveAll(
                        mapper.toEntities(entities)));
    }

    @Override
    public RES update(String id, REQ entity) {
        return findAndExecute(id, (foundEntity) -> updateExistingEntity(entity, foundEntity));
    }

    public RES updateExistingEntity(REQ entity, T foundEntity) {
        mapper.updateEntityFromDto(entity, foundEntity);
        return mapper.toDTO(
                repository.save(foundEntity));
    }

    @Override
    public void delete(String id) {
        findAndExecute(id, (foundEntity) -> {
            repository.delete(foundEntity);
            return null;
        });
    }

    @Override
    public RES findById(String id) {
        return mapper.toDTO(findEntityById(id));
    }

    @Override
    public T findEntityById(String id) {
        Optional<T> entity = executeWithUUID(id, repository::findById);
        return entity.orElseThrow(() -> new EntityNotFound(id));
    }

    @Override
    public List<RES> findAll() {
        List<T> entities = repository.findAll();
        if(entities.isEmpty()) {
            throw new ListIsEmpty();
        }
        return mapper.toDTOs(repository.findAll());
    }

    @Override
    public <R> R executeWithUUID(String id, Function<UUID, R> function) {
        UUID uuid = UUID.fromString(id);
        return function.apply(uuid);
    }

    public <R> R findAndExecute(String id, Function<T, R> function) {
        T entity = findEntityById(id);
        return function.apply(entity);
    }

    //    private void findAndExecute(String id, Consumer<T> consumer) {
//        T entity = findEntityById(id);
//        consumer.accept(entity);
//    }
}
