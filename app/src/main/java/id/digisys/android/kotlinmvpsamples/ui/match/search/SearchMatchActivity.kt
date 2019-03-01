package id.digisys.android.kotlinmvpsamples.ui.match.search

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import id.digisys.android.kotlinmvpsamples.R
import id.digisys.android.kotlinmvpsamples.adapter.MatchRecyclerViewAdapter
import id.digisys.android.kotlinmvpsamples.api.RestApi
import id.digisys.android.kotlinmvpsamples.api.service.TheSportDbService
import id.digisys.android.kotlinmvpsamples.contract.match.search.SearchMatchContract
import id.digisys.android.kotlinmvpsamples.model.event.Event
import id.digisys.android.kotlinmvpsamples.presenter.match.search.SearchMatchPresenter
import id.digisys.android.kotlinmvpsamples.repository.match.MatchRepository
import id.digisys.android.kotlinmvpsamples.utility.SchedulerProvider
import id.digisys.android.kotlinmvpsamples.utility.hide
import id.digisys.android.kotlinmvpsamples.utility.show
import kotlinx.android.synthetic.main.activity_search_match.*

class SearchMatchActivity : AppCompatActivity(), SearchMatchContract.View {

    private var eventMutableList : MutableList<Event> = mutableListOf()
    lateinit var mPresenter: SearchMatchContract.Presenter
    private lateinit var matchRecyclerViewAdapter: MatchRecyclerViewAdapter
    private var handler = Handler()

    override fun displayMatch(eventList: List<Event>) {
        eventMutableList.clear()
        eventMutableList.addAll(eventList)
        matchRecyclerViewAdapter.notifyDataSetChanged()
    }

    override fun hideLoading() {
        progressBar.hide()
        rvSearchMatch.visibility = View.VISIBLE
    }

    override fun showLoading() {
        progressBar.show()
        rvSearchMatch.visibility = View.GONE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_match)
        progressBar.hide()

        supportActionBar?.title = getString(R.string.title_search_match)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val service = RestApi.theSportDb().create(TheSportDbService::class.java)
        val matchRepository = MatchRepository(service)
        val scheduler = SchedulerProvider()
        mPresenter = SearchMatchPresenter(this, matchRepository, scheduler)

        matchRecyclerViewAdapter = MatchRecyclerViewAdapter(eventMutableList, this)
        rvSearchMatch.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvSearchMatch.adapter = matchRecyclerViewAdapter
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
        searchView?.queryHint = getString(R.string.title_search_match)
        searchView?.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                handler.removeCallbacksAndMessages(null)
                handler.postDelayed({
                    mPresenter.searchMatch(newText)
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
