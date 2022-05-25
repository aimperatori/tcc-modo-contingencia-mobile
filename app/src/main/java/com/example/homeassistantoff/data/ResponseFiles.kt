package com.example.homeassistantoff.data

data class ResponseFiles(
    var camera: List<Camera>? = null,
    var exception: Exception? = null
)