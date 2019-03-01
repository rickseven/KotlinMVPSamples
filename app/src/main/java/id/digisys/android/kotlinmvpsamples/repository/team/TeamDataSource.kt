package id.digisys.android.kotlinmvpsamples.repository.team

import id.digisys.android.kotlinmvpsamples.model.team.TeamResponse
import io.reactivex.Flowable

interface TeamDataSource{
    fun getAllTeam(idLeague: String): Flowable<TeamResponse>
    fun getTeam(idTeam: String): Flowable<TeamResponse>
    fun searchTeam(query: String?): Flowable<TeamResponse>
}