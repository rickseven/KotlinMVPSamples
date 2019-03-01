package id.digisys.android.kotlinmvpsamples.contract.match.next

import id.digisys.android.kotlinmvpsamples.model.event.Event

interface NextMatchContract{
    interface View{
        fun displayNextMatch(eventList: List<Event>)
        fun hideLoading()
        fun showLoading()
    }
    interface Presenter{
        fun onDestroy()
        fun getAllNextMatch(idLeague: String)
    }
}