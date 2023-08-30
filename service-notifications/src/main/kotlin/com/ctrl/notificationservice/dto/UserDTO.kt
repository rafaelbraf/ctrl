package com.ctrl.notificationservice.dto

import com.ctrl.notificationservice.model.User
import java.time.Instant

data class UserDTO(
    val status: String,
    val data: UserData
)

data class UserData(
    val user: User
)

