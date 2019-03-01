package id.digisys.android.kotlinmvpsamples.contract.favorite.match

import id.digisys.android.kotlinmvpsamples.model.event.Event

interface MatchFavoriteContract{
    interface View{
        fun displayMatchFavorite(eventList: List<Event>)
        fun hideLoading()
        fun showLoading()
    }

    interface Presenter{
        fun getAllMatchFavorite()
        fun onDestroy()
    }
}