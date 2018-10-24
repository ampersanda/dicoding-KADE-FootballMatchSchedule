package com.ampersanda.footballmatchschedule

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.ampersanda.footballmatchschedule.dataclasses.Event
import com.ampersanda.footballmatchschedule.dataclasses.TeamResponse
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.StringRequestListener
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity : AppCompatActivity() {

    companion object {
        fun newIntent (fromContext : Context, event : Event) : Intent {
            val intent = Intent(fromContext, DetailActivity::class.java)
            intent.putExtra(APIConfiguration.eventKey, event)

            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Match Details"

        val event : Event = intent.extras?.getParcelable(APIConfiguration.eventKey) as Event

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



    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId

        if (id == android.R.id.home){
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
