package com.example.homeassistantoff.utils

import java.text.SimpleDateFormat

class Helper {

    companion object {
        fun formatDateTime(_dateTime : String) : String {

            val parser = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")

            val dateTimeFormated: String = formatter.format(parser.parse(_dateTime))

            return dateTimeFormated
        }

        fun booleanToNoYes(_bool : Boolean) : String {
            return if (_bool) {
                "Sim"
            } else {
                "Não"
            }
        }
    }


}