package com.example.homeassistantoff.data

import android.os.Parcel
import android.os.Parcelable


data class CollectedData(
    var id : Int = 0,
    var createdDateTime : String? = "",
    var temp : Int? = 0,
    var humidity : Int? = 0,
    var gas_smoke : Int? = 0,
    var movement : Movement? = null
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Movement::class.java.classLoader) as? Movement,
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(createdDateTime)
        parcel.writeValue(temp)
        parcel.writeValue(humidity)
        parcel.writeValue(gas_smoke)
        parcel.writeValue(movement)
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