package org.citronix.repositories;

import org.citronix.models.Harvest;
import org.citronix.models.Tree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TreeRepository extends JpaRepository<Tree, UUID> {
    List<Tree> findByField_Id(UUID fieldId);
}
