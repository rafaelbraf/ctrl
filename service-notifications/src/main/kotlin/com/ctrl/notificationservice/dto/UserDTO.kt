package com.ctrl.notificationservice.dto

import com.ctrl.notificationservice.model.User

data class UserDTO(
    val status: String,
    val data: UserData
)

data class UserData(
    val user: User
)

