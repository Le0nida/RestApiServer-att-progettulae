package io.swagger.api;

import io.swagger.model.Workstation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkstationRepository extends JpaRepository<Workstation, Long> {
    boolean existsByWorkstation(String workstation); 
}