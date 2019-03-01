package id.digisys.android.kotlinmvpsamples.presenter.favorite.team

import id.digisys.android.kotlinmvpsamples.contract.favorite.team.TeamFavoriteContract
import id.digisys.android.kotlinmvpsamples.repository.favorite.team.TeamFavoriteRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.ResourceSubscriber
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import java.util.*
import id.digisys.android.kotlinmvpsamples.model.team.Team
import id.digisys.android.kotlinmvpsamples.utility.SchedulerInterface

class TeamFavoritePresenter(private val view : TeamFavoriteContract.View, private val repository: TeamFavoriteRepository, private val scheduler: SchedulerInterface): TeamFavoriteContract.Presenter{

    private val log = AnkoLogger(this.javaClass)
    private val compositeDisposable = CompositeDisposable()

    override fun getAllTeamFavorite() {
        view.showLoading()
        compositeDisposable.add(repository.getAllTeamFavorite()
                .observeOn(scheduler.ui())
                .subscribeOn(scheduler.io())
                .subscribeWith(object : ResourceSubscriber<List<Team>>(){
                    override fun onComplete() {
                        view.hideLoading()
                    }

                    override fun onNext(t: List<Team>?) {
                        view.displayTeamFavorite(t?: Collections.emptyList())
                    }

                    override fun onError(t: Throwable?) {
                        log.error("getAllTeamFavorite : "+t!!.message)
                        view.displayTeamFavorite(Collections.emptyList())
                        view.hideLoading()
                    }
                })
        )
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }

}