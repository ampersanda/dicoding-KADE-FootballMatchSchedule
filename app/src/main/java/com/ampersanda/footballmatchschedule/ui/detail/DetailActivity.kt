package com.ampersanda.footballmatchschedule.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.ampersanda.footballmatchschedule.APIConfiguration
import com.ampersanda.footballmatchschedule.R
import com.ampersanda.footballmatchschedule.data.Event
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.longToast
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton


class DetailActivity : AppCompatActivity(), DetailView {

    companion object {
        fun newIntent(fromContext: Context, event: Event): Intent {
            val intent = Intent(fromContext, DetailActivity::class.java)
            intent.putExtra(APIConfiguration.eventKey, event)

            return intent
        }
    }

    private lateinit var event: Event
    private lateinit var mainMenu: Menu
    private var favCount = 0
    private lateinit var presenter: DetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        presenter = DetailPresenter(this, applicationContext)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.match_details)

        event = intent.extras?.getParcelable(APIConfiguration.eventKey) as Event

        presenter.getAllFavs(event) {
            favCount++
        }

        updateUI(event)

        presenter.loadBadge(event.idHomeTeam) {
            Picasso.get().load(it.teamList[0].strTeamBadge).into(detail_match_home_badge)
        }

        presenter.loadBadge(event.idAwayTeam) {
            Picasso.get().load(it.teamList[0].strTeamBadge).into(detail_match_away_badge)
        }

        presenter.loadAdditionalEventInfo(event.idEvent) {
            detail_match_away_formation.text = it.strHomeFormation
            detail_match_home_formation.text = it.strAwayFormation

            detail_match_away_formation.visibility = View.VISIBLE
            detail_match_home_formation.visibility = View.VISIBLE
        }
    }

    override fun updateUI(event: Event) {
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
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        mainMenu = menu as Menu
        changeStar()

        return super.onCreateOptionsMenu(menu)
    }

    override fun changeStar() {
        if (favCount > 0) {
            mainMenu.getItem(0)?.icon = ContextCompat.getDrawable(applicationContext, R.drawable.ic_star_black_24dp)
        } else {
            mainMenu.getItem(0)?.icon = ContextCompat.getDrawable(applicationContext, R.drawable.ic_star_border_black_24dp)
        }
    }

    override fun showAlertAskDeleteFavourite() {
        alert {
            title = getString(R.string.ask_delete_favourite)
            message = getString(R.string.ask_sure_delete_favourite)
            yesButton {
                favCount = 0
                presenter.onFavDelete(event)
            }
            noButton {}
        }.show()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId

        if (id == android.R.id.home) {
            onBackPressed()
            return true
        } else if (id == R.id.detail_menu_add_fav) {

            if (favCount > 0) {
                showAlertAskDeleteFavourite()
            } else {
                presenter.insertToDb(event)
                favCount++

                longToast(getString(R.string.added_to_favourite))
            }

            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
