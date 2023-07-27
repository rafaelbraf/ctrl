package com.ctrl.integrationservice.controller;

import com.ctrl.integrationservice.model.Integration;
import com.ctrl.integrationservice.service.IntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/integrations")
public class IntegrationController {

    @Autowired
    private IntegrationService integrationService;

    @GetMapping
    private ResponseEntity<List<Integration>> findAll() {
        List<Integration> integrationList = integrationService.findAll();

        return ResponseEntity.ok().body(integrationList);
    }

    @GetMapping("/{id}")
    private ResponseEntity<Integration> findById(@RequestParam UUID id) {
        Integration integration = integrationService.findById(id);

        return ResponseEntity.ok().body(integration);
    }

}
