package id.digisys.android.kotlinmvpsamples.presenter.match.last

import id.digisys.android.kotlinmvpsamples.model.event.EventResponse
import id.digisys.android.kotlinmvpsamples.repository.match.MatchRepository
import id.digisys.android.kotlinmvpsamples.contract.match.last.LastMatchContract
import id.digisys.android.kotlinmvpsamples.utility.SchedulerInterface
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.ResourceSubscriber
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import java.util.*

class LastMatchPresenter(val view: LastMatchContract.View, val repository: MatchRepository, val scheduler: SchedulerInterface): LastMatchContract.Presenter{

    private val log = AnkoLogger(this.javaClass)
    private val compositeDisposable = CompositeDisposable()

    override fun onDestroy(){
        compositeDisposable.dispose()
    }
    override fun getAllLastMatch(idLeague: String){
        view.showLoading()
        compositeDisposable.add(repository.getAllLastMatch(idLeague)
                .observeOn(scheduler.ui())
                .subscribeOn(scheduler.io())
                .subscribeWith(object : ResourceSubscriber<EventResponse>(){
                    override fun onComplete() {
                        view.hideLoading()
                    }

                    override fun onNext(t: EventResponse) {
                        view.displayLastMatch(t.events ?: Collections.emptyList())
                    }

                    override fun onError(t: Throwable?) {
                        log.error("getAllLastMatch : "+t!!.message)
                        view.displayLastMatch(Collections.emptyList())
                        view.hideLoading()
                    }
                })
        )
    }
}