package com.ctrl.notificationservice.consumer

import com.ctrl.notificationservice.model.Notification
import com.ctrl.notificationservice.service.MyRestService
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class RabbitMQConsumer(private val restService: MyRestService) {

    @RabbitListener(queues = ["integrations-notifications"])
    fun receiveMessage(message: String) {
        val notification = Json.decodeFromString<Notification>(message)

        processMessage(notification)
    }

    private fun processMessage(notification: Notification) {
        val integrationId = notification.integrationId

        val userId = restService.makeRequestToGetUserId(integrationId).block()?.userId
        userId?.let {
            val user = restService.makeRequestToGetUserById(it).block()?.data?.user
            //TODO: Pegar as informações de notificações do usuário e notificar
        }
    }

}