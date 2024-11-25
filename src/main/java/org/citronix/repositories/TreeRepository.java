package org.citronix.repositories;

import org.citronix.models.Tree;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TreeRepository extends JpaRepository<Tree, UUID>, JpaSpecificationExecutor<Tree> {
    List<Tree> findByFieldId(UUID fieldId);

    interface Specs {
        static Specification<Tree> byFieldHarvestId(UUID harvestId) {
            return (root, query, builder) ->
                    builder.equal(
                            root.join("field")
                                    .join("harvests")
                                    .get("id"),
                            harvestId);
        }
    }
}
