package id.digisys.android.kotlinmvpsamples.presenter.match.detail

import id.digisys.android.kotlinmvpsamples.contract.match.detail.DetailMatchContract
import id.digisys.android.kotlinmvpsamples.model.event.Event
import id.digisys.android.kotlinmvpsamples.model.event.EventResponse
import id.digisys.android.kotlinmvpsamples.model.team.Team
import id.digisys.android.kotlinmvpsamples.model.team.TeamResponse
import id.digisys.android.kotlinmvpsamples.repository.favorite.match.MatchFavoriteRepository
import id.digisys.android.kotlinmvpsamples.repository.match.MatchRepository
import id.digisys.android.kotlinmvpsamples.repository.team.TeamRepository
import id.digisys.android.kotlinmvpsamples.utility.SchedulerInterface
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.ResourceSubscriber
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error

class DetailMatchPresenter(private val view: DetailMatchContract.View, private val matchRepository : MatchRepository, private val teamRepository: TeamRepository, private val matchFavoriteRepository: MatchFavoriteRepository, private val scheduler: SchedulerInterface): DetailMatchContract.Presenter{

    private val log = AnkoLogger(this.javaClass)
    private val compositeDisposable = CompositeDisposable()

    override fun isFavorite(idEvent: String) {
        compositeDisposable.add(matchFavoriteRepository.getMatchFavorite(idEvent)
                .observeOn(scheduler.ui())
                .subscribeOn(scheduler.io())
                .subscribeWith(object : ResourceSubscriber<Event>(){
                    override fun onComplete() {
                    }

                    override fun onNext(t: Event?) {
                        val isFavorite = (t != null)
                        view.setFavorite(isFavorite)
                    }

                    override fun onError(t: Throwable?) {
                        log.error("isFavorite : "+t!!.message)
                    }

                })
        )
    }

    override fun addFavoriteMatch(eventId: String, eventDate: String, eventTime: String, homeTeamId: String, awayTeamId: String, homeTeamName: String, awayTeamName: String, homeTeamScore: String?, awayTeamScore: String?) {
        compositeDisposable.add(matchFavoriteRepository.createMatchFavorite(eventId, eventDate, eventTime, homeTeamId, awayTeamId, homeTeamName, awayTeamName, homeTeamScore, awayTeamScore)
                .observeOn(scheduler.ui())
                .subscribeOn(scheduler.io())
                .subscribeWith(object : ResourceSubscriber<Long>(){
                    override fun onComplete() {
                    }

                    override fun onNext(t: Long) {
                        val isAdded = (t != -1L)
                        view.onFavoriteMatchAdded(isAdded)
                    }

                    override fun onError(t: Throwable?) {
                        log.error("addFavoriteMatch : "+t!!.message)
                    }

                })
        )
    }

    override fun removeFavoriteMatch(eventId: String) {
        compositeDisposable.add(matchFavoriteRepository.deleteMatchFavorite(eventId)
                .observeOn(scheduler.ui())
                .subscribeOn(scheduler.io())
                .subscribeWith(object : ResourceSubscriber<Boolean>(){
                    override fun onComplete() {}

                    override fun onNext(t: Boolean?) {
                        view.onFavoriteMatchRemoved(t!!)
                    }

                    override fun onError(t: Throwable?) {
                        log.error("removeFavoriteMatch : "+t!!.message)
                    }

                })
        )
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }

    override fun getMatch(idEvent: String) {
        view.showLoading()
        compositeDisposable.add(matchRepository.getMatch(idEvent)
                .observeOn(scheduler.ui())
                .subscribeOn(scheduler.io())
                .subscribeWith(object : ResourceSubscriber<EventResponse>(){
                    override fun onComplete() {
                        view.hideLoading()
                    }

                    override fun onNext(t: EventResponse?) {
                        var event = Event()
                        if (t?.events != null) {
                            event = t.events[0]
                        }
                        view.displayMatch(event)
                    }

                    override fun onError(t: Throwable?) {
                        log.error("getMatch : "+t!!.message)
                        view.displayMatch(Event())
                        view.hideLoading()
                    }

                })
        )
    }

    override fun getHomeTeamBadge(idTeam: String) {
        compositeDisposable.add(teamRepository.getTeam(idTeam)
                .observeOn(scheduler.ui())
                .subscribeOn(scheduler.io())
                .subscribeWith(object : ResourceSubscriber<TeamResponse>(){
                    override fun onComplete() {
                    }

                    override fun onNext(t: TeamResponse?) {
                        var team = Team()
                        if (t?.teams != null) {
                            team = t.teams[0]
                        }
                        view.displayHomeTeamBadge(team)
                    }

                    override fun onError(t: Throwable?) {
                        log.error("getHomeTeamBadge : "+t!!.message)
                    }

                })
        )
    }

    override fun getAwayTeamBadge(idTeam: String) {
        compositeDisposable.add(teamRepository.getTeam(idTeam)
                .observeOn(scheduler.ui())
                .subscribeOn(scheduler.io())
                .subscribeWith(object : ResourceSubscriber<TeamResponse>(){
                    override fun onComplete() {
                    }

                    override fun onNext(t: TeamResponse?) {
                        var team = Team()
                        if (t?.teams != null) {
                            team = t.teams[0]
                        }
                        view.displayAwayTeamBadge(team)
                    }

                    override fun onError(t: Throwable?) {
                        log.error("getAwayTeamBadge : "+t!!.message)
                    }

                })
        )
    }

}