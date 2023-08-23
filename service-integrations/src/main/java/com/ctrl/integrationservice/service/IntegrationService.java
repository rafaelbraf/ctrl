package com.ctrl.integrationservice.service;

import com.ctrl.integrationservice.dto.IntegrationResponseDTO;
import com.ctrl.integrationservice.model.Integration;
import com.ctrl.integrationservice.repository.IntegrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class IntegrationService {

    @Autowired
    private IntegrationRepository integrationRepository;

    public List<IntegrationResponseDTO> findAll() {
        List<Integration> listIntegrations = integrationRepository.findAll();
        List<IntegrationResponseDTO> listIntegrationsDTO = new ArrayList<>();

        for (Integration integration : listIntegrations) {
            IntegrationResponseDTO integrationResponseDTO = mapperIntegrationToDTO(integration);

            listIntegrationsDTO.add(integrationResponseDTO);
        }

        return listIntegrationsDTO;
    }

    public IntegrationResponseDTO findById(UUID id) {
        Integration integration = integrationRepository.findById(id).orElseThrow();
        IntegrationResponseDTO integrationResponseDTO = mapperIntegrationToDTO(integration);

        return integrationResponseDTO;
    }

    public List<IntegrationResponseDTO> findByUserId(String userId) {
        List<IntegrationResponseDTO> integrationResponseDTOList = new ArrayList<>();
        List<Integration> integrationList = integrationRepository.findByUserId(userId);

        for (Integration integration : integrationList) {
            IntegrationResponseDTO integrationResponseDTO = mapperIntegrationToDTO(integration);

            integrationResponseDTOList.add(integrationResponseDTO);
        }

        return integrationResponseDTOList;
    }

    public IntegrationResponseDTO create(Integration integration) {
        Instant instantToSave = Instant.now();

        integration.setCreatedAt(instantToSave);
        integration.setUpdatedAt(instantToSave);
        integration.setNextMonitoringAt(instantToSave.plus(integration.getInterval(), ChronoUnit.MINUTES));

        Integration integrationSaved = integrationRepository.save(integration);
        IntegrationResponseDTO integrationResponseDTO = mapperIntegrationToDTO(integrationSaved);

        return integrationResponseDTO;
    }

    public void delete(IntegrationResponseDTO integrationResponseDTO) {
        Integration integration = mapperDTOToIntegration(integrationResponseDTO);

        integrationRepository.delete(integration);
    }

    private IntegrationResponseDTO mapperIntegrationToDTO(Integration integration) {
        IntegrationResponseDTO integrationResponseDTO = new IntegrationResponseDTO();
        integrationResponseDTO.setId(integration.getId());
        integrationResponseDTO.setTitle(integration.getTitle());
        integrationResponseDTO.setUrl(integration.getUrl());
        integrationResponseDTO.setPort(integration.getPort());
        integrationResponseDTO.setInterval(integration.getInterval());
        integrationResponseDTO.setDescription(integration.getDescription());
        integrationResponseDTO.setCreatedAt(integration.getCreatedAt());
        integrationResponseDTO.setUpdatedAt(integration.getUpdatedAt());
        integrationResponseDTO.setMonitoringAt(integration.getMonitoringAt());
        integrationResponseDTO.setNextMonitoringAt(integration.getNextMonitoringAt());
        integrationResponseDTO.setUserId(integration.getUserId());

        return integrationResponseDTO;
    }

    private Integration mapperDTOToIntegration(IntegrationResponseDTO integrationResponseDTO) {
        Integration integration = new Integration();
        integration.setId(integrationResponseDTO.getId());
        integration.setTitle(integrationResponseDTO.getTitle());
        integration.setUrl(integrationResponseDTO.getUrl());
        integration.setPort(integrationResponseDTO.getPort());
        integration.setInterval(integrationResponseDTO.getInterval());
        integration.setDescription(integrationResponseDTO.getDescription());
        integration.setCreatedAt(integrationResponseDTO.getCreatedAt());
        integration.setUpdatedAt(integrationResponseDTO.getUpdatedAt());
        integration.setMonitoringAt(integrationResponseDTO.getMonitoringAt());
        integration.setNextMonitoringAt(integrationResponseDTO.getNextMonitoringAt());
        integration.setUserId(integrationResponseDTO.getUserId());

        return integration;
    }

}
