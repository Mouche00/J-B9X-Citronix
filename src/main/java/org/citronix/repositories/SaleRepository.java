package org.citronix.repositories;

import org.citronix.models.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SaleRepository extends JpaRepository<Sale, UUID> {
    List<Sale> findByHarvestId(UUID harvestId);
}
