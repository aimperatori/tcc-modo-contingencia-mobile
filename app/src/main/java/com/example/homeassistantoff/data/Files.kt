package com.example.homeassistantoff.data

import android.os.Parcel
import android.os.Parcelable

data class Files(
    var urlFile: String? = "",
    var extension: String? = ""
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(urlFile)
        parcel.writeString(extension)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Files> {
        override fun createFromParcel(parcel: Parcel): Files {
            return Files(parcel)
        }

        override fun newArray(size: Int): Array<Files?> {
            return arrayOfNulls(size)
        }
    }
}