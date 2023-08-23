package com.ctrl.integrationservice.controller;

import com.ctrl.integrationservice.dto.IntegrationResponseDTO;
import com.ctrl.integrationservice.dto.IntegrationResponseUserIdDTO;
import com.ctrl.integrationservice.model.Integration;
import com.ctrl.integrationservice.service.IntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/integrations")
public class IntegrationController {

    @Autowired
    private IntegrationService integrationService;

    @GetMapping
    private ResponseEntity<List<IntegrationResponseDTO>> findAll() {
        List<IntegrationResponseDTO> integrationResponseDTOList = integrationService.findAll();

        return ResponseEntity.ok().body(integrationResponseDTOList);
    }

    @GetMapping("/{id}")
    private ResponseEntity<IntegrationResponseDTO> findById(@PathVariable UUID id) {
        IntegrationResponseDTO integrationResponseDTO = integrationService.findById(id);

        return ResponseEntity.ok().body(integrationResponseDTO);
    }

    @GetMapping("/user/{userId}")
    private ResponseEntity<List<IntegrationResponseDTO>> findByUserId(@PathVariable String userId) {
        List<IntegrationResponseDTO> integrationResponseDTOList = integrationService.findByUserId(userId);

        return ResponseEntity.ok().body(integrationResponseDTOList);
    }

    @GetMapping("/user/integration/{integrationId}")
    private ResponseEntity<IntegrationResponseUserIdDTO> findUserByIntegrationId(@PathVariable UUID integrationId) {
        IntegrationResponseDTO integrationResponseDTO = integrationService.findById(integrationId);
        IntegrationResponseUserIdDTO integrationResponseUserIdDTO = new IntegrationResponseUserIdDTO(integrationResponseDTO.getUserId());

        return ResponseEntity.ok().body(integrationResponseUserIdDTO);
    }

    @PostMapping("/create")
    private ResponseEntity<IntegrationResponseDTO> create(@RequestBody Integration integration) {
        IntegrationResponseDTO integrationResponseDTOSaved = integrationService.create(integration);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(integrationResponseDTOSaved.getId()).toUri();

        return ResponseEntity.created(uri).body(integrationResponseDTOSaved);
    }

    @DeleteMapping("/{integrationId}")
    private ResponseEntity<Void> delete(@PathVariable UUID integrationId) {
        IntegrationResponseDTO integrationResponseDTO = integrationService.findById(integrationId);

        integrationService.delete(integrationResponseDTO);

        return ResponseEntity.noContent().build();
    }

}
