package com.ampersanda.footballmatchschedule.dataclasses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EventDetails(
        @SerializedName("idEvent")
        var idEvent: String? = null,

        @SerializedName("strHomeFormation")
        var strHomeFormation: String? = null,

        @SerializedName("strAwayFormation")
        var strAwayFormation: String? = null
) : Parcelable