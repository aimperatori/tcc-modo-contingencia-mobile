package com.example.homeassistantoff.data

import android.os.Parcel
import android.os.Parcelable

data class Movement (
    var movDetected : Boolean? = false,
    var files : List<Files>? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Files::class.java.classLoader) as? List<Files>,
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(movDetected)
        parcel.writeValue(files)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movement> {
        override fun createFromParcel(parcel: Parcel): Movement {
            return Movement(parcel)
        }

        override fun newArray(size: Int): Array<Movement?> {
            return arrayOfNulls(size)
        }
    }
}