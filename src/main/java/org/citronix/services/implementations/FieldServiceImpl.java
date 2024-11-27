package org.citronix.services.implementations;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.citronix.dtos.request.FieldRequestDTO;
import org.citronix.dtos.response.FieldResponseDTO;
import org.citronix.events.FieldSaveEvent;
import org.citronix.events.TreeSaveEvent;
import org.citronix.exceptions.custom.ConstraintFailed;
import org.citronix.models.Farm;
import org.citronix.models.Field;
import org.citronix.repositories.FieldRepository;
import org.citronix.repositories.FieldRepository;
import org.citronix.services.FieldService;
import org.citronix.utils.mappers.FieldMapper;
import org.citronix.utils.mappers.FieldMapper;
import org.citronix.utils.mappers.GenericMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Transactional
@Service
public class FieldServiceImpl extends GenericServiceImpl<Field, FieldRequestDTO, FieldResponseDTO> implements FieldService {
    private final FieldRepository repository;
    private final FieldMapper mapper;
    private final ApplicationEventPublisher eventPublisher;

    private final static int MAX_FIELD_COUNT = 10;

    public FieldServiceImpl(FieldRepository repository, FieldMapper mapper, ApplicationEventPublisher eventPublisher) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public FieldResponseDTO save(FieldRequestDTO req) {
        Field entity = mapper.toEntity(req);
        validateFieldConstraints(entity, entity.getFarm().getId().toString());

//        entity = repository.save(entity);
        return mapper.toDTO(repository.save(entity));
    }

    @Override
    public FieldResponseDTO update(String id, FieldRequestDTO entity) {
        return findAndExecute(id, (foundEntity) -> {
            validateFieldConstraints(mapper.toEntity(entity), foundEntity.getFarm().getId().toString());
            return updateExistingEntity(entity, foundEntity);
        });
    }

    public void validateFieldConstraints(Field field, String farmId) {
        FieldSaveEvent event = new FieldSaveEvent(this, farmId);
        eventPublisher.publishEvent(event);
        try {
            Farm farm = event.getResult().get();
            List<Field> fields = farm.getFields();
            if(fields.size() >= MAX_FIELD_COUNT) {
                throw new IllegalArgumentException("A farm cannot contain more than 10 fields");
            }

            if (field.getSurfaceArea() > (farm.getSurfaceArea() * 0.5)) {
                throw new IllegalArgumentException("Field area cannot exceed 50% of the total farm area.");
            }

            double totalAreaSum = fields.stream().mapToDouble(Field::getSurfaceArea).sum();
            if(totalAreaSum > farm.getSurfaceArea() - field.getSurfaceArea()) {
                throw new IllegalArgumentException("The sum of the fields' areas cannot exceed the total farm area.");
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @EventListener
    public void handleTreeSaveEvent(TreeSaveEvent treeSaveEvent) {
        Field field = findEntityById(treeSaveEvent.getFieldId());
        if(field != null) treeSaveEvent.getResult().complete(field);
    }
}
