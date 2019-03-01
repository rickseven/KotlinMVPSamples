package id.digisys.android.kotlinmvpsamples.ui.team.player.detail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.digisys.android.kotlinmvpsamples.R
import id.digisys.android.kotlinmvpsamples.model.player.Player
import kotlinx.android.synthetic.main.activity_detail_player_team.*

class DetailPlayerTeamActivity : AppCompatActivity() {

    private lateinit var player : Player

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_player_team)

        supportActionBar?.title = getString(R.string.player_detail)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        player = intent.getParcelableExtra("player")
        tvPlayerName.text = player.strPlayer
        tvPlayerPosition.text = player.strPosition
        tvPlayerBirthDate.text = player.dateBorn
        tvPlayerDescription.text = player.strDescriptionEN
        Glide.with(applicationContext)
            .load(player.strCutout)
            .apply(RequestOptions().placeholder(R.drawable.logo_football))
            .apply(RequestOptions().override(200, 234))
            .into(imagePlayer)
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
