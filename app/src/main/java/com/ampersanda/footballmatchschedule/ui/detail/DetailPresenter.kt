package com.ampersanda.footballmatchschedule.ui.detail

import android.content.Context
import com.ampersanda.footballmatchschedule.APIConfiguration
import com.ampersanda.footballmatchschedule.LocalDatabase
import com.ampersanda.footballmatchschedule.data.Event
import com.ampersanda.footballmatchschedule.data.EventDetails
import com.ampersanda.footballmatchschedule.data.TeamResponse
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.StringRequestListener
import com.google.gson.Gson
import org.jetbrains.anko.db.*

class DetailPresenter(
        private val fview: DetailView,
        private val context: Context
) {

    private val db: LocalDatabase = LocalDatabase(context)

    fun onFavDelete(event: Event) {
        db.use {
            delete(Event.TABLE_NAME, "(${Event.FAVOURITE_EVENT_ID} = ${event.idEvent})")
        }
        fview.changeStar()
    }

    fun getAllFavs(event: Event, onLoop: () -> Unit = {}) {
        db.use {
            select(Event.TABLE_NAME, Event.FAVOURITE_EVENT_ID)
                    .whereArgs("(${Event.FAVOURITE_EVENT_ID} = ${event.idEvent})").exec {
                        parseList(StringParser).forEach {
                            onLoop()
                        }
                    }
        }
    }

    fun insertToDb(event: Event) {
        db.use {
            insert(Event.TABLE_NAME,
                    Event.FAVOURITE_EVENT_ID to event.idEvent,
                    Event.FAVOURITE_EVENT_DATE to event.dateEvent,
                    Event.FAVOURITE_HOME_ID to event.idHomeTeam,
                    Event.FAVOURITE_AWAY_ID to event.idAwayTeam,
                    Event.FAVOURITE_HOME_NAME to event.strHomeTeam,
                    Event.FAVOURITE_AWAY_NAME to event.strAwayTeam,
                    Event.FAVOURITE_HOME_SCORE to event.intHomeScore,
                    Event.FAVOURITE_AWAY_SCORE to event.intAwayScore,
                    Event.FAVOURITE_HOME_DETAILS to event.strHomeGoalDetails,
                    Event.FAVOURITE_AWAY_DETAILS to event.strAwayGoalDetails,
                    Event.FAVOURITE_HOME_SHOTS to event.intHomeShots,
                    Event.FAVOURITE_AWAY_SHOTS to event.intAwayScore,
                    Event.FAVOURITE_HOME_LINEUP_GOAL_KEEPER to event.strHomeLineupGoalkeeper,
                    Event.FAVOURITE_AWAY_LINEUP_GOAL_KEEPER to event.strAwayLineupGoalkeeper,
                    Event.FAVOURITE_HOME_LINEUP_DEFENSE to event.strHomeLineupDefense,
                    Event.FAVOURITE_AWAY_LINEUP_DEFENSE to event.strAwayLineupDefense,
                    Event.FAVOURITE_HOME_LINEUP_MIDFIELD to event.strHomeLineupMidfield,
                    Event.FAVOURITE_AWAY_LINEUP_MIDFIELD to event.strAwayLineupDefense,
                    Event.FAVOURITE_HOME_LINEUP_FORWARD to event.strHomeLineupForward,
                    Event.FAVOURITE_AWAY_LINEUP_FORWARD to event.strAwayLineupForward,
                    Event.FAVOURITE_HOME_LINEUP_SUBSTITUTES to event.strHomeLineupSubstitutes,
                    Event.FAVOURITE_AWAY_LINEUP_SUBSTITUTES to event.strAwayLineupSubstitutes)
        }

        fview.changeStar()
    }

    fun loadBadge(teamId : String?, onResponse: (teamResponse : TeamResponse) -> Unit){
        AndroidNetworking.get(APIConfiguration.getTeamInformationURL(teamId))
                .build()
                .getAsString(object : StringRequestListener {
                    override fun onResponse(response: String?) {
                        val gson = Gson()
                        val teamResponse: TeamResponse = gson.fromJson(response, TeamResponse::class.java)

                        onResponse(teamResponse)

                    }

                    override fun onError(anError: ANError?) {

                    }
                })
    }

    fun loadAdditionalEventInfo(eventId : String?, onResponse: (eventDetails: EventDetails) -> Unit){
        AndroidNetworking.get(APIConfiguration.getEventInformationURL(eventId))
                .build()
                .getAsString(object : StringRequestListener {
                    override fun onResponse(response: String?) {

                        val gson = Gson()
                        val eventDetails: EventDetails = gson.fromJson(response, EventDetails::class.java)

                        onResponse(eventDetails)

                    }

                    override fun onError(anError: ANError?) {

                    }

                })
    }
}