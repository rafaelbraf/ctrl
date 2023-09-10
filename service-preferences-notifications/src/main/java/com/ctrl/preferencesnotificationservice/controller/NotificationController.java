package com.ctrl.preferencesnotificationservice.controller;

import com.ctrl.preferencesnotificationservice.model.Notification;
import com.ctrl.preferencesnotificationservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/preferences-notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping
    private ResponseEntity<List<Notification>> findAll() {
        List<Notification> notificationList = notificationService.findAll();

        return ResponseEntity.ok().body(notificationList);
    }

    @GetMapping("/{id}")
    private ResponseEntity<Notification> findById(@PathVariable String id) {
        Notification notification = notificationService.findById(id);

        return ResponseEntity.ok().body(notification);
    }

    @GetMapping("/user/{userId}")
    private ResponseEntity<List<Notification>> findByUserId(@PathVariable String userId) {
        List<Notification> notificationList = notificationService.findByUserId(userId);

        return ResponseEntity.ok().body(notificationList);
    }

    @PostMapping("/create")
    private ResponseEntity<Notification> insert(@RequestBody Notification notification) {
        Notification notificationSaved = notificationService.insert(notification);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(notificationSaved.getId())
                .toUri();

        return ResponseEntity.created(uri).body(notificationSaved);
    }

    @PutMapping("/update")
    private ResponseEntity<Object> update(@RequestBody Notification notification) {
        Notification notificationUpdated = notificationService.update(notification);

        if (notificationUpdated == null) {
            Map<String, String> messageToBody = new HashMap<>();
            messageToBody.put("status", "Not found");
            messageToBody.put("message", "Notificação não encontrada.");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageToBody);
        }

        return ResponseEntity.ok().body(notificationUpdated);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> delete(@PathVariable String id) {
        notificationService.delete(id);

        return ResponseEntity.noContent().build();
    }

}
