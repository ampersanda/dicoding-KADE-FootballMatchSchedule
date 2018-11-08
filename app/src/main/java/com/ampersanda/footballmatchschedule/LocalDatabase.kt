package com.ampersanda.footballmatchschedule

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.ampersanda.footballmatchschedule.dataclasses.Event
import org.jetbrains.anko.db.*

class LocalDatabase(context: Context) : ManagedSQLiteOpenHelper(context, "FavouriteMatches.db", null, 1) {

    companion object {
        private var instance: LocalDatabase? = null

        @Synchronized
        fun getInstance(context: Context): LocalDatabase {
            if (instance == null) {
                instance = LocalDatabase(context.applicationContext)
            }
            return instance as LocalDatabase
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(Event.TABLE_NAME, true,
                Event.FAVOURITE_ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                Event.FAVOURITE_EVENT_ID to TEXT + UNIQUE,
                Event.FAVOURITE_EVENT_DATE to TEXT,

                Event.FAVOURITE_HOME_ID to TEXT,
                Event.FAVOURITE_AWAY_ID to TEXT,

                Event.FAVOURITE_HOME_NAME to TEXT,
                Event.FAVOURITE_AWAY_NAME to TEXT,

                Event.FAVOURITE_HOME_SCORE to TEXT,
                Event.FAVOURITE_AWAY_SCORE to TEXT,

                Event.FAVOURITE_HOME_DETAILS to TEXT,
                Event.FAVOURITE_AWAY_DETAILS to TEXT,

                Event.FAVOURITE_HOME_SHOTS to TEXT,
                Event.FAVOURITE_AWAY_SHOTS to TEXT,

                Event.FAVOURITE_HOME_LINEUP_GOAL_KEEPER to TEXT,
                Event.FAVOURITE_AWAY_LINEUP_GOAL_KEEPER to TEXT,

                Event.FAVOURITE_HOME_LINEUP_DEFENSE to TEXT,
                Event.FAVOURITE_AWAY_LINEUP_DEFENSE to TEXT,

                Event.FAVOURITE_HOME_LINEUP_MIDFIELD to TEXT,
                Event.FAVOURITE_AWAY_LINEUP_MIDFIELD to TEXT,

                Event.FAVOURITE_HOME_LINEUP_FORWARD to TEXT,
                Event.FAVOURITE_AWAY_LINEUP_FORWARD to TEXT,

                Event.FAVOURITE_HOME_LINEUP_SUBSTITUTES to TEXT,
                Event.FAVOURITE_AWAY_LINEUP_SUBSTITUTES to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(Event.TABLE_NAME, true)
    }

    val Context.database: LocalDatabase
        get() = LocalDatabase.getInstance(applicationContext)

}