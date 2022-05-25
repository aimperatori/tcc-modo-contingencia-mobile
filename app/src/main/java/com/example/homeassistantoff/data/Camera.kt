package com.example.homeassistantoff.data

import android.os.Parcel
import android.os.Parcelable

data class Camera(
    var from : String? = "",
    var to : String? = "",
    var files : ArrayList<String>? = ArrayList()
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readArrayList(ArrayList::class.java.classLoader) as ArrayList<String>
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(from)
        parcel.writeString(to)
        parcel.writeList(files)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Camera> {
        override fun createFromParcel(parcel: Parcel): Camera {
            return Camera(parcel)
        }

        override fun newArray(size: Int): Array<Camera?> {
            return arrayOfNulls(size)
        }
    }
}