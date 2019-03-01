package id.digisys.android.kotlinmvpsamples.ui.main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import id.digisys.android.kotlinmvpsamples.R
import id.digisys.android.kotlinmvpsamples.ui.favorite.FavoriteFragment
import id.digisys.android.kotlinmvpsamples.ui.match.MatchFragment
import id.digisys.android.kotlinmvpsamples.ui.team.TeamFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_match -> {
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frameLayout, MatchFragment())
                        .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_team -> {
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frameLayout, TeamFragment())
                        .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favorite -> {
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frameLayout, FavoriteFragment())
                        .commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.selectedItemId = R.id.navigation_match
    }

}
