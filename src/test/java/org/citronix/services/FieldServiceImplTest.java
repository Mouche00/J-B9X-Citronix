package org.citronix.services.implementations;

import org.citronix.dtos.request.FieldRequestDTO;
import org.citronix.dtos.response.FieldResponseDTO;
import org.citronix.events.FieldSaveEvent;
import org.citronix.models.Farm;
import org.citronix.models.Field;
import org.citronix.repositories.FieldRepository;
import org.citronix.utils.mappers.FieldMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FieldServiceImplTest {

    @Mock
    private FieldRepository repository;

    @Mock
    private FieldMapper mapper;

    @Mock
    CompletableFuture<Farm> futureFarm;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private FieldServiceImpl service;

    @Captor
    private ArgumentCaptor<FieldSaveEvent> eventCaptor;

    FieldRequestDTO requestDTO;
    Field field;
    Farm farm;
    FieldResponseDTO responseDTO;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Arrange
        requestDTO = FieldRequestDTO.builder().build();
        field = new Field();
        field.setId(UUID.randomUUID());
        field.setSurfaceArea(50);
        farm = new Farm();
        farm.setId(UUID.randomUUID());
        farm.setSurfaceArea(1000);
        farm.setFields(List.of(
                Field.builder().id(UUID.randomUUID()).surfaceArea(300).build(),
                Field.builder().id(UUID.randomUUID()).surfaceArea(300).build(),
                Field.builder().id(UUID.randomUUID()).surfaceArea(300).build()
        ));
        field.setFarm(farm);
        futureFarm = CompletableFuture.completedFuture(farm);
        responseDTO = FieldResponseDTO.builder().build();

    }

    @Test
    void save_shouldThrowException_WhenFieldsExceedFarmArea() {
        farm.setSurfaceArea(100);
        when(mapper.toEntity(requestDTO)).thenReturn(field);
        doAnswer(invocation -> {
            FieldSaveEvent event = invocation.getArgument(0);
            event.setResult(futureFarm);
            return null;
        }).when(eventPublisher).publishEvent(any(FieldSaveEvent.class));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.save(requestDTO));
        assertEquals("The sum of the fields' areas cannot exceed the total farm area.", exception.getMessage());

        verify(eventPublisher, times(1)).publishEvent(eventCaptor.capture());
        FieldSaveEvent capturedEvent = eventCaptor.getValue();
        assertEquals(farm.getId().toString(), capturedEvent.getFarmId());
    }

    @Test
    void save_shouldThrowException_WhenFieldIsTooLarge() {
        field.setSurfaceArea(2000.0);
        when(mapper.toEntity(requestDTO)).thenReturn(field);
        doAnswer(invocation -> {
            FieldSaveEvent event = invocation.getArgument(0);
            event.setResult(futureFarm);
            return null;
        }).when(eventPublisher).publishEvent(any(FieldSaveEvent.class));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.save(requestDTO));
        assertEquals("Field area cannot exceed 50% of the total farm area.", exception.getMessage());

        verify(eventPublisher, times(1)).publishEvent(eventCaptor.capture());
        FieldSaveEvent capturedEvent = eventCaptor.getValue();
        assertEquals(farm.getId().toString(), capturedEvent.getFarmId());
    }

    @Test
    void save_shouldThrowException_WhenFieldAreTooMany() {
        List<Field> fields = new ArrayList<>();
        for(int i = 0; i <= 10; i++) {
            fields.add(Field.builder().id(UUID.randomUUID()).surfaceArea(300).build());
        }
        farm.setFields(fields);
        when(mapper.toEntity(requestDTO)).thenReturn(field);
        doAnswer(invocation -> {
            FieldSaveEvent event = invocation.getArgument(0);
            event.setResult(futureFarm);
            return null;
        }).when(eventPublisher).publishEvent(any(FieldSaveEvent.class));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> service.save(requestDTO));
        assertEquals("A farm cannot contain more than 10 fields", exception.getMessage());

        verify(eventPublisher, times(1)).publishEvent(eventCaptor.capture());
        FieldSaveEvent capturedEvent = eventCaptor.getValue();
        assertEquals(farm.getId().toString(), capturedEvent.getFarmId());
    }

    @Test
    void save_shouldSaveField_WhenConstraintsAreMet() {

        when(mapper.toEntity(requestDTO)).thenReturn(field);
        doAnswer(invocation -> {
            FieldSaveEvent event = invocation.getArgument(0);
            event.setResult(futureFarm);
            return null;
        }).when(eventPublisher).publishEvent(any(FieldSaveEvent.class));
        when(repository.save(field)).thenReturn(field);
        when(mapper.toDTO(field)).thenReturn(responseDTO);

        FieldResponseDTO result = service.save(requestDTO);

        assertNotNull(result);
        assertEquals(responseDTO, result);
        verify(repository, times(1)).save(field);
        verify(eventPublisher, times(1)).publishEvent(any(FieldSaveEvent.class));
    }
}
