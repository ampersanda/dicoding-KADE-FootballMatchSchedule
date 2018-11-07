package com.ampersanda.footballmatchschedule

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.ampersanda.footballmatchschedule.dataclasses.Event
import com.ampersanda.footballmatchschedule.dataclasses.EventDetails
import com.ampersanda.footballmatchschedule.dataclasses.TeamResponse
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.StringRequestListener
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.db.*
import org.jetbrains.anko.longToast
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton


class DetailActivity : AppCompatActivity() {

    companion object {
        fun newIntent(fromContext: Context, event: Event): Intent {
            val intent = Intent(fromContext, DetailActivity::class.java)
            intent.putExtra(APIConfiguration.eventKey, event)

            return intent
        }
    }

    private lateinit var database: LocalDatabase
    private lateinit var event: Event
    private lateinit var mainMenu: Menu
    private var favCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        database = LocalDatabase(applicationContext)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Match Details"

        event = intent.extras?.getParcelable(APIConfiguration.eventKey) as Event

        database.use {
            select(Event.TABLE_NAME, Event.FAVOURITE_EVENT_ID)
                    .whereArgs("(${Event.FAVOURITE_EVENT_ID} = ${event.idEvent})").exec {
                        parseList(StringParser).forEach {
                            favCount++
                        }
                    }
        }

        detail_match_date.text = APIConfiguration.getFormattedDate(event.dateEvent)

        detail_match_home.text = event.strHomeTeam
        detail_match_away.text = event.strAwayTeam

        detail_match_home_score.text = event.intHomeScore
        detail_match_away_score.text = event.intAwayScore

        detail_match_home_goals.text = APIConfiguration.replaceSemicolonToNewLine(event.strHomeGoalDetails)
        detail_match_away_goals.text = APIConfiguration.replaceSemicolonToNewLine(event.strAwayGoalDetails)

        detail_match_home_shots.text = event.intHomeShots
        detail_match_away_shots.text = event.intAwayShots

        detail_match_home_goal_keeper.text = APIConfiguration.replaceSemicolonToNewLine(event.strHomeLineupGoalkeeper)
        detail_match_away_goal_keeper.text = APIConfiguration.replaceSemicolonToNewLine(event.strAwayLineupGoalkeeper)

        detail_match_home_defense.text = APIConfiguration.replaceSemicolonToNewLine(event.strHomeLineupDefense)
        detail_match_away_defense.text = APIConfiguration.replaceSemicolonToNewLine(event.strAwayLineupDefense)

        detail_match_home_midfield.text = APIConfiguration.replaceSemicolonToNewLine(event.strHomeLineupMidfield)
        detail_match_away_midfield.text = APIConfiguration.replaceSemicolonToNewLine(event.strAwayLineupMidfield)

        detail_match_home_forward.text = APIConfiguration.replaceSemicolonToNewLine(event.strHomeLineupForward)
        detail_match_away_forward.text = APIConfiguration.replaceSemicolonToNewLine(event.strAwayLineupForward)

        detail_match_home_substitutes.text = APIConfiguration.replaceSemicolonToNewLine(event.strHomeLineupSubstitutes)
        detail_match_away_substitutes.text = APIConfiguration.replaceSemicolonToNewLine(event.strAwayLineupSubstitutes)


        AndroidNetworking.get(APIConfiguration.getTeamInformationURL(event.idHomeTeam))
                .build()
                .getAsString(object : StringRequestListener {
                    override fun onResponse(response: String?) {
                        val gson = Gson()
                        val teamResponse: TeamResponse = gson.fromJson(response, TeamResponse::class.java)

                        Picasso.get().load(teamResponse.teamList[0].strTeamBadge).into(detail_match_home_badge)

                    }

                    override fun onError(anError: ANError?) {

                    }
                })

        AndroidNetworking.get(APIConfiguration.getTeamInformationURL(event.idAwayTeam))
                .build()
                .getAsString(object : StringRequestListener {
                    override fun onResponse(response: String?) {
                        val gson = Gson()
                        val teamResponse: TeamResponse = gson.fromJson(response, TeamResponse::class.java)

                        Picasso.get().load(teamResponse.teamList[0].strTeamBadge).into(detail_match_away_badge)

                    }

                    override fun onError(anError: ANError?) {

                    }
                })


        AndroidNetworking.get(APIConfiguration.getEventInformationURL(event.idEvent))
                .build()
                .getAsString(object : StringRequestListener {
                    override fun onResponse(response: String?) {

                        val gson = Gson()
                        val eventDetails: EventDetails = gson.fromJson(response, EventDetails::class.java)

                        detail_match_away_formation.text = eventDetails.strHomeFormation
                        detail_match_home_formation.text = eventDetails.strAwayFormation

                        detail_match_away_formation.visibility = View.VISIBLE
                        detail_match_home_formation.visibility = View.VISIBLE
                    }

                    override fun onError(anError: ANError?) {

                    }

                })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        mainMenu = menu as Menu
        changeStar()

        return super.onCreateOptionsMenu(menu)
    }

    private fun changeStar() {
        if (favCount > 0) {
            mainMenu.getItem(0)?.icon = ContextCompat.getDrawable(applicationContext, R.drawable.ic_star_black_24dp)
        } else {
            mainMenu.getItem(0)?.icon = ContextCompat.getDrawable(applicationContext, R.drawable.ic_star_border_black_24dp)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId

        if (id == android.R.id.home) {
            onBackPressed()
            return true
        } else if (id == R.id.detail_menu_add_fav) {

            if (favCount > 0){
                alert {
                    title = getString(R.string.ask_delete_favourite)
                    message = getString(R.string.ask_sure_delete_favourite)
                    yesButton {
                        favCount = 0

                        database.use {
                            delete(Event.TABLE_NAME, "(${Event.FAVOURITE_EVENT_ID} = ${event.idEvent})")
                        }

                        changeStar()

                    }
                    noButton {}
                }.show()
            } else {
                database.use {
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

                favCount++
                changeStar()

                longToast(getString(R.string.added_to_favourite))
            }

            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
