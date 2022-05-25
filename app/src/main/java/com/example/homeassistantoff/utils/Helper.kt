package com.example.homeassistantoff.utils

import android.content.Context
import com.example.homeassistantoff.R
import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class Helper {
    companion object {
        fun formatDateTime(_dateTime: String): String {
            val parser = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            parser.timeZone = TimeZone.getTimeZone("UTC+0")

            val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
            formatter.timeZone = TimeZone.getTimeZone("America/Sao_Paulo")

            return formatter.format(parser.parse(_dateTime))
        }

        fun booleanToNoYes(_bool : Boolean, context: Context) : String {
            return if (_bool) {
                context.getString(R.string.Yes)
            } else {
                context.getString(R.string.No)
            }
        }

        fun isImage(_extension : String) : Boolean {
            return listOf(".png", ".jpg", "jpeg").contains(_extension)
        }

        fun isVideo(_extension : String) : Boolean {
            return listOf(".mp4").contains(_extension)
        }
    }
}