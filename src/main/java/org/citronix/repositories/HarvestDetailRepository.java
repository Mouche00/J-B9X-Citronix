package org.citronix.repositories;

import org.citronix.models.Harvest;
import org.citronix.models.HarvestDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HarvestDetailRepository extends JpaRepository<HarvestDetail, UUID> {
    @Query("SELECT COUNT(hd) > 0 FROM HarvestDetail hd JOIN hd.tree t JOIN hd.harvest h WHERE t.id = :treeId AND h.id = :harvestId")
    boolean existsByHarvestAndTree(@Param("harvestId") UUID harvestId, @Param("treeId") UUID treeId);
}
