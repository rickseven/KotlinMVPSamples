package id.digisys.android.kotlinmvpsamples.ui.main

import android.content.Intent
import android.support.design.widget.TabLayout
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.PerformException
import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import android.view.View
import id.digisys.android.kotlinmvpsamples.R.id.*
import org.hamcrest.Matchers.allOf
import org.junit.runners.MethodSorters
import org.junit.FixMethodOrder

@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class MainActivityTest{

    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java, true, false)

    @Test
    fun step1_displayTeamDetailsAndClickFavorite(){

        activityRule.launchActivity(Intent())

        onView(withId(navigation_team)).check(matches(isDisplayed()))
        onView(withId(navigation_team)).perform(click())

        waiting()
        onView(withId(spinnerTeam)).check(matches(isDisplayed()))
        onView(withId(spinnerTeam)).perform(click())

        waiting()
        onView(withText("Spanish La Liga")).check(matches(isDisplayed()))
        onView(withText("Spanish La Liga")).perform(click())

        waiting(5000)
        onView(withId(rvTeam)).check(matches(isDisplayed()))
        onView(withId(rvTeam)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(13))
        waiting()
        onView(withId(rvTeam)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(13, click()))

        waiting(2000)
        onView(withId(favorite)).check(matches(isDisplayed()))
        onView(withId(favorite)).perform(click())
    }

    @Test
    fun step2_displayTeamFavorite(){

        activityRule.launchActivity(Intent())

        onView(withId(navigation_favorite)).check(matches(isDisplayed()))
        onView(withId(navigation_favorite)).perform(click())

        waiting()
        onView(withId(tabLayout)).check(matches(isDisplayed()))
        onView(withId(tabLayout)).perform(selectTabAtPosition(1))

        waiting()
        onView(withId(rvTeam)).check(matches(isDisplayed()))
    }

    private fun waiting(millis: Long = 1000){
        try {
            Thread.sleep(millis)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    private fun selectTabAtPosition(tabIndex: Int): ViewAction {
        return object : ViewAction {
            override fun getDescription() = "with tab at index $tabIndex"

            override fun getConstraints() = allOf(isDisplayed(), isAssignableFrom(TabLayout::class.java))

            override fun perform(uiController: UiController, view: View) {
                val tabLayout = view as TabLayout
                val tabAtIndex: TabLayout.Tab = tabLayout.getTabAt(tabIndex)
                    ?: throw PerformException.Builder()
                        .withCause(Throwable("No tab at index $tabIndex"))
                        .build()

                tabAtIndex.select()
            }
        }
    }
}

