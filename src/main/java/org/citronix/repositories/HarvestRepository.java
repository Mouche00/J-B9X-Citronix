package org.citronix.repositories;

import org.citronix.models.Field;
import org.citronix.models.Harvest;
import org.citronix.utils.constants.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HarvestRepository extends JpaRepository<Harvest, UUID>, JpaSpecificationExecutor<Harvest> {
    @Query("SELECT COUNT(h) > 0 FROM Harvest h JOIN h.field f WHERE f.id = :fieldId AND h.season = :season")
    boolean existsByFieldAndSeason(@Param("fieldId") UUID fieldId, @Param("season") Season season);

    @Query("SELECT COUNT(h) > 0 FROM Harvest h JOIN h.field f JOIN f.trees t WHERE h.id = :harvestId AND t.id = :treeId")
    boolean existsByTree(@Param("harvestId") UUID harvestId, @Param("treeId") UUID treeId);

}
