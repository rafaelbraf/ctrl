package com.ctrl.notificationservice.model

data class User (
    val _id: String?,
    val name: String,
    val email: String,
    val role: String,
    val createdAt: String,
    val updatedAt: String,
    val v: Int
)