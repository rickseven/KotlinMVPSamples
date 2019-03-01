package id.digisys.android.kotlinmvpsamples.repository.favorite.match

import id.digisys.android.kotlinmvpsamples.model.event.Event
import io.reactivex.Flowable

interface MatchFavoriteDataSource{
    fun getAllMatchFavorite(): Flowable<List<Event>>
    fun createMatchFavorite(
            eventId: String,
            eventDate: String,
            eventTime: String,
            homeTeamId: String,
            awayTeamId: String,
            homeTeamName: String,
            awayTeamName: String,
            homeTeamScore: String?,
            awayTeamScore: String?
    ): Flowable<Long>
    fun deleteMatchFavorite(idEvent: String): Flowable<Boolean>
    fun getMatchFavorite(idEvent: String): Flowable<Event>
}