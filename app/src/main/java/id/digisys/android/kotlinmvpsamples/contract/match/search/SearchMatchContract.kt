package id.digisys.android.kotlinmvpsamples.contract.match.search

import id.digisys.android.kotlinmvpsamples.model.event.Event

interface SearchMatchContract{
    interface View{
        fun displayMatch(eventList: List<Event>)
        fun hideLoading()
        fun showLoading()
    }
    interface Presenter{
        fun onDestroy()
        fun searchMatch(query: String?)
    }
}