package id.digisys.android.kotlinmvpsamples.ui.match.detail

import android.annotation.SuppressLint
import android.content.ContentUris
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.digisys.android.kotlinmvpsamples.R
import id.digisys.android.kotlinmvpsamples.api.RestApi
import id.digisys.android.kotlinmvpsamples.api.service.TheSportDbService
import id.digisys.android.kotlinmvpsamples.contract.match.detail.DetailMatchContract
import id.digisys.android.kotlinmvpsamples.model.event.Event
import id.digisys.android.kotlinmvpsamples.model.team.Team
import id.digisys.android.kotlinmvpsamples.presenter.match.detail.DetailMatchPresenter
import id.digisys.android.kotlinmvpsamples.repository.favorite.match.MatchFavoriteRepository
import id.digisys.android.kotlinmvpsamples.repository.match.MatchRepository
import id.digisys.android.kotlinmvpsamples.repository.team.TeamRepository
import id.digisys.android.kotlinmvpsamples.utility.*
import kotlinx.android.synthetic.main.activity_detail_match.*
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*
import android.provider.CalendarContract
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import org.jetbrains.anko.longToast


class DetailMatchActivity : AppCompatActivity(), DetailMatchContract.View {

    private lateinit var mPresenter : DetailMatchPresenter
    private lateinit var event: Event
    private var optionMenu: Menu? = null
    private var isFavorite: Boolean = false

    override fun onFavoriteMatchRemoved(isRemoved: Boolean) {
        if(isRemoved) {
            setFavorite(false)
            toast(getString(R.string.item_deleted))
        }else {
            toast(getString(R.string.could_not_delete_item))
        }
    }

    override fun onFavoriteMatchAdded(isAdded: Boolean) {
        if(isAdded) {
            setFavorite(true)
            toast(getString(R.string.item_added))
        }else {
            toast(getString(R.string.could_not_add_item))
        }
    }

    override fun hideLoading() {
        progressBar.hide()
        scrollView.visibility = View.VISIBLE
    }

    override fun showLoading() {
        progressBar.show()
        scrollView.visibility = View.GONE
    }

    override fun displayMatch(event: Event) {
        event.strDate = event.strDate ?: event.dateEvent.let { DateHelper.formatDateToString(it, "dd/MM/yy") }
        event.strTime = if(event.strTime.isNullOrEmpty()) "00:00" else event.strTime.toString().substring(0,5)
        val dateEvent = SimpleDateFormat("dd/MM/yy HH:mm", Locale.getDefault()).parse(event.strDate +" "+ event.strTime)
        if(dateEvent.inFuture()){
            tvDate.setTextColor(ContextCompat.getColor(applicationContext, R.color.colorPrimary))
            addAlert.visibility = View.VISIBLE
        }
        tvDate.text = event.dateEvent?.let { DateHelper.formatDateToString(it) }
        homeTeam.text = event.strHomeTeam
        awayTeam.text = event.strAwayTeam
        homeScore.text = event.intHomeScore
        awayScore.text = event.intAwayScore
        homeGoalDetails.text = event.strHomeGoalDetails
        awayGoalDetails.text = event.strAwayGoalDetails
        homeGoalKeeper.text = event.strHomeLineupGoalkeeper
        awayGoalKeeper.text = event.strAwayLineupGoalkeeper
        homeDefense.text = event.strHomeLineupDefense
        awayDefense.text = event.strAwayLineupDefense
        homeMidfield.text = event.strHomeLineupMidfield
        awayMidfield.text = event.strAwayLineupMidfield
        homeForward.text = event.strHomeLineupForward
        awayForward.text = event.strAwayLineupForward
        homeSubstitute.text = event.strHomeLineupSubstitutes
        awaySubstitute.text = event.strAwayLineupSubstitutes

        hideUI(event)

        val date = dateEvent.let { DateHelper.formatDateToString(it, "dd-MM-yyyy") }
        val title = event.strHomeTeam+" vs "+event.strAwayTeam
        val desc = "Match schedule " +event.strHomeTeam+" vs "+event.strAwayTeam

        if (isEventInCalendar(date, event.strTime.toString(), title)) {
            addAlert.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_notifications_active_black_24dp
                )
            )
        }
        addAlert.setOnClickListener {
            if (!isEventInCalendar(date, event.strTime.toString(), title))
                addEventToCalendar(date, event.strTime.toString(), title, desc)
            else
                longToast(getString(R.string.event_already_exist_on_calendar))
        }
    }

    override fun displayHomeTeamBadge(team: Team) {
        Glide.with(applicationContext)
                .load(team.strTeamBadge)
                .apply(RequestOptions().placeholder(R.drawable.logo_football))
                .into(homeBadge)
    }

    override fun displayAwayTeamBadge(team: Team) {
        Glide.with(applicationContext)
                .load(team.strTeamBadge)
                .apply(RequestOptions().placeholder(R.drawable.logo_football))
                .into(awayBadge)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_match)

        supportActionBar?.title = getString(R.string.match_detail)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        addAlert.visibility = View.GONE

        val service = RestApi.theSportDb().create(TheSportDbService::class.java)
        val matchRepository = MatchRepository(service)
        val teamRepository = TeamRepository(service)
        val matchFavoriteRepository = MatchFavoriteRepository(applicationContext)
        val scheduler = SchedulerProvider()

        event = intent.getParcelableExtra("event")

        mPresenter = DetailMatchPresenter(this, matchRepository, teamRepository, matchFavoriteRepository, scheduler)
        mPresenter.getHomeTeamBadge(event.idHomeTeam?:"")
        mPresenter.getAwayTeamBadge(event.idAwayTeam?:"")
        mPresenter.getMatch(event.idEvent?:"")
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.favorite -> {
                if (!isFavorite) {
                    mPresenter.addFavoriteMatch(event.idEvent
                            ?: "", event.strDate?:"", event.strTime?:"", event.idHomeTeam
                            ?: "", event.idAwayTeam ?: "", event.strHomeTeam
                            ?: "", event.strAwayTeam ?: "", event.intHomeScore, event.intAwayScore)
                }else {
                    mPresenter.removeFavoriteMatch(event.idEvent ?: "")
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favorite, menu)
        optionMenu = menu
        mPresenter.isFavorite(event.idEvent?:"")
        return super.onCreateOptionsMenu(menu)
    }

    override fun setFavorite(isFavorite: Boolean){
        this.isFavorite = isFavorite
        if(isFavorite)
            optionMenu?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_black_24dp)
        else
            optionMenu?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_black_24dp)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
    }

    @SuppressLint("MissingPermission")
    private fun addEventToCalendar(calendarDate: String, time: String, title: String, desc: String){
        val calendarDateArray = calendarDate.split("-").toTypedArray()
        val timeArray = time.split(":").toTypedArray()
        val calID: Long = 1
        val startMillis: Long = Calendar.getInstance().run {
            set(calendarDateArray[2].toInt(),
                calendarDateArray[1].toInt() - 1,
                calendarDateArray[0].toInt(),
                timeArray[0].toInt(),
                timeArray[1].toInt())
            timeInMillis
        }
        val endMillis: Long = startMillis + 60 * 60 * 1000
        val timeZone = TimeZone.getDefault().id
        val event = ContentValues().apply {
            put(CalendarContract.Events.DTSTART, startMillis)
            put(CalendarContract.Events.DTEND, endMillis)
            put(CalendarContract.Events.TITLE, title)
            put(CalendarContract.Events.DESCRIPTION, desc)
            put(CalendarContract.Events.CALENDAR_ID, calID)
            put(CalendarContract.Events.EVENT_TIMEZONE, timeZone)
        }
        val uri: Uri = contentResolver.insert(CalendarContract.Events.CONTENT_URI, event)
        val eventId: Long = uri.lastPathSegment.toLong()

        //add reminder
        val values = ContentValues().apply {
            put(CalendarContract.Reminders.MINUTES, 15)
            put(CalendarContract.Reminders.EVENT_ID, eventId)
            put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT)
        }
        contentResolver.insert(CalendarContract.Reminders.CONTENT_URI, values)

        addAlert.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.ic_notifications_active_black_24dp
            )
        )

    }

    private fun isEventInCalendar(calendarDate: String, time: String, title: String):Boolean{
        val calendarDateArray = calendarDate.split("-").toTypedArray()
        val timeArray = time.split(":").toTypedArray()
        val instanceProjection: Array<String> = arrayOf(
            CalendarContract.Instances.EVENT_ID,
            CalendarContract.Instances.BEGIN,
            CalendarContract.Instances.TITLE
        )

        val startMillis: Long = Calendar.getInstance().run {
            set(calendarDateArray[2].toInt(),
                calendarDateArray[1].toInt() - 1,
                calendarDateArray[0].toInt(),
                timeArray[0].toInt(),
                timeArray[1].toInt())
            timeInMillis
        }

        val endMillis: Long = Calendar.getInstance().run{
            set(calendarDateArray[2].toInt(),
                calendarDateArray[1].toInt(),
                calendarDateArray[0].toInt(),
                timeArray[0].toInt(),
                timeArray[1].toInt())
            timeInMillis
        }

        val selection = "${CalendarContract.Instances.TITLE} = ?"
        val selectionArgs: Array<String> = arrayOf(title)
        val builder: Uri.Builder = CalendarContract.Instances.CONTENT_URI.buildUpon()
        ContentUris.appendId(builder, startMillis)
        ContentUris.appendId(builder, endMillis)

        val cursor: Cursor = contentResolver.query(
            builder.build(),
            instanceProjection,
            selection,
            selectionArgs, null
        )

        val isIn = cursor.moveToFirst() && cursor.getString(cursor.getColumnIndex(CalendarContract.Instances.TITLE)).equals(
            title,
            ignoreCase = true
        )

        cursor.close()
        return isIn
    }

    private fun hideUI(event: Event){
        var goalKeeperVisible = true
        var defenseVisible = true
        var midfieldVisible = true
        var forwardVisible = true
        var substituteVisible = true

        if(event.strHomeLineupGoalkeeper == null && event.strAwayLineupGoalkeeper == null) {
            layoutGoalKeeper.visibility = View.GONE
            lineGoalKeeper.visibility = View.GONE
            goalKeeperVisible = false
        }

        if(event.strHomeLineupDefense == null && event.strAwayLineupDefense == null) {
            layoutDefense.visibility = View.GONE
            lineDefense.visibility = View.GONE
            defenseVisible = false
        }

        if(event.strHomeLineupMidfield == null && event.strAwayLineupMidfield == null) {
            layoutMidfield.visibility = View.GONE
            lineMidfield.visibility = View.GONE
            midfieldVisible = false
        }

        if(event.strHomeLineupForward == null && event.strAwayLineupForward == null) {
            layoutForward.visibility = View.GONE
            lineForward.visibility = View.GONE
            forwardVisible = false
        }

        if(event.strHomeLineupSubstitutes == null && event.strAwayLineupSubstitutes == null) {
            layoutSubstitute.visibility = View.GONE
            lineSubstitute.visibility = View.GONE
            substituteVisible = false
        }

        if(!goalKeeperVisible && !defenseVisible && !midfieldVisible && !forwardVisible && !substituteVisible){
            lineTeam.visibility = View.GONE
            linearLayoutLineup.visibility = View.GONE
            lineLineup.visibility = View.GONE
            linearLayoutLineupDetail.visibility = View.GONE
        }

    }

}
