package org.citronix.repositories;

import org.citronix.models.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SaleRepository extends JpaRepository<Sale, UUID> {
    List<Sale> findByHarvestId(UUID harvestId);

    @Query("SELECT s FROM Sale s JOIN s.harvest h JOIN h.harvestDetails hd JOIN hd.tree t JOIN t.field fi JOIN fi.farm fa WHERE fa.id = :fieldId")
    List<Sale> findByFarmId(@Param("fieldId") UUID harvestId);
}
