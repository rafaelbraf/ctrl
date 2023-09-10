package com.ctrl.preferencesnotificationservice.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Notification {

    private String id;
    private String name;
    private boolean active;
    private String userId;

    public Notification(String name, boolean active, String userId) {
        this.name = name;
        this.active = active;
        this.userId = userId;
    }
}
