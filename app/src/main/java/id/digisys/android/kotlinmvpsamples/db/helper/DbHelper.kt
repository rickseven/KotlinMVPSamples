package id.digisys.android.kotlinmvpsamples.db.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import id.digisys.android.kotlinmvpsamples.db.entity.FavoriteEvent
import id.digisys.android.kotlinmvpsamples.db.entity.FavoriteTeam
import org.jetbrains.anko.db.*

class DbHelper(context: Context) : ManagedSQLiteOpenHelper(context, "footballclub.db", null, 1){

    companion object {
        private var instance: DbHelper? = null

        @Synchronized
        fun getInstance(ctx: Context) = instance ?: DbHelper(ctx.applicationContext)
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(FavoriteEvent.TABLE, true,
            FavoriteEvent.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavoriteEvent.EVENT_ID to TEXT + UNIQUE,
            FavoriteEvent.EVENT_DATE to TEXT,
            FavoriteEvent.EVENT_TIME to TEXT,
            FavoriteEvent.HOME_TEAM_ID to TEXT,
            FavoriteEvent.AWAY_TEAM_ID to TEXT,
            FavoriteEvent.HOME_TEAM_NAME to TEXT,
            FavoriteEvent.AWAY_TEAM_NAME to TEXT,
            FavoriteEvent.HOME_TEAM_SCORE to TEXT,
            FavoriteEvent.AWAY_TEAM_SCORE to TEXT,
            FavoriteEvent.EVENT_SPORT to TEXT)
        db?.createTable(FavoriteTeam.TABLE, true,
            FavoriteTeam.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavoriteTeam.TEAM_ID to TEXT + UNIQUE,
            FavoriteTeam.TEAM_NAME to TEXT,
            FavoriteTeam.TEAM_BADGE to TEXT,
            FavoriteTeam.TEAM_MANAGER to TEXT,
            FavoriteTeam.TEAM_STADIUM to TEXT,
            FavoriteTeam.TEAM_DESCRIPTION to TEXT,
            FavoriteTeam.TEAM_SPORT to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(FavoriteEvent.TABLE, true)
        db?.dropTable(FavoriteTeam.TABLE, true)
    }

    fun clearDbAndRecreate() {
        clearDb()
        onCreate(writableDatabase)
    }

    fun clearDb() {
        writableDatabase.execSQL("DROP TABLE IF EXISTS "+FavoriteEvent.TABLE)
        writableDatabase.execSQL("DROP TABLE IF EXISTS "+FavoriteTeam.TABLE)
    }

}