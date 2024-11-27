package org.citronix.dtos.response;

import lombok.Builder;

import java.util.List;

@Builder
public record RevenueDTO(double totalRevenue,
                        List<SaleResponseDTO> sales) {

}
