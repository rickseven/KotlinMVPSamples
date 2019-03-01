package id.digisys.android.kotlinmvpsamples.contract.team.search

import id.digisys.android.kotlinmvpsamples.model.team.Team

interface SearchTeamContract{
    interface View{
        fun displayTeam(teamList: List<Team>)
        fun hideLoading()
        fun showLoading()
    }
    interface Presenter{
        fun onDestroy()
        fun searchTeam(query: String?)
    }
}