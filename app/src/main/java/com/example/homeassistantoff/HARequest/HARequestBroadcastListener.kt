package com.example.homeassistantoff.HARequest

interface HARequestBroadcastListener {
    fun updateUI(text: String?, status : Boolean)
}