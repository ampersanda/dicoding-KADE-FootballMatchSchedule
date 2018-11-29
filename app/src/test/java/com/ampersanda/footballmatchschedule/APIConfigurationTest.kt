package com.ampersanda.footballmatchschedule

import org.junit.Assert.assertEquals
import org.junit.Test

class APIConfigurationTest {

    @Test
    fun getFormattedDate() {
        assertEquals("Sun, 09 Dec 2018", APIConfiguration.getFormattedDate("2018-12-09"))
    }

    @Test
    fun replaceSemicolonToNewLine() {
        assertEquals("a\nb\nc", APIConfiguration.replaceSemicolonToNewLine("a;b;c"))
    }

    @Test
    fun getLastFifteenMatchURL() {
        assertEquals("https://www.thesportsdb.com/api/v1/json/1/eventspastleague.php?id=4328", APIConfiguration.getLastFifteenMatchURL(APIConfiguration.LEAGUE_ENGLISH_PREMIERE))
    }

    @Test
    fun getTeamInformationURL() {
        assertEquals("https://www.thesportsdb.com/api/v1/json/1/lookupteam.php?id=133604", APIConfiguration.getTeamInformationURL("133604"))
    }

    @Test
    fun getEventInformationURL() {
        assertEquals("https://www.thesportsdb.com/api/v1/json/1/lookupevent.php?id=441613", APIConfiguration.getEventInformationURL("441613"))
    }

    @Test
    fun getNextFifteenMatchURL() {
        assertEquals("https://www.thesportsdb.com/api/v1/json/1/eventsnextleague.php?id=4328", APIConfiguration.getNextFifteenMatchURL(APIConfiguration.LEAGUE_ENGLISH_PREMIERE))
    }

    @Test
    fun getBlankStringWhenNull() {
        assertEquals("", APIConfiguration.getBlankStringWhenNull(null))
        assertEquals("", APIConfiguration.getBlankStringWhenNull("null"))
    }
}