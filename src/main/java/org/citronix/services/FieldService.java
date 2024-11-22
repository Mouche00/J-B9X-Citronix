package org.citronix.services;

import org.citronix.dtos.request.FieldRequestDTO;
import org.citronix.dtos.response.FieldResponseDTO;
import org.citronix.models.Field;

public interface FieldService extends GenericService<Field, FieldRequestDTO, FieldResponseDTO> {
}
