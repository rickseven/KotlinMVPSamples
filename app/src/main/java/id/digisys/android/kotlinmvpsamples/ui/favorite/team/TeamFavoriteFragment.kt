package id.digisys.android.kotlinmvpsamples.ui.favorite.team


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.digisys.android.kotlinmvpsamples.R
import id.digisys.android.kotlinmvpsamples.contract.favorite.team.TeamFavoriteContract
import id.digisys.android.kotlinmvpsamples.presenter.favorite.team.TeamFavoritePresenter
import kotlinx.android.synthetic.main.fragment_team_favorite.*
import id.digisys.android.kotlinmvpsamples.adapter.TeamRecyclerViewAdapter
import id.digisys.android.kotlinmvpsamples.repository.favorite.team.TeamFavoriteRepository
import id.digisys.android.kotlinmvpsamples.utility.SchedulerProvider
import id.digisys.android.kotlinmvpsamples.model.team.Team
import id.digisys.android.kotlinmvpsamples.utility.hide
import id.digisys.android.kotlinmvpsamples.utility.show

class TeamFavoriteFragment : Fragment(), TeamFavoriteContract.View {

    private lateinit var mPresenter: TeamFavoritePresenter
    private var teamMutableList : MutableList<Team> = mutableListOf()

    override fun displayTeamFavorite(teamList: List<Team>) {
        teamMutableList.clear()
        teamMutableList.addAll(teamList.reversed())
        rvTeam.layoutManager = GridLayoutManager(context, 3)
        rvTeam.adapter = TeamRecyclerViewAdapter(teamMutableList, context)
    }

    override fun hideLoading() {
        progressBar.hide()
        rvTeam.visibility = View.VISIBLE
    }

    override fun showLoading() {
        progressBar.show()
        rvTeam.visibility = View.GONE
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team_favorite, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val teamFavoriteRepository = TeamFavoriteRepository(context!!)
        val scheduler = SchedulerProvider()
        mPresenter = TeamFavoritePresenter(this, teamFavoriteRepository, scheduler)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        mPresenter.getAllTeamFavorite()
    }

}
