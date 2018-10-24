package com.ampersanda.footballmatchschedule.dataclasses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TeamResponse(
        @SerializedName("teams")
        val teamList: MutableList<Team> = mutableListOf()
) : Parcelable