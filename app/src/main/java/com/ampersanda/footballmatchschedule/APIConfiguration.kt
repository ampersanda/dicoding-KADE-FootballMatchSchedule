package com.ampersanda.footballmatchschedule

object APIConfiguration {
    fun getLastFifteenMatchURL(leagueId: String?): String {
        return BuildConfig.BASE_URL + "/api/v1/json/" + BuildConfig.TSDB_API_KEY + "/eventspastleague.php?id=" + leagueId
    }
}