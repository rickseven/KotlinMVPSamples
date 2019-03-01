package id.digisys.android.kotlinmvpsamples.presenter.team.player

import android.util.Log
import id.digisys.android.kotlinmvpsamples.contract.team.player.PlayerTeamContract
import id.digisys.android.kotlinmvpsamples.model.player.PlayerResponse
import id.digisys.android.kotlinmvpsamples.repository.player.PlayerRepository
import id.digisys.android.kotlinmvpsamples.utility.SchedulerInterface
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.ResourceSubscriber
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import java.util.*

class PlayerTeamPresenter(private val view : PlayerTeamContract.View, private val playerRepository: PlayerRepository, private val scheduler : SchedulerInterface): PlayerTeamContract.Presenter{

    private val log = AnkoLogger(this.javaClass)
    private val compositeDisposable = CompositeDisposable()

    override fun onDestroy() {
        compositeDisposable.dispose()
    }

    override fun getAllPlayer(idTeam: String) {
        view.showLoading()
        compositeDisposable.add(playerRepository.getAllPlayer(idTeam)
            .observeOn(scheduler.ui())
            .subscribeOn(scheduler.io())
            .subscribeWith(object : ResourceSubscriber<PlayerResponse>(){
                override fun onComplete() {
                    view.hideLoading()
                }

                override fun onNext(t: PlayerResponse?) {
                    Log.d("AAA","OK")
                    view.displayPlayers(t?.player?:Collections.emptyList())
                }

                override fun onError(t: Throwable?) {
                    log.error("getAllPlayer : "+t!!.message)
                    view.displayPlayers(Collections.emptyList())
                    view.hideLoading()
                }

            })
        )
    }

}