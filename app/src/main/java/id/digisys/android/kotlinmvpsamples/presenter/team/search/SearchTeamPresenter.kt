package id.digisys.android.kotlinmvpsamples.presenter.team.search

import id.digisys.android.kotlinmvpsamples.contract.team.search.SearchTeamContract
import id.digisys.android.kotlinmvpsamples.model.team.TeamResponse
import id.digisys.android.kotlinmvpsamples.repository.team.TeamRepository
import id.digisys.android.kotlinmvpsamples.utility.SchedulerInterface
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.ResourceSubscriber
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import java.util.*

class SearchTeamPresenter(val view: SearchTeamContract.View, val repository: TeamRepository, val scheduler: SchedulerInterface): SearchTeamContract.Presenter{

    private val log = AnkoLogger(this.javaClass)
    private val compositeDisposable = CompositeDisposable()

    override fun onDestroy() {
        compositeDisposable.dispose()
    }

    override fun searchTeam(query: String?) {
        view.showLoading()
        compositeDisposable.add(repository.searchTeam(query?:"")
            .observeOn(scheduler.ui())
            .subscribeOn(scheduler.io())
            .subscribeWith(object : ResourceSubscriber<TeamResponse>() {
                override fun onComplete() {
                    view.hideLoading()
                }

                override fun onNext(t: TeamResponse) {
                    if(!query.isNullOrEmpty()) {
                        view.displayTeam(t.teams ?: Collections.emptyList())
                    }else {
                        view.displayTeam(Collections.emptyList())
                    }
                }

                override fun onError(t: Throwable?) {
                    log.error("searchTeam : "+t!!.message)
                    view.displayTeam(Collections.emptyList())
                    view.hideLoading()
                }
            })
        )
    }

}