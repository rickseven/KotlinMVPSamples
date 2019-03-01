package id.digisys.android.kotlinmvpsamples.adapter

import android.support.v4.app.FragmentManager
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter

class ViewPagerAdapter(fragmentManager: FragmentManager?): FragmentPagerAdapter(fragmentManager){

    private var fragmentList = arrayListOf<Fragment>()
    private var titleList = arrayListOf<String>()

    fun populateFragment(fragment: Fragment, title: String){
        fragmentList.add(fragment)
        titleList.add(title)
    }

    override fun getItem(position: Int): Fragment = fragmentList[position]

    override fun getCount(): Int = fragmentList.size

    override fun getPageTitle(position: Int) = titleList[position]
}