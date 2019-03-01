package id.digisys.android.kotlinmvpsamples.repository.match

import id.digisys.android.kotlinmvpsamples.api.service.TheSportDbService
import id.digisys.android.kotlinmvpsamples.model.event.EventResponse
import id.digisys.android.kotlinmvpsamples.model.event.EventSearchResponse
import io.reactivex.Flowable

class MatchRepository(var theSportDbService: TheSportDbService): MatchDataSource{

    override fun searchMatch(query: String?): Flowable<EventSearchResponse> {
        return theSportDbService.searchEvent(query)
    }

    override fun getMatch(idEvent: String): Flowable<EventResponse> {
        return theSportDbService.getEvent(idEvent)
    }

    override fun getAllLastMatch(idLeague: String): Flowable<EventResponse> {
        return theSportDbService.getAllLastEvent(idLeague)
    }
    override fun getAllNextMatch(idLeague: String): Flowable<EventResponse> {
        return theSportDbService.getAllNextEvent(idLeague)
    }
}