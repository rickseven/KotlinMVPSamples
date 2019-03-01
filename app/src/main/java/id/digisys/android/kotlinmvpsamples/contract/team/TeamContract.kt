package id.digisys.android.kotlinmvpsamples.contract.team

import id.digisys.android.kotlinmvpsamples.model.team.Team

interface TeamContract{
    interface View{
        fun displayAllTeam(teamList: List<Team>)
        fun hideLoading()
        fun showLoading()
    }
    interface Presenter{
        fun getAllTeam(idLeague: String)
        fun onDestroy()
    }
}