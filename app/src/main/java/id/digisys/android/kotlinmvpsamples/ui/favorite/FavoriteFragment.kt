package id.digisys.android.kotlinmvpsamples.ui.favorite


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.digisys.android.kotlinmvpsamples.R
import id.digisys.android.kotlinmvpsamples.adapter.ViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_favorite.*
import id.digisys.android.kotlinmvpsamples.ui.favorite.match.MatchFavoriteFragment
import id.digisys.android.kotlinmvpsamples.ui.favorite.team.TeamFavoriteFragment

class FavoriteFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.populateFragment(MatchFavoriteFragment(), getString(R.string.title_favorite_match))
        adapter.populateFragment(TeamFavoriteFragment(), getString(R.string.title_favorite_team))
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }

}
