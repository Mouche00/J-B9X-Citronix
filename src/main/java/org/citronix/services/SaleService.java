package org.citronix.services;

import org.citronix.dtos.request.SaleRequestDTO;
import org.citronix.dtos.response.SaleResponseDTO;
import org.citronix.models.Sale;

public interface SaleService extends GenericService<Sale, SaleRequestDTO, SaleResponseDTO>{
}
