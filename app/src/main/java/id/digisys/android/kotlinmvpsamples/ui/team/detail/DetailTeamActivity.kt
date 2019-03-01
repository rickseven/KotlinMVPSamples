package id.digisys.android.kotlinmvpsamples.ui.team.detail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.digisys.android.kotlinmvpsamples.R
import id.digisys.android.kotlinmvpsamples.adapter.ViewPagerAdapter
import id.digisys.android.kotlinmvpsamples.api.RestApi
import id.digisys.android.kotlinmvpsamples.api.service.TheSportDbService
import id.digisys.android.kotlinmvpsamples.contract.team.detail.DetailTeamContract
import id.digisys.android.kotlinmvpsamples.model.team.Team
import id.digisys.android.kotlinmvpsamples.presenter.team.detail.DetailTeamPresenter
import id.digisys.android.kotlinmvpsamples.repository.favorite.team.TeamFavoriteRepository
import id.digisys.android.kotlinmvpsamples.repository.team.TeamRepository
import id.digisys.android.kotlinmvpsamples.ui.team.overview.OverviewTeamFragment
import id.digisys.android.kotlinmvpsamples.ui.team.player.PlayerTeamFragment
import id.digisys.android.kotlinmvpsamples.utility.SchedulerProvider
import org.jetbrains.anko.*
import kotlinx.android.synthetic.main.activity_detail_team.*

class DetailTeamActivity : AppCompatActivity(), DetailTeamContract.View {

    private lateinit var mPresenter: DetailTeamPresenter
    private lateinit var team: Team
    private var optionMenu: Menu? = null
    private var isFavorite: Boolean = false

    override fun setFavorite(isFavorite: Boolean) {
        this.isFavorite = isFavorite
        if(isFavorite)
            optionMenu?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_black_24dp)
        else
            optionMenu?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_black_24dp)
    }

    override fun onFavoriteTeamRemoved(isRemoved: Boolean) {
        if(isRemoved) {
            setFavorite(false)
            toast(getString(R.string.item_deleted))
        }else {
            toast(getString(R.string.could_not_delete_item))
        }
    }

    override fun onFavoriteTeamAdded(isAdded: Boolean) {
        if(isAdded) {
            setFavorite(true)
            toast(getString(R.string.item_added))
        }else {
            toast(getString(R.string.could_not_add_item))
        }
    }

    override fun displayTeam(team: Team) {
        supportActionBar?.title = team.strTeam
        Glide.with(applicationContext)
                .load(team.strTeamBadge)
                .apply(RequestOptions().placeholder(R.drawable.logo_football))
                .apply(RequestOptions().override(200, 233))
                .into(teamBadge)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_team)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val service = RestApi.theSportDb().create(TheSportDbService::class.java)
        val teamRepository = TeamRepository(service)
        val teamFavoriteRepository = TeamFavoriteRepository(applicationContext)
        val scheduler = SchedulerProvider()

        team = intent.getParcelableExtra("team")

        mPresenter = DetailTeamPresenter(this, teamRepository, teamFavoriteRepository, scheduler)
        mPresenter.getTeam(team.idTeam?:"")

        val bundle = Bundle()
        val adapter = ViewPagerAdapter(supportFragmentManager)
        val overviewDetailTeamFragment = OverviewTeamFragment()
        val playerTeamFragment = PlayerTeamFragment()

        bundle.putParcelable("team", team)
        overviewDetailTeamFragment.arguments = bundle
        playerTeamFragment.arguments = bundle
        adapter.populateFragment(overviewDetailTeamFragment, getString(R.string.title_overview))
        adapter.populateFragment(playerTeamFragment, getString(R.string.title_player))
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.favorite -> {
                if (!isFavorite)
                    mPresenter.addFavoriteTeam(team.idTeam?:"", team.strTeam?:"", team.strTeamBadge?:"", team.strManager?:"", team.strStadium?:"", team.strDescriptionEN?:"")
                else
                    mPresenter.removeFavoriteTeam(team.idTeam?:"")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favorite, menu)
        optionMenu = menu
        mPresenter.isFavorite(team.idTeam?:"")
        return super.onCreateOptionsMenu(menu)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
    }
}
