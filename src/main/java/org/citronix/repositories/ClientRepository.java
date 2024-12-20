package org.citronix.repositories;

import org.citronix.models.Client;
import org.citronix.models.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
}
