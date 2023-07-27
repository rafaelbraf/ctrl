package com.ctrl.integrationservice.service;

import com.ctrl.integrationservice.model.Integration;
import com.ctrl.integrationservice.repository.IntegrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
