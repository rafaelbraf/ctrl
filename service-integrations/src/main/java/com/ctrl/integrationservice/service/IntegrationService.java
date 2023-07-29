package com.ctrl.integrationservice.service;

import com.ctrl.integrationservice.model.Integration;
import com.ctrl.integrationservice.repository.IntegrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
public class IntegrationService {

    @Autowired
    private IntegrationRepository integrationRepository;

    public List<Integration> findAll() {
        return integrationRepository.findAll();
    }

    public Integration findById(UUID id) {
        return integrationRepository.findById(id).orElseThrow();
    }

    public List<Integration> findByUserId(String userId) {
        return integrationRepository.findByUserId(userId);
    }

    public Integration create(Integration integration) {
        Instant instantToSave = Instant.now();

        integration.setCreatedAt(instantToSave);
        integration.setUpdatedAt(instantToSave);
        integration.setNextMonitoringAt(instantToSave.plus(integration.getInterval(), ChronoUnit.MINUTES));

        return integrationRepository.save(integration);
    }

}
