package com.ampersanda.footballmatchschedule.dataclasses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Event(
        @SerializedName("idEvent")
        var idEvent: String? = null,

        @SerializedName("dateEvent")
        var dateEvent: String? = null,

        @SerializedName("idHomeTeam")
        var idHomeTeam: String? = null,

        @SerializedName("idAwayTeam")
        var idAwayTeam: String? = null,

        @SerializedName("strHomeTeam")
        var strHomeTeam: String? = null,

        @SerializedName("strAwayTeam")
        var strAwayTeam: String? = null,

        @SerializedName("intHomeScore")
        var intHomeScore: String? = null,

        @SerializedName("intAwayScore")
        var intAwayScore: String? = null,

        @SerializedName("strHomeGoalDetails")
        var strHomeGoalDetails: String? = null,

        @SerializedName("strAwayGoalDetails")
        var strAwayGoalDetails: String? = null,

        @SerializedName("intHomeShots")
        var intHomeShots: String? = null,

        @SerializedName("intAwayShots")
        var intAwayShots: String? = null,

        @SerializedName("strHomeLineupGoalkeeper")
        var strHomeLineupGoalkeeper: String? = null,

        @SerializedName("strAwayLineupGoalkeeper")
        var strAwayLineupGoalkeeper: String? = null,

        @SerializedName("strHomeLineupDefense")
        var strHomeLineupDefense: String? = null,

        @SerializedName("strAwayLineupDefense")
        var strAwayLineupDefense: String? = null,

        @SerializedName("strHomeLineupMidfield")
        var strHomeLineupMidfield: String? = null,

        @SerializedName("strAwayLineupMidfield")
        var strAwayLineupMidfield: String? = null,

        @SerializedName("strHomeLineupForward")
        var strHomeLineupForward: String? = null,

        @SerializedName("strAwayLineupForward")
        var strAwayLineupForward: String? = null,

        @SerializedName("strHomeLineupSubstitutes")
        var strHomeLineupSubstitutes: String? = null,

        @SerializedName("strAwayLineupSubstitutes")
        var strAwayLineupSubstitutes: String? = null
) : Parcelable {
    companion object {
        const val TABLE_NAME: String = "FAVOURITE_EVENTS"
        const val FAVOURITE_ID: String = "FAVOURITE_ID"
        const val FAVOURITE_EVENT_ID: String = "FAVOURITE_EVENT_ID"
        const val FAVOURITE_EVENT_DATE: String = "FAVOURITE_EVENT_DATE"

        const val FAVOURITE_HOME_ID: String = "FAVOURITE_HOME_ID"
        const val FAVOURITE_AWAY_ID: String = "FAVOURITE_AWAY_ID"

        const val FAVOURITE_HOME_NAME: String = "FAVOURITE_HOME_NAME"
        const val FAVOURITE_AWAY_NAME: String = "FAVOURITE_AWAY_NAME"

        const val FAVOURITE_HOME_SCORE: String = "FAVOURITE_HOME_SCORE"
        const val FAVOURITE_AWAY_SCORE: String = "FAVOURITE_AWAY_SCORE"

        const val FAVOURITE_HOME_DETAILS: String = "FAVOURITE_HOME_DETAILS"
        const val FAVOURITE_AWAY_DETAILS: String = "FAVOURITE_AWAY_DETAILS"

        const val FAVOURITE_HOME_SHOTS: String = "FAVOURITE_HOME_SHOTS"
        const val FAVOURITE_AWAY_SHOTS: String = "FAVOURITE_AWAY_SHOTS"

        const val FAVOURITE_HOME_LINEUP_GOAL_KEEPER: String = "FAVOURITE_HOME_LINEUP_GOAL_KEEPER"
        const val FAVOURITE_AWAY_LINEUP_GOAL_KEEPER: String = "FAVOURITE_AWAY_LINEUP_GOAL_KEEPER"

        const val FAVOURITE_HOME_LINEUP_DEFENSE: String = "FAVOURITE_HOME_LINEUP_DEFENSE"
        const val FAVOURITE_AWAY_LINEUP_DEFENSE: String = "FAVOURITE_AWAY_LINEUP_DEFENSE"

        const val FAVOURITE_HOME_LINEUP_MIDFIELD: String = "FAVOURITE_HOME_LINEUP_MIDFIELD"
        const val FAVOURITE_AWAY_LINEUP_MIDFIELD: String = "FAVOURITE_AWAY_LINEUP_MIDFIELD"

        const val FAVOURITE_HOME_LINEUP_FORWARD: String = "FAVOURITE_HOME_LINEUP_FORWARD"
        const val FAVOURITE_AWAY_LINEUP_FORWARD: String = "FAVOURITE_AWAY_LINEUP_FORWARD"

        const val FAVOURITE_HOME_LINEUP_SUBSTITUTES: String = "FAVOURITE_HOME_LINEUP_SUBSTITUTES"
        const val FAVOURITE_AWAY_LINEUP_SUBSTITUTES: String = "FAVOURITE_AWAY_LINEUP_SUBSTITUTES"
    }
}