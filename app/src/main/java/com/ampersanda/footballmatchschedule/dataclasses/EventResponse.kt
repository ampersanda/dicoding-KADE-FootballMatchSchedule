package com.ampersanda.footballmatchschedule.dataclasses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EventResponse(
        @SerializedName("events")
        val eventList: MutableList<Event> = mutableListOf()
) : Parcelable