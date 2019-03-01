package id.digisys.android.kotlinmvpsamples.ui.team


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import id.digisys.android.kotlinmvpsamples.R
import id.digisys.android.kotlinmvpsamples.contract.team.TeamContract
import id.digisys.android.kotlinmvpsamples.model.team.Team
import id.digisys.android.kotlinmvpsamples.presenter.team.TeamPresenter
import kotlinx.android.synthetic.main.fragment_team.*
import id.digisys.android.kotlinmvpsamples.adapter.TeamRecyclerViewAdapter
import id.digisys.android.kotlinmvpsamples.api.RestApi
import id.digisys.android.kotlinmvpsamples.api.service.TheSportDbService
import id.digisys.android.kotlinmvpsamples.repository.team.TeamRepository
import id.digisys.android.kotlinmvpsamples.utility.SchedulerProvider
import id.digisys.android.kotlinmvpsamples.ui.team.search.SearchTeamActivity
import id.digisys.android.kotlinmvpsamples.utility.hide
import id.digisys.android.kotlinmvpsamples.utility.show

class TeamFragment : Fragment(), TeamContract.View {

    lateinit var mPresenter: TeamContract.Presenter
    private var teamMutableList: MutableList<Team> = mutableListOf()

    override fun displayAllTeam(teamList: List<Team>) {
        teamMutableList.clear()
        teamMutableList.addAll(teamList)
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
        return inflater.inflate(R.layout.fragment_team, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val service = RestApi.theSportDb().create(TheSportDbService::class.java)
        val teamRepository = TeamRepository(service)
        val scheduler = SchedulerProvider()
        val leagueNameList = resources.getStringArray(R.array.league_name)
        val leagueIdList = resources.getStringArray(R.array.league_id)
        val spinnerLastMatchAdapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_dropdown_item, leagueNameList)
        mPresenter = TeamPresenter(this, teamRepository, scheduler)
        mPresenter.getAllTeam(leagueIdList[0].toString())
        spinnerTeam.adapter = spinnerLastMatchAdapter
        spinnerTeam.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val leagueName = spinnerTeam.selectedItem.toString()
                when(leagueName){
                    leagueNameList[0].toString() -> mPresenter.getAllTeam(leagueIdList[0].toString())
                    leagueNameList[1].toString() -> mPresenter.getAllTeam(leagueIdList[1].toString())
                    leagueNameList[2].toString() -> mPresenter.getAllTeam(leagueIdList[2].toString())
                    leagueNameList[3].toString() -> mPresenter.getAllTeam(leagueIdList[3].toString())
                    leagueNameList[4].toString() -> mPresenter.getAllTeam(leagueIdList[4].toString())
                    else -> mPresenter.getAllTeam(leagueIdList[0].toString())
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.search, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.actionSearch -> {
                val intent = Intent(context, SearchTeamActivity::class.java)
                context?.startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
    }

}
