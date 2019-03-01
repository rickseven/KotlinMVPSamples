package id.digisys.android.kotlinmvpsamples.contract.match.detail

import id.digisys.android.kotlinmvpsamples.model.event.Event
import id.digisys.android.kotlinmvpsamples.model.team.Team

interface DetailMatchContract{
    interface View{
        fun displayHomeTeamBadge(team: Team)
        fun displayAwayTeamBadge(team: Team)
        fun displayMatch(event: Event)
        fun setFavorite(isFavorite: Boolean)
        fun hideLoading()
        fun showLoading()
        fun onFavoriteMatchRemoved(isRemoved: Boolean)
        fun onFavoriteMatchAdded(isAdded: Boolean)
    }
    interface Presenter{
        fun onDestroy()
        fun getHomeTeamBadge(idTeam: String)
        fun getAwayTeamBadge(idTeam: String)
        fun getMatch(idEvent: String)
        fun addFavoriteMatch(eventId: String, eventDate: String, eventTime: String, homeTeamId: String, awayTeamId: String, homeTeamName: String, awayTeamName: String, homeTeamScore: String?, awayTeamScore: String?)
        fun removeFavoriteMatch(eventId: String)
        fun isFavorite(idEvent: String)
    }
}