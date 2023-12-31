package com.ctrl.integrationservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@Table(name = "integrations")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Integration {
    @Id
    @GeneratedValue(generator = "UUIDGenerator")
    private UUID id;

    private String title;
    private String url;
    private String port;
    private Integer interval;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;

    private Instant createdAt;
    private Instant updatedAt;
    private Instant monitoringAt;
    private Instant nextMonitoringAt;

    @Column(name = "user_id")
    private String userId;
}
