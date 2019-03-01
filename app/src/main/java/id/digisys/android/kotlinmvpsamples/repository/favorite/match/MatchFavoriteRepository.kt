package id.digisys.android.kotlinmvpsamples.repository.favorite.match

import android.content.Context
import id.digisys.android.kotlinmvpsamples.db.entity.FavoriteEvent
import id.digisys.android.kotlinmvpsamples.model.event.Event
import id.digisys.android.kotlinmvpsamples.utility.db
import io.reactivex.Flowable
import org.jetbrains.anko.db.*
import java.text.SimpleDateFormat
import java.util.*

class MatchFavoriteRepository(private val context: Context): MatchFavoriteDataSource{
    override fun getMatchFavorite(idEvent: String) : Flowable<Event>{
        var event : Event? = null
        context.db.use {
            select(FavoriteEvent.TABLE)
                    .whereSimple("${FavoriteEvent.EVENT_ID} = ?", idEvent)
                    .parseOpt(object : MapRowParser<Event> {
                        override fun parseRow(columns: Map<String, Any?>): Event {
                            val eventId = columns.getValue("EVENT_ID")
                            val eventDate = columns.getValue("EVENT_DATE")
                            val eventTime = columns.getValue("EVENT_TIME")
                            val homeTeamId = columns.getValue("HOME_TEAM_ID")
                            val awayTeamId = columns.getValue("AWAY_TEAM_ID")
                            val homeTeamName = columns.getValue("HOME_TEAM_NAME")
                            val awayTeamName = columns.getValue("AWAY_TEAM_NAME")
                            val homeTeamScore = columns.getValue("HOME_TEAM_SCORE")
                            val awayTeamScore = columns.getValue("AWAY_TEAM_SCORE")
                            val eventSport = columns.getValue("EVENT_SPORT")

                            val dateEvent: Date? = SimpleDateFormat("dd/MM/yy HH:mm", Locale.US).parse(eventDate.toString() + " " + eventTime.toString())
                            event = Event(
                                idEvent = eventId.toString(),
                                dateEvent = dateEvent,
                                idHomeTeam = homeTeamId.toString(),
                                idAwayTeam = awayTeamId.toString(),
                                strHomeTeam = homeTeamName.toString(),
                                strAwayTeam = awayTeamName.toString(),
                                intHomeScore = homeTeamScore.toString(),
                                intAwayScore = awayTeamScore.toString(),
                                strSport = eventSport.toString())
                            return event!!
                        }
                    })
        }
        return if (event != null) Flowable.just(event) else Flowable.empty<Event>()
    }

    override fun createMatchFavorite(eventId: String, eventDate: String, eventTime: String, homeTeamId: String, awayTeamId: String, homeTeamName: String, awayTeamName: String, homeTeamScore: String?, awayTeamScore: String?): Flowable<Long> {
        var insertedId = -1L
        context.db.use {
            insertedId = insert(FavoriteEvent.TABLE,
                FavoriteEvent.EVENT_ID to eventId,
                FavoriteEvent.EVENT_DATE to eventDate,
                FavoriteEvent.EVENT_TIME to eventTime,
                FavoriteEvent.HOME_TEAM_ID to homeTeamId,
                FavoriteEvent.AWAY_TEAM_ID to awayTeamId,
                FavoriteEvent.HOME_TEAM_NAME to homeTeamName,
                FavoriteEvent.AWAY_TEAM_NAME to awayTeamName,
                FavoriteEvent.HOME_TEAM_SCORE to homeTeamScore,
                FavoriteEvent.AWAY_TEAM_SCORE to awayTeamScore,
                FavoriteEvent.EVENT_SPORT to "Soccer")
        }
        return Flowable.just(insertedId)
    }

    override fun deleteMatchFavorite(idEvent: String): Flowable<Boolean> {
        var isDeleted : Boolean? = null
        context.db.use{
            isDeleted = delete(FavoriteEvent.TABLE,
                    "(EVENT_ID = {idEvent})",
                    "idEvent" to idEvent) > 0
        }
        return Flowable.just(isDeleted)
    }

    override fun getAllMatchFavorite(): Flowable<List<Event>> {
        val eventList = ArrayList<Event>()
        context.db.use {
            select(FavoriteEvent.TABLE).parseList(object : MapRowParser<List<Event>> {
                override fun parseRow(columns: Map<String, Any?>): List<Event> {
                    val eventId = columns.getValue("EVENT_ID")
                    val eventDate = columns.getValue("EVENT_DATE")
                    val eventTime = columns.getValue("EVENT_TIME")
                    val homeTeamId = columns.getValue("HOME_TEAM_ID")
                    val awayTeamId = columns.getValue("AWAY_TEAM_ID")
                    val homeTeamName = columns.getValue("HOME_TEAM_NAME")
                    val awayTeamName = columns.getValue("AWAY_TEAM_NAME")
                    val homeTeamScore = columns.getValue("HOME_TEAM_SCORE")
                    val awayTeamScore = columns.getValue("AWAY_TEAM_SCORE")
                    val eventSport = columns.getValue("EVENT_SPORT")

                    val dateEvent: Date? = SimpleDateFormat("dd/MM/yy HH:mm", Locale.US).parse(eventDate.toString() + " " + eventTime.toString())
                    eventList.add(Event(
                        idEvent = eventId.toString(),
                        dateEvent = dateEvent,
                        strDate = eventDate.toString(),
                        strTime = eventTime.toString(),
                        idHomeTeam = homeTeamId.toString(),
                        idAwayTeam = awayTeamId.toString(),
                        strHomeTeam = homeTeamName.toString(),
                        strAwayTeam = awayTeamName.toString(),
                        intHomeScore = homeTeamScore.toString(),
                        intAwayScore = awayTeamScore.toString(),
                        strSport = eventSport.toString()
                    ))
                    return eventList
                }

            })
        }
        return Flowable.just(eventList)
    }

}