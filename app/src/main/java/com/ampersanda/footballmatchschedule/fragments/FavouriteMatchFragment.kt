package com.ampersanda.footballmatchschedule.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ampersanda.footballmatchschedule.APIConfiguration
import com.ampersanda.footballmatchschedule.DetailActivity
import com.ampersanda.footballmatchschedule.LocalDatabase
import com.ampersanda.footballmatchschedule.R
import com.ampersanda.footballmatchschedule.adapters.MatchAdapter
import com.ampersanda.footballmatchschedule.dataclasses.Event
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.select

class FavouriteMatchFragment : Fragment() {

    private lateinit var layoutView: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressLoading: ProgressBar
    private lateinit var labelNoData: TextView
    var eventList : MutableList<Event> = mutableListOf()

    private fun setLoading() {
        progressLoading.visibility = View.VISIBLE
        recyclerView.visibility = View.INVISIBLE
        labelNoData.visibility = View.INVISIBLE
    }

    private fun hideLoading() {
        progressLoading.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        layoutView = inflater.inflate(R.layout.fragment_favourite_match, container, false)

        recyclerView = layoutView.findViewById(R.id.frag_recycler_favourite_match)
        progressLoading = layoutView.findViewById(R.id.frag_loading_favourite_match)
        labelNoData = layoutView.findViewById(R.id.frag_label_no_data)

        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        return layoutView
    }

    override fun onStart() {
        super.onStart()

        eventList.clear()

        setLoading()

        val database = LocalDatabase(context!!)

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

        hideLoading()

        if (eventList.size > 0){
            recyclerView.adapter = MatchAdapter(eventList) { event ->
                startActivity(context?.let { DetailActivity.newIntent(it, event) })
            }
        } else {
            recyclerView.visibility = View.INVISIBLE
            labelNoData.visibility = View.VISIBLE
        }
    }
}
