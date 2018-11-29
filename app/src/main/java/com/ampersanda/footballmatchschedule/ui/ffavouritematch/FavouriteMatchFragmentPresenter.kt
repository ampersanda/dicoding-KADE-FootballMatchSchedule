package com.ampersanda.footballmatchschedule.ui.ffavouritematch

import com.ampersanda.footballmatchschedule.APIConfiguration
import com.ampersanda.footballmatchschedule.LocalDatabase
import com.ampersanda.footballmatchschedule.data.Event
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.select

class FavouriteMatchFragmentPresenter {

    fun selectAllFromLocalDB(database: LocalDatabase, eventList: MutableList<Event>, onDataAvailable: (isDataAvailable : Boolean, eventList : MutableList<Event>) -> Unit ){

        eventList.clear()

        database.use {
            select(Event.TABLE_NAME,
                    Event.FAVOURITE_ID, Event.FAVOURITE_EVENT_ID, Event.FAVOURITE_EVENT_DATE,
                    Event.FAVOURITE_HOME_ID, Event.FAVOURITE_AWAY_ID,
                    Event.FAVOURITE_HOME_NAME, Event.FAVOURITE_AWAY_NAME,
                    Event.FAVOURITE_HOME_SCORE, Event.FAVOURITE_AWAY_SCORE,
                    Event.FAVOURITE_HOME_DETAILS, Event.FAVOURITE_AWAY_DETAILS,
                    Event.FAVOURITE_HOME_SHOTS, Event.FAVOURITE_AWAY_SHOTS,
                    Event.FAVOURITE_HOME_LINEUP_GOAL_KEEPER, Event.FAVOURITE_AWAY_LINEUP_GOAL_KEEPER,
                    Event.FAVOURITE_HOME_LINEUP_DEFENSE, Event.FAVOURITE_AWAY_LINEUP_DEFENSE,
                    Event.FAVOURITE_HOME_LINEUP_MIDFIELD, Event.FAVOURITE_AWAY_LINEUP_MIDFIELD,
                    Event.FAVOURITE_HOME_LINEUP_FORWARD, Event.FAVOURITE_AWAY_LINEUP_FORWARD,
                    Event.FAVOURITE_HOME_LINEUP_SUBSTITUTES, Event.FAVOURITE_AWAY_LINEUP_SUBSTITUTES
            ).exec {
                parseList(object : MapRowParser<String> {
                    override fun parseRow(columns: Map<String, Any?>): String {
                        eventList.add(Event(
                                APIConfiguration.getBlankStringWhenNull(columns[Event.FAVOURITE_EVENT_ID].toString()),
                                APIConfiguration.getBlankStringWhenNull(columns[Event.FAVOURITE_EVENT_DATE].toString()),
                                APIConfiguration.getBlankStringWhenNull(columns[Event.FAVOURITE_HOME_ID].toString()),
                                APIConfiguration.getBlankStringWhenNull(columns[Event.FAVOURITE_AWAY_ID].toString()),
                                APIConfiguration.getBlankStringWhenNull(columns[Event.FAVOURITE_HOME_NAME].toString()),
                                APIConfiguration.getBlankStringWhenNull(columns[Event.FAVOURITE_AWAY_NAME].toString()),
                                APIConfiguration.getBlankStringWhenNull(columns[Event.FAVOURITE_HOME_SCORE].toString()),
                                APIConfiguration.getBlankStringWhenNull(columns[Event.FAVOURITE_AWAY_SCORE].toString()),
                                APIConfiguration.getBlankStringWhenNull(columns[Event.FAVOURITE_HOME_DETAILS].toString()),
                                APIConfiguration.getBlankStringWhenNull(columns[Event.FAVOURITE_AWAY_DETAILS].toString()),
                                APIConfiguration.getBlankStringWhenNull(columns[Event.FAVOURITE_HOME_SHOTS].toString()),
                                APIConfiguration.getBlankStringWhenNull(columns[Event.FAVOURITE_AWAY_SHOTS].toString()),
                                APIConfiguration.getBlankStringWhenNull(columns[Event.FAVOURITE_HOME_LINEUP_GOAL_KEEPER].toString()),
                                APIConfiguration.getBlankStringWhenNull(columns[Event.FAVOURITE_AWAY_LINEUP_GOAL_KEEPER].toString()),
                                APIConfiguration.getBlankStringWhenNull(columns[Event.FAVOURITE_HOME_LINEUP_DEFENSE].toString()),
                                APIConfiguration.getBlankStringWhenNull(columns[Event.FAVOURITE_AWAY_LINEUP_DEFENSE].toString()),
                                APIConfiguration.getBlankStringWhenNull(columns[Event.FAVOURITE_HOME_LINEUP_MIDFIELD].toString()),
                                APIConfiguration.getBlankStringWhenNull(columns[Event.FAVOURITE_AWAY_LINEUP_MIDFIELD].toString()),
                                APIConfiguration.getBlankStringWhenNull(columns[Event.FAVOURITE_HOME_LINEUP_FORWARD].toString()),
                                APIConfiguration.getBlankStringWhenNull(columns[Event.FAVOURITE_AWAY_LINEUP_FORWARD].toString()),
                                APIConfiguration.getBlankStringWhenNull(columns[Event.FAVOURITE_HOME_LINEUP_SUBSTITUTES].toString()),
                                APIConfiguration.getBlankStringWhenNull(columns[Event.FAVOURITE_AWAY_LINEUP_SUBSTITUTES].toString())
                        ))
                        return columns.toString()
                    }
                })
            }
        }

        onDataAvailable(eventList.size > 0, eventList)
    }
}