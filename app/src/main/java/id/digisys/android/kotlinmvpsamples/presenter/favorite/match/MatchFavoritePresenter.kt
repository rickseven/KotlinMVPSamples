package id.digisys.android.kotlinmvpsamples.presenter.favorite.match

import id.digisys.android.kotlinmvpsamples.contract.favorite.match.MatchFavoriteContract
import id.digisys.android.kotlinmvpsamples.model.event.Event
import id.digisys.android.kotlinmvpsamples.repository.favorite.match.MatchFavoriteRepository
import id.digisys.android.kotlinmvpsamples.utility.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.ResourceSubscriber
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import java.util.*

class MatchFavoritePresenter(private val view : MatchFavoriteContract.View, private val repository: MatchFavoriteRepository, private val scheduler: SchedulerProvider): MatchFavoriteContract.Presenter{

    private val log = AnkoLogger(this.javaClass)
    private val compositeDisposable = CompositeDisposable()

    override fun getAllMatchFavorite() {
        view.showLoading()
        compositeDisposable.add(repository.getAllMatchFavorite()
                .observeOn(scheduler.ui())
                .subscribeOn(scheduler.io())
                .subscribeWith(object : ResourceSubscriber<List<Event>>(){
                    override fun onComplete() {
                        view.hideLoading()
                    }

                    override fun onNext(t: List<Event>?) {
                        view.displayMatchFavorite(t?: Collections.emptyList())
                    }

                    override fun onError(t: Throwable?) {
                        log.error("getAllMatchFavorite : "+t!!.message)
                        view.displayMatchFavorite(Collections.emptyList())
                        view.hideLoading()
                    }
                })
        )
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }

}