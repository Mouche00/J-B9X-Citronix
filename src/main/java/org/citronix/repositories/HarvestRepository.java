package org.citronix.repositories;

import org.citronix.models.Field;
import org.citronix.models.Harvest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HarvestRepository extends JpaRepository<Harvest, UUID> {
}
