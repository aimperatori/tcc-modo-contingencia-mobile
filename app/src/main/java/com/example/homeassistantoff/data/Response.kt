package com.example.homeassistantoff.data

data class Response(
    var collectedData: List<CollectedData>? = null,
    var exception: Exception? = null
)