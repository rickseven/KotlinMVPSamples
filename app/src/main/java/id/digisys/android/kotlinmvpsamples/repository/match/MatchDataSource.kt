package id.digisys.android.kotlinmvpsamples.repository.match

import id.digisys.android.kotlinmvpsamples.model.event.EventResponse
import id.digisys.android.kotlinmvpsamples.model.event.EventSearchResponse
import io.reactivex.Flowable

interface MatchDataSource{
    fun getAllLastMatch(idLeague: String): Flowable<EventResponse>
    fun getAllNextMatch(idLeague: String): Flowable<EventResponse>
    fun getMatch(idEvent: String): Flowable<EventResponse>
    fun searchMatch(query: String?): Flowable<EventSearchResponse>
}