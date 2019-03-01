package id.digisys.android.kotlinmvpsamples.repository.team

import id.digisys.android.kotlinmvpsamples.api.service.TheSportDbService
import id.digisys.android.kotlinmvpsamples.model.team.TeamResponse
import io.reactivex.Flowable

class TeamRepository(var theSportDbService: TheSportDbService): TeamDataSource{

    override fun searchTeam(query: String?): Flowable<TeamResponse> {
        return theSportDbService.searchTeam(query)
    }

    override fun getAllTeam(idLeague: String): Flowable<TeamResponse> {
        return theSportDbService.getAllTeam(idLeague)
    }

    override fun getTeam(idTeam: String): Flowable<TeamResponse> {
        return theSportDbService.getTeam(idTeam)
    }
}