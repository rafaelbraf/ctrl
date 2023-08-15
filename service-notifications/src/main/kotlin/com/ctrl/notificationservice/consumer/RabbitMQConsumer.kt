package com.ctrl.notificationservice.consumer

import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class RabbitMQConsumer {

    @RabbitListener(queues = ["integrations-notifications"])
    fun receiveMessage(message: String) {
        println("Mensagem recebida $message")
    }

}