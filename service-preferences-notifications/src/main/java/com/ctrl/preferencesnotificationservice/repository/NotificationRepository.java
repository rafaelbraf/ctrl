package com.ctrl.preferencesnotificationservice.repository;

import com.ctrl.preferencesnotificationservice.model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface NotificationRepository extends MongoRepository<Notification, String> {

    @Query("{'userId': ?0}")
    List<Notification> findByUserId(String userId);

}