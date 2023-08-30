package com.ctrl.notificationservice.service

import com.ctrl.notificationservice.dto.UserDTO
import com.ctrl.notificationservice.dto.UserDataResponseDTO
import com.ctrl.notificationservice.model.User
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono

@Service
class MyRestService {

    fun makeRequestToGetUserId(integrationId: String): Mono<UserDataResponseDTO> {
        val webClient: WebClient = WebClient.create("http://localhost:8000/api")

        return webClient
            .get()
            .uri("/integrations/user/integration/$integrationId")
            .retrieve()
            .bodyToMono(UserDataResponseDTO::class.java)
    }

    fun makeRequestToGetUserById(userId: String): Mono<UserDTO> {
        val webClient: WebClient = WebClient.create("http://localhost:8080/api")

        return webClient
            .get()
            .uri("/users/user/$userId")
            .retrieve()
            .bodyToMono(UserDTO::class.java)
    }

}