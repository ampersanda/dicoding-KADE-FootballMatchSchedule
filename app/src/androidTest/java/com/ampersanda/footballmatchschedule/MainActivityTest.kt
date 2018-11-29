package com.ampersanda.footballmatchschedule

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.ampersanda.footballmatchschedule.R.id.*
import com.ampersanda.footballmatchschedule.ui.main.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun addToFavs() {

        // goto the last match

        onView(withId(main_navigation_last_match))
                .check(matches(isDisplayed()))
                .perform(ViewActions.click())

        fwait()

        // click first item

        onView(withId(frag_recycler_last_match))
                .check(matches(isDisplayed()))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))

        fwait()

        // add to fav

        onView(withId(detail_menu_add_fav))
                .check(matches(isDisplayed()))
                .perform(ViewActions.click())

        pressBack()

        fwait()

        // goto the fav match

        onView(withId(main_navigation_fav))
                .check(matches(isDisplayed()))
                .perform(ViewActions.click())

        fwait()

        // click first item

        onView(withId(frag_recycler_favourite_match))
                .check(matches(isDisplayed()))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))

        fwait()

        // remove from fav

        onView(withId(detail_menu_add_fav))
                .check(matches(isDisplayed()))
                .perform(ViewActions.click())

        onView(withId(android.R.id.button1))
                .perform(click())

        pressBack()

        fwait()


    }

    private fun fwait() {
        // from https://stackoverflow.com/questions/22350713/how-to-create-a-delay-on-click-button-with-esspresso-tests
        try {
            Thread.sleep(2000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}