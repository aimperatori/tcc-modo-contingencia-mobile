package com.example.homeassistantoff.data

import android.os.Parcel
import android.os.Parcelable


data class CollectedData(
    var created : String? = "",
    var value : String? = "",
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(created)
        parcel.writeValue(value)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CollectedData> {
        override fun createFromParcel(parcel: Parcel): CollectedData {
            return CollectedData(parcel)
        }

        override fun newArray(size: Int): Array<CollectedData?> {
            return arrayOfNulls(size)
        }
    }
}