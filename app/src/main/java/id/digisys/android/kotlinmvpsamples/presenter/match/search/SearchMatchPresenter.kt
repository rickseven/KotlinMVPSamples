package id.digisys.android.kotlinmvpsamples.presenter.match.search

import id.digisys.android.kotlinmvpsamples.contract.match.search.SearchMatchContract
import id.digisys.android.kotlinmvpsamples.model.event.EventSearchResponse
import id.digisys.android.kotlinmvpsamples.repository.match.MatchRepository
import id.digisys.android.kotlinmvpsamples.utility.SchedulerInterface
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.ResourceSubscriber
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import java.util.*

class SearchMatchPresenter(val view: SearchMatchContract.View, val repository: MatchRepository, val scheduler: SchedulerInterface): SearchMatchContract.Presenter{

    private val log = AnkoLogger(this.javaClass)
    private val compositeDisposable = CompositeDisposable()

    override fun onDestroy() {
        compositeDisposable.dispose()
    }

    override fun searchMatch(query: String?) {
        view.showLoading()
        compositeDisposable.add(repository.searchMatch(query?:"")
            .observeOn(scheduler.ui())
            .subscribeOn(scheduler.io())
            .subscribeWith(object : ResourceSubscriber<EventSearchResponse>() {
                override fun onComplete() {
                    view.hideLoading()
                }

                override fun onNext(t: EventSearchResponse) {
                    if(!query.isNullOrEmpty()) {
                        view.displayMatch(t.event ?: Collections.emptyList())
                    }else {
                        view.displayMatch(Collections.emptyList())
                    }
                }

                override fun onError(t: Throwable?) {
                    log.error("searchMatch : "+t!!.message)
                    view.displayMatch(Collections.emptyList())
                    view.hideLoading()
                }
            })
        )
    }

}