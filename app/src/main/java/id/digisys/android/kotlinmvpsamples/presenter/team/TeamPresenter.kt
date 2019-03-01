package id.digisys.android.kotlinmvpsamples.presenter.team

import id.digisys.android.kotlinmvpsamples.contract.team.TeamContract
import id.digisys.android.kotlinmvpsamples.model.team.TeamResponse
import id.digisys.android.kotlinmvpsamples.repository.team.TeamDataSource
import id.digisys.android.kotlinmvpsamples.utility.SchedulerInterface
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.ResourceSubscriber
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import java.util.*

class TeamPresenter(val view: TeamContract.View, var repository: TeamDataSource, val scheduler: SchedulerInterface): TeamContract.Presenter{

    private val log = AnkoLogger(this.javaClass)
    private val compositeDisposable = CompositeDisposable()

    override fun onDestroy() {
        compositeDisposable.dispose()
    }

    override fun getAllTeam(idLeague: String) {
        view.showLoading()
        compositeDisposable.add(repository.getAllTeam(idLeague)
                .observeOn(scheduler.ui())
                .subscribeOn(scheduler.io())
                .subscribeWith(object: ResourceSubscriber<TeamResponse>(){
                    override fun onComplete() {
                        view.hideLoading()
                    }

                    override fun onNext(t: TeamResponse) {
                        view.displayAllTeam(t.teams ?: Collections.emptyList())
                    }

                    override fun onError(t: Throwable?) {
                        log.error("getAllTeam : "+t!!.message)
                        view.displayAllTeam(Collections.emptyList())
                        view.hideLoading()
                    }

                })
        )
    }

}