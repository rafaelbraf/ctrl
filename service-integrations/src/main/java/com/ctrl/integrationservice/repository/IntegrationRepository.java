package com.ctrl.integrationservice.repository;

import com.ctrl.integrationservice.model.Integration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IntegrationRepository extends JpaRepository<Integration, UUID> {



}
