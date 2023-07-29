package com.ctrl.integrationservice.repository;

import com.ctrl.integrationservice.model.Integration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IntegrationRepository extends JpaRepository<Integration, UUID> {

    @Query("SELECT i FROM Integration i WHERE i.userId = :userId")
    List<Integration> findByUserId(String userId);

}
