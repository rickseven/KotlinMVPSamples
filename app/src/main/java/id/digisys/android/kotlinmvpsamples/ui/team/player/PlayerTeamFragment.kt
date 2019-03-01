package id.digisys.android.kotlinmvpsamples.ui.team.player


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.digisys.android.kotlinmvpsamples.R
import id.digisys.android.kotlinmvpsamples.contract.team.player.PlayerTeamContract
import id.digisys.android.kotlinmvpsamples.model.player.Player
import id.digisys.android.kotlinmvpsamples.adapter.PlayerRecyclerViewAdapter
import id.digisys.android.kotlinmvpsamples.model.team.Team
import id.digisys.android.kotlinmvpsamples.api.RestApi
import id.digisys.android.kotlinmvpsamples.api.service.TheSportDbService
import id.digisys.android.kotlinmvpsamples.repository.player.PlayerRepository
import id.digisys.android.kotlinmvpsamples.utility.SchedulerProvider
import id.digisys.android.kotlinmvpsamples.presenter.team.player.PlayerTeamPresenter
import id.digisys.android.kotlinmvpsamples.utility.hide
import id.digisys.android.kotlinmvpsamples.utility.show
import kotlinx.android.synthetic.main.fragment_player_team.*

class PlayerTeamFragment : Fragment(), PlayerTeamContract.View {

    private var playerMutableList : MutableList<Player> = mutableListOf()
    private lateinit var mPresenter : PlayerTeamContract.Presenter

    override fun showLoading() {
        progressBar.show()
        rvPlayer.visibility = View.GONE
    }

    override fun hideLoading() {
        progressBar.hide()
        rvPlayer.visibility = View.VISIBLE
    }

    override fun displayPlayers(playerList: List<Player>) {
        playerMutableList.clear()
        playerMutableList.addAll(playerList)
        rvPlayer.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvPlayer.adapter = PlayerRecyclerViewAdapter(playerMutableList, context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_player_team, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val team: Team? = arguments?.getParcelable("team")
        val service = RestApi.theSportDb().create(TheSportDbService::class.java)
        val repository = PlayerRepository(service)
        val scheduler = SchedulerProvider()
        mPresenter = PlayerTeamPresenter(this, repository, scheduler)

        mPresenter.getAllPlayer(team?.idTeam!!)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
    }

}
