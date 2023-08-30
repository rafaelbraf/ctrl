package com.ctrl.notificationservice.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Notification (
    @SerialName("ObjectID") val objectId: String,
    @SerialName("IntegrationID") val integrationId: String,
    @SerialName("Result") val result: String,
    @SerialName("Status") val status: String
)