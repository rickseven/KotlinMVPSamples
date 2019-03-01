package id.digisys.android.kotlinmvpsamples.ui.match


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import id.digisys.android.kotlinmvpsamples.R
import kotlinx.android.synthetic.main.fragment_match.*
import id.digisys.android.kotlinmvpsamples.adapter.ViewPagerAdapter
import id.digisys.android.kotlinmvpsamples.ui.match.last.LastMatchFragment
import id.digisys.android.kotlinmvpsamples.ui.match.next.NextMatchFragment
import id.digisys.android.kotlinmvpsamples.ui.match.search.SearchMatchActivity

class MatchFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.populateFragment(LastMatchFragment(), getString(R.string.title_last_match))
        adapter.populateFragment(NextMatchFragment(), getString(R.string.title_next_match))
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.search, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.actionSearch -> {
                val intent = Intent(context, SearchMatchActivity::class.java)
                context?.startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
