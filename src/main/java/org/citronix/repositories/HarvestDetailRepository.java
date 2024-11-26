package org.citronix.repositories;

import org.citronix.models.Harvest;
import org.citronix.models.HarvestDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HarvestDetailRepository extends JpaRepository<HarvestDetail, UUID> {
}
