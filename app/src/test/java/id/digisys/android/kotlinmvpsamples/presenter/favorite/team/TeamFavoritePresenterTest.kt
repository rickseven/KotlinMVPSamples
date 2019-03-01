package id.digisys.android.kotlinmvpsamples.presenter.favorite.team

import androidx.test.core.app.ApplicationProvider
import id.digisys.android.kotlinmvpsamples.contract.favorite.team.TeamFavoriteContract
import id.digisys.android.kotlinmvpsamples.model.team.Team
import id.digisys.android.kotlinmvpsamples.repository.favorite.team.TeamFavoriteRepository
import id.digisys.android.kotlinmvpsamples.utility.SchedulerInterface
import id.digisys.android.kotlinmvpsamples.utility.TestSchedulerProvider
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class TeamFavoritePresenterTest {

    @Mock
    private lateinit var view: TeamFavoriteContract.View

    private lateinit var teamMutableList: MutableList<Team>
    private lateinit var teamResponseFlowable: Flowable<List<Team>>
    private lateinit var repository: TeamFavoriteRepository
    private lateinit var scheduler: SchedulerInterface
    private lateinit var mPresenter: TeamFavoriteContract.Presenter
    private lateinit var teamList: List<Team>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        teamMutableList = mutableListOf()
        repository = Mockito.spy(TeamFavoriteRepository(ApplicationProvider.getApplicationContext()))
        scheduler = TestSchedulerProvider()
        mPresenter = TeamFavoritePresenter(view, repository, scheduler)
        teamList = listOf(
                Team(
                        idTeam = "133604",
                        strTeam = "Arsenal",
                        strTeamBadge = "https://www.thesportsdb.com/images/media/team/badge/vrtrtp1448813175.png"
                )
        )
    }

    @Test
    fun `get all team favorite, when data available, should update view`() {

        teamMutableList.addAll(teamList)
        teamResponseFlowable = Flowable.just(teamMutableList)

        Mockito.`when`(repository.getAllTeamFavorite()).thenReturn(teamResponseFlowable)

        mPresenter.getAllTeamFavorite()

        Mockito.verify(view).showLoading()
        Mockito.verify(view).displayTeamFavorite(teamMutableList)
        Mockito.verify(view).hideLoading()
    }
}