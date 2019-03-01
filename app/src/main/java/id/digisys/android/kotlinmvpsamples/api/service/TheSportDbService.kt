package id.digisys.android.kotlinmvpsamples.api.service

import id.digisys.android.kotlinmvpsamples.model.event.EventResponse
import id.digisys.android.kotlinmvpsamples.model.event.EventSearchResponse
import id.digisys.android.kotlinmvpsamples.model.player.PlayerResponse
import id.digisys.android.kotlinmvpsamples.model.team.TeamResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface TheSportDbService{
    @GET("eventspastleague.php")
    fun getAllLastEvent(@Query("id") idLeague : String): Flowable<EventResponse>

    @GET("eventsnextleague.php")
    fun getAllNextEvent(@Query("id") idLeague: String): Flowable<EventResponse>

    @GET("lookup_all_teams.php")
    fun getAllTeam(@Query("id") idLeague:String?) : Flowable<TeamResponse>

    @GET("lookupteam.php")
    fun getTeam(@Query("id") idTeam:String) : Flowable<TeamResponse>

    @GET("lookupevent.php")
    fun getEvent(@Query("id") idEvent:String) : Flowable<EventResponse>

    @GET("searchevents.php")
    fun searchEvent(@Query("e") query: String?) : Flowable<EventSearchResponse>

    @GET("searchteams.php")
    fun searchTeam(@Query("t") query: String?) : Flowable<TeamResponse>

    @GET("lookup_all_players.php")
    fun getAllPlayer(@Query("id") idTeam: String) : Flowable<PlayerResponse>

    @GET("lookupplayer.php")
    fun getPlayer(@Query("id") idPlayer: String) : Flowable<PlayerResponse>
}