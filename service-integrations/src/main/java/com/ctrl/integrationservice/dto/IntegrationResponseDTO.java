package com.ctrl.integrationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IntegrationResponseDTO {
    private UUID id;
    private String title;
    private String url;
    private String port;
    private Integer interval;
    private String description;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant monitoringAt;
    private Instant nextMonitoringAt;
    private String userId;
}