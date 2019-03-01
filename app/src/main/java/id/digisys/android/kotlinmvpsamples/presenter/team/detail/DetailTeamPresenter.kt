package id.digisys.android.kotlinmvpsamples.presenter.team.detail

import id.digisys.android.kotlinmvpsamples.contract.team.detail.DetailTeamContract
import id.digisys.android.kotlinmvpsamples.model.team.Team
import id.digisys.android.kotlinmvpsamples.model.team.TeamResponse
import id.digisys.android.kotlinmvpsamples.repository.favorite.team.TeamFavoriteRepository
import id.digisys.android.kotlinmvpsamples.repository.team.TeamRepository
import id.digisys.android.kotlinmvpsamples.utility.SchedulerInterface
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.ResourceSubscriber
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error

class DetailTeamPresenter(private val view : DetailTeamContract.View, private val teamRepository : TeamRepository, private val teamFavoriteRepository: TeamFavoriteRepository, private val scheduler : SchedulerInterface): DetailTeamContract.Presenter{

    private val log = AnkoLogger(this.javaClass)
    private val compositeDisposable = CompositeDisposable()

    override fun addFavoriteTeam(teamId: String, teamName: String, teamBadge: String, teamManager: String, teamStadium: String, teamDescription: String) {
        compositeDisposable.add(teamFavoriteRepository.createTeamFavorite(teamId, teamName, teamBadge, teamManager, teamStadium, teamDescription)
                .observeOn(scheduler.ui())
                .subscribeOn(scheduler.io())
                .subscribeWith(object : ResourceSubscriber<Long>(){
                    override fun onComplete() {
                    }

                    override fun onNext(t: Long) {
                        val isAdded = (t != -1L)
                        view.onFavoriteTeamAdded(isAdded)
                    }

                    override fun onError(t: Throwable?) {
                        log.error("addFavoriteTeam : "+t!!.message)
                    }

                })
        )
    }

    override fun removeFavoriteTeam(teamId: String) {
        compositeDisposable.add(teamFavoriteRepository.deleteTeamFavorite(teamId)
                .observeOn(scheduler.ui())
                .subscribeOn(scheduler.io())
                .subscribeWith(object : ResourceSubscriber<Boolean>(){
                    override fun onComplete() {}

                    override fun onNext(t: Boolean?) {
                        view.onFavoriteTeamRemoved(t!!)
                    }

                    override fun onError(t: Throwable?) {
                        log.error("removeFavoriteTeam : "+t!!.message)
                    }

                })
        )
    }

    override fun isFavorite(idTeam: String) {
        compositeDisposable.add(teamFavoriteRepository.getTeamFavorite(idTeam)
                .observeOn(scheduler.ui())
                .subscribeOn(scheduler.io())
                .subscribeWith(object : ResourceSubscriber<Team>(){
                    override fun onComplete() {
                    }

                    override fun onNext(t: Team) {
                        val isFavorite = (t != Team())
                        view.setFavorite(isFavorite)
                    }

                    override fun onError(t: Throwable?) {
                        log.error("isFavorite : "+t!!.message)
                    }

                })
        )
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }

    override fun getTeam(idTeam: String) {
        compositeDisposable.add(teamRepository.getTeam(idTeam)
                .observeOn(scheduler.ui())
                .subscribeOn(scheduler.io())
                .subscribeWith(object : ResourceSubscriber<TeamResponse>(){
                    override fun onComplete() {
                    }

                    override fun onNext(t: TeamResponse?) {
                        var team = Team()
                        if (t?.teams != null) {
                            if(t.teams.isNotEmpty())
                                team = t.teams[0]
                        }
                        view.displayTeam(team)
                    }

                    override fun onError(t: Throwable?) {
                        log.error("getTeam : "+t!!.message)
                        view.displayTeam(Team())
                    }

                })
        )
    }

}