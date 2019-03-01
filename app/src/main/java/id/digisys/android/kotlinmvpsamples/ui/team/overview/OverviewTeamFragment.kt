package id.digisys.android.kotlinmvpsamples.ui.team.overview


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import id.digisys.android.kotlinmvpsamples.R
import id.digisys.android.kotlinmvpsamples.model.team.Team
import kotlinx.android.synthetic.main.fragment_overview_team.*

class OverviewTeamFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_overview_team, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val team: Team? = arguments?.getParcelable("team")
        tvManager.text = team?.strManager
        tvStadium.text = team?.strStadium
        tvStadium.text = team?.strStadium
        tvDescription.text = team?.strDescriptionEN
    }

}
