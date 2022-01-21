package com.example.homeassistantoff.data

import android.media.Image

data class CollectedData(
    var createdDateTime : String? = "",
    var temp : Int? = 0,
    var humidity : Int? = 0,
    var gas_smoke : Int? = 0,
    var movement : Movement? = null
)