package com.ampersanda.footballmatchschedule

import java.text.SimpleDateFormat
import java.util.*

object APIConfiguration {

    const val LEAGUE_ENGLISH_PREMIERE: String = "4328"
    const val eventKey = "eventLeague"

    fun getLastFifteenMatchURL(leagueId: String?): String {
        return "${BuildConfig.BASE_URL}/api/v1/json/${BuildConfig.TSDB_API_KEY}/eventspastleague.php?id=$leagueId"
    }

    fun getTeamInformationURL(teamId: String?): String {
        return "${BuildConfig.BASE_URL}/api/v1/json/${BuildConfig.TSDB_API_KEY}/lookupteam.php?id=$teamId"
    }

    fun getEventInformationURL(eventId: String?): String {
        return "${BuildConfig.BASE_URL}/api/v1/json/${BuildConfig.TSDB_API_KEY}/lookupevent.php?id=$eventId"
    }

    fun getNextFifteenMatchURL(leagueId: String?): String {
        return "${BuildConfig.BASE_URL}/api/v1/json/${BuildConfig.TSDB_API_KEY}/eventsnextleague.php?id=$leagueId"
    }

    fun getFormattedDate(dateEvent: String?): String {
        val date = dateEvent?.split("-") as List<String>
        val calendar: Calendar = Calendar.getInstance()

        calendar.set(date[0].toInt(), date[1].toInt(), date[2].toInt(), 0, 0, 0)

        val dayFormatter = SimpleDateFormat("EEE", Locale.US)
        val monthFormatter = SimpleDateFormat("MMMM", Locale.US)
        val dateObj = Date(calendar.timeInMillis)

        return "${dayFormatter.format(dateObj)}, ${date[2]} ${monthFormatter.format(dateObj).substring(0, 3)} ${date[0]}"
    }

    fun replaceSemicolonToNewLine(string: String?): String {
        @Suppress("RegExpRedundantEscape")
        return string?.replace(Regex("""\;\s*"""), "\n") ?: ' '.toString()
    }

    fun getBlankStringWhenNull(s : String?): String {
        if (s == "null"){
            return ""
        } else if (s != null){
            return s
        }

        return ""
    }
}