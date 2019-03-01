package id.digisys.android.kotlinmvpsamples.contract.match.last

import id.digisys.android.kotlinmvpsamples.model.event.Event

interface LastMatchContract{
    interface View{
        fun displayLastMatch(eventList: List<Event>)
        fun hideLoading()
        fun showLoading()
    }
    interface Presenter{
        fun onDestroy()
        fun getAllLastMatch(idLeague: String)
    }
}