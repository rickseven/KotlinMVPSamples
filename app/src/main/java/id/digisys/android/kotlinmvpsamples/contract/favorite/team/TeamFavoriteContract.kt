package id.digisys.android.kotlinmvpsamples.contract.favorite.team

import id.digisys.android.kotlinmvpsamples.model.team.Team

interface TeamFavoriteContract{
    interface View{
        fun displayTeamFavorite(teamList: List<Team>)
        fun hideLoading()
        fun showLoading()
    }

    interface Presenter{
        fun getAllTeamFavorite()
        fun onDestroy()
    }
}