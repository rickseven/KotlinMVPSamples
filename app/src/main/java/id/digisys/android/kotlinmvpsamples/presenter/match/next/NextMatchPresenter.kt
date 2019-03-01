package id.digisys.android.kotlinmvpsamples.presenter.match.next

import id.digisys.android.kotlinmvpsamples.contract.match.next.NextMatchContract
import id.digisys.android.kotlinmvpsamples.model.event.EventResponse
import id.digisys.android.kotlinmvpsamples.repository.match.MatchRepository
import id.digisys.android.kotlinmvpsamples.utility.SchedulerInterface
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.ResourceSubscriber
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import java.util.*

class NextMatchPresenter(val view: NextMatchContract.View, val repository: MatchRepository, val scheduler: SchedulerInterface): NextMatchContract.Presenter{

    private val log = AnkoLogger(this.javaClass)
    private val compositeDisposable = CompositeDisposable()

    override fun onDestroy() {
        compositeDisposable.dispose()
    }

    override fun getAllNextMatch(idLeague: String) {
        view.showLoading()
        compositeDisposable.add(repository.getAllNextMatch(idLeague)
                .observeOn(scheduler.ui())
                .subscribeOn(scheduler.io())
                .subscribeWith(object : ResourceSubscriber<EventResponse>() {
                    override fun onComplete() {
                        view.hideLoading()
                    }

                    override fun onNext(t: EventResponse) {
                        view.displayNextMatch(t.events ?: Collections.emptyList())
                    }

                    override fun onError(t: Throwable?) {
                        log.error("getAllNextMatch : "+t!!.message)
                        view.displayNextMatch(Collections.emptyList())
                        view.hideLoading()
                    }
                })
        )
    }

}