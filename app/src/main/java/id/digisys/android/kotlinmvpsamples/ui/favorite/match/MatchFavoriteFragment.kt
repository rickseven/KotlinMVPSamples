package id.digisys.android.kotlinmvpsamples.ui.favorite.match


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.digisys.android.kotlinmvpsamples.R
import id.digisys.android.kotlinmvpsamples.contract.favorite.match.MatchFavoriteContract
import id.digisys.android.kotlinmvpsamples.presenter.favorite.match.MatchFavoritePresenter
import id.digisys.android.kotlinmvpsamples.adapter.MatchRecyclerViewAdapter
import id.digisys.android.kotlinmvpsamples.model.event.Event
import id.digisys.android.kotlinmvpsamples.repository.favorite.match.MatchFavoriteRepository
import id.digisys.android.kotlinmvpsamples.utility.SchedulerProvider
import id.digisys.android.kotlinmvpsamples.utility.hide
import id.digisys.android.kotlinmvpsamples.utility.show
import kotlinx.android.synthetic.main.fragment_match_favorite.*

class MatchFavoriteFragment : Fragment(), MatchFavoriteContract.View {

    private lateinit var mPresenter : MatchFavoritePresenter
    private var eventMutableList : MutableList<Event> = mutableListOf()

    override fun displayMatchFavorite(eventList: List<Event>) {
        eventMutableList.clear()
        eventMutableList.addAll(eventList.reversed())
        rvMatchFavorite.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvMatchFavorite.adapter = MatchRecyclerViewAdapter(eventMutableList, context)
    }

    override fun hideLoading() {
        progressBar.hide()
        rvMatchFavorite.visibility = View.VISIBLE
    }

    override fun showLoading() {
        progressBar.show()
        rvMatchFavorite.visibility = View.GONE
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_match_favorite, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val matchFavoriteRepository = MatchFavoriteRepository(context!!)
        val scheduler = SchedulerProvider()
        mPresenter = MatchFavoritePresenter(this, matchFavoriteRepository, scheduler)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        mPresenter.getAllMatchFavorite()
    }
}
