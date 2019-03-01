package id.digisys.android.kotlinmvpsamples.ui.match.last


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import id.digisys.android.kotlinmvpsamples.R
import id.digisys.android.kotlinmvpsamples.model.event.Event
import id.digisys.android.kotlinmvpsamples.presenter.match.last.LastMatchPresenter
import id.digisys.android.kotlinmvpsamples.adapter.MatchRecyclerViewAdapter
import id.digisys.android.kotlinmvpsamples.api.RestApi
import id.digisys.android.kotlinmvpsamples.api.service.TheSportDbService
import id.digisys.android.kotlinmvpsamples.contract.match.last.LastMatchContract
import id.digisys.android.kotlinmvpsamples.repository.match.MatchRepository
import id.digisys.android.kotlinmvpsamples.utility.SchedulerProvider
import id.digisys.android.kotlinmvpsamples.utility.hide
import id.digisys.android.kotlinmvpsamples.utility.show
import kotlinx.android.synthetic.main.fragment_last_match.*

class LastMatchFragment : Fragment(), LastMatchContract.View {

    private lateinit var mPresenter : LastMatchPresenter
    private var eventMutableList : MutableList<Event> = mutableListOf()
    private lateinit var matchRecyclerViewAdapter: MatchRecyclerViewAdapter

    override fun displayLastMatch(eventList: List<Event>) {
        eventMutableList.clear()
        eventMutableList.addAll(eventList)
        matchRecyclerViewAdapter.notifyDataSetChanged()
    }

    override fun hideLoading() {
        progressBar.hide()
        rvLastMatch.visibility = View.VISIBLE
    }

    override fun showLoading() {
        progressBar.show()
        rvLastMatch.visibility = View.GONE
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_last_match, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val service = RestApi.theSportDb().create(TheSportDbService::class.java)
        val matchRepository = MatchRepository(service)
        val scheduler = SchedulerProvider()
        val leagueNameList = resources.getStringArray(R.array.league_name)
        val leagueIdList = resources.getStringArray(R.array.league_id)
        val spinnerLastMatchAdapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_dropdown_item, leagueNameList)
        mPresenter = LastMatchPresenter(this, matchRepository, scheduler)
        mPresenter.getAllLastMatch(leagueIdList[0].toString())
        spinnerLastMatch.adapter = spinnerLastMatchAdapter
        spinnerLastMatch.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val leagueName = spinnerLastMatch.selectedItem.toString()
                when(leagueName){
                    leagueNameList[0].toString() -> mPresenter.getAllLastMatch(leagueIdList[0].toString())
                    leagueNameList[1].toString() -> mPresenter.getAllLastMatch(leagueIdList[1].toString())
                    leagueNameList[2].toString() -> mPresenter.getAllLastMatch(leagueIdList[2].toString())
                    leagueNameList[3].toString() -> mPresenter.getAllLastMatch(leagueIdList[3].toString())
                    leagueNameList[4].toString() -> mPresenter.getAllLastMatch(leagueIdList[4].toString())
                    else -> mPresenter.getAllLastMatch(leagueIdList[0].toString())
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }
        matchRecyclerViewAdapter = MatchRecyclerViewAdapter(eventMutableList, context)
        rvLastMatch.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvLastMatch.adapter = matchRecyclerViewAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
    }
}
