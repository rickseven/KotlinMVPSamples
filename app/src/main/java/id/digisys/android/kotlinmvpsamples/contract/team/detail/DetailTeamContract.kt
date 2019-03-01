package id.digisys.android.kotlinmvpsamples.contract.team.detail

import id.digisys.android.kotlinmvpsamples.model.team.Team

interface DetailTeamContract{
    interface View{
        fun displayTeam(team: Team)
        fun setFavorite(isFavorite: Boolean)
        fun onFavoriteTeamRemoved(isRemoved: Boolean)
        fun onFavoriteTeamAdded(isAdded: Boolean)
    }
    interface Presenter{
        fun onDestroy()
        fun getTeam(idTeam: String)
        fun addFavoriteTeam(teamId: String, teamName: String, teamBadge: String, teamManager: String, teamStadium: String, teamDescription: String)
        fun removeFavoriteTeam(teamId: String)
        fun isFavorite(idTeam: String)
    }
}