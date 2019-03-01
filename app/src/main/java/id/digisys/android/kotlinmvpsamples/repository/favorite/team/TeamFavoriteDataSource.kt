package id.digisys.android.kotlinmvpsamples.repository.favorite.team

import id.digisys.android.kotlinmvpsamples.model.team.Team
import io.reactivex.Flowable

interface TeamFavoriteDataSource{
    fun getAllTeamFavorite(): Flowable<List<Team>>
    fun createTeamFavorite(
            teamId: String,
            teamName: String,
            teamBadge: String,
            teamManager: String,
            teamStadium: String,
            teamDescription: String
    ): Flowable<Long>
    fun deleteTeamFavorite(idTeam: String): Flowable<Boolean>
    fun getTeamFavorite(idTeam: String): Flowable<Team>
}