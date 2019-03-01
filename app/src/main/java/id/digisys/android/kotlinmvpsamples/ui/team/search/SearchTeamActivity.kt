package id.digisys.android.kotlinmvpsamples.ui.team.search

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import id.digisys.android.kotlinmvpsamples.R
import id.digisys.android.kotlinmvpsamples.adapter.TeamRecyclerViewAdapter
import id.digisys.android.kotlinmvpsamples.api.RestApi
import id.digisys.android.kotlinmvpsamples.api.service.TheSportDbService
import id.digisys.android.kotlinmvpsamples.contract.team.search.SearchTeamContract
import id.digisys.android.kotlinmvpsamples.model.team.Team
import id.digisys.android.kotlinmvpsamples.presenter.team.search.SearchTeamPresenter
import id.digisys.android.kotlinmvpsamples.repository.team.TeamRepository
import id.digisys.android.kotlinmvpsamples.utility.SchedulerProvider
import id.digisys.android.kotlinmvpsamples.utility.hide
import id.digisys.android.kotlinmvpsamples.utility.show
import kotlinx.android.synthetic.main.activity_search_team.*

class SearchTeamActivity : AppCompatActivity(), SearchTeamContract.View {

    private var teamMutableList : MutableList<Team> = mutableListOf()
    lateinit var mPresenter: SearchTeamContract.Presenter
    private lateinit var teamRecyclerViewAdapter: TeamRecyclerViewAdapter
    private var handler = Handler()

    override fun displayTeam(teamList: List<Team>) {
        teamMutableList.clear()
        teamMutableList.addAll(teamList)
        teamRecyclerViewAdapter.notifyDataSetChanged()
    }

    override fun hideLoading() {
        progressBar.hide()
        rvSearchTeam.visibility = View.VISIBLE
    }

    override fun showLoading() {
        progressBar.show()
        rvSearchTeam.visibility = View.GONE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_team)
        progressBar.hide()

        supportActionBar?.title = getString(R.string.title_search_team)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val service = RestApi.theSportDb().create(TheSportDbService::class.java)
        val teamRepository = TeamRepository(service)
        val scheduler = SchedulerProvider()
        mPresenter = SearchTeamPresenter(this, teamRepository, scheduler)

        rvSearchTeam.layoutManager = GridLayoutManager(this, 3)
        teamRecyclerViewAdapter = TeamRecyclerViewAdapter(teamMutableList, this)
        rvSearchTeam.adapter = teamRecyclerViewAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_view, menu)
        val searchView = menu?.findItem(R.id.actionSearch)?.actionView as SearchView?
        val searchItem = menu?.findItem(R.id.actionSearch)

        searchItem?.expandActionView()
        searchView?.queryHint = getString(R.string.title_search_team)
        searchView?.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                handler.removeCallbacksAndMessages(null)
                handler.postDelayed({
                    mPresenter.searchTeam(newText)
                }, 1000)
                return false
            }
        })

        searchItem?.setOnActionExpandListener(object: MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                onBackPressed()
                return true
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }
}
