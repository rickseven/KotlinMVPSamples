package id.digisys.android.kotlinmvpsamples.ui.match.next


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import id.digisys.android.kotlinmvpsamples.R
import id.digisys.android.kotlinmvpsamples.contract.match.next.NextMatchContract
import id.digisys.android.kotlinmvpsamples.model.event.Event
import id.digisys.android.kotlinmvpsamples.presenter.match.next.NextMatchPresenter
import id.digisys.android.kotlinmvpsamples.adapter.MatchRecyclerViewAdapter
import id.digisys.android.kotlinmvpsamples.api.RestApi
import id.digisys.android.kotlinmvpsamples.api.service.TheSportDbService
import id.digisys.android.kotlinmvpsamples.repository.match.MatchRepository
import id.digisys.android.kotlinmvpsamples.utility.SchedulerProvider
import id.digisys.android.kotlinmvpsamples.utility.hide
import id.digisys.android.kotlinmvpsamples.utility.show
import kotlinx.android.synthetic.main.fragment_next_match.*

class NextMatchFragment : Fragment(), NextMatchContract.View {

    private lateinit var mPresenter: NextMatchPresenter
    private var eventMutableList: MutableList<Event> = mutableListOf()
    private lateinit var matchRecyclerViewAdapter: MatchRecyclerViewAdapter

    override fun displayNextMatch(eventList: List<Event>) {
        eventMutableList.clear()
        eventMutableList.addAll(eventList)
        matchRecyclerViewAdapter.notifyDataSetChanged()
    }

    override fun hideLoading() {
        progressBar.hide()
        rvNextMatch.visibility = View.VISIBLE
    }

    override fun showLoading() {
        progressBar.show()
        rvNextMatch.visibility = View.GONE
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_next_match, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val service = RestApi.theSportDb().create(TheSportDbService::class.java)
        val matchRepository = MatchRepository(service)
        val scheduler = SchedulerProvider()
        val leagueNameList = resources.getStringArray(R.array.league_name)
        val leagueIdList = resources.getStringArray(R.array.league_id)
        val spinnerNextMatchAdapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_dropdown_item, leagueNameList)
        mPresenter = NextMatchPresenter(this, matchRepository, scheduler)
        mPresenter.getAllNextMatch(leagueIdList[0].toString())
        spinnerNextMatch.adapter = spinnerNextMatchAdapter
        spinnerNextMatch.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val leagueName = spinnerNextMatch.selectedItem.toString()
                when(leagueName){
                    leagueNameList[0].toString() -> mPresenter.getAllNextMatch(leagueIdList[0].toString())
                    leagueNameList[1].toString() -> mPresenter.getAllNextMatch(leagueIdList[1].toString())
                    leagueNameList[2].toString() -> mPresenter.getAllNextMatch(leagueIdList[2].toString())
                    leagueNameList[3].toString() -> mPresenter.getAllNextMatch(leagueIdList[3].toString())
                    leagueNameList[4].toString() -> mPresenter.getAllNextMatch(leagueIdList[4].toString())
                    else -> mPresenter.getAllNextMatch(leagueIdList[0].toString())
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }
        matchRecyclerViewAdapter = MatchRecyclerViewAdapter(eventMutableList, context)
        rvNextMatch.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvNextMatch.adapter = matchRecyclerViewAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
    }
}
