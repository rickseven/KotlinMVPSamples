package id.digisys.android.kotlinmvpsamples.contract.team.player

import id.digisys.android.kotlinmvpsamples.model.player.Player

interface PlayerTeamContract{
    interface View{
        fun showLoading()
        fun hideLoading()
        fun displayPlayers(playerList: List<Player>)
    }
    interface Presenter{
        fun getAllPlayer(idTeam: String)
        fun onDestroy()
    }
}