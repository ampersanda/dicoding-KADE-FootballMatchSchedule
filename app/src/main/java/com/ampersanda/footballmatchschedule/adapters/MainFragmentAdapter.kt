package com.ampersanda.footballmatchschedule.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ampersanda.footballmatchschedule.fragments.LastMatchFragment
import com.ampersanda.footballmatchschedule.fragments.NextMatchFragment

class MainFragmentAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    private var fragmentList : MutableList<Fragment> = mutableListOf()
    private var titleList : MutableList<String> = mutableListOf()

    init {
        titleList.add("LAST MATCH")
        titleList.add("NEXT MATCH")

        fragmentList.add(LastMatchFragment())
        fragmentList.add(NextMatchFragment())
    }

    override fun getItem(position: Int): Fragment = fragmentList[position]
    override fun getCount(): Int = fragmentList.size
    override fun getPageTitle(position: Int): CharSequence? = titleList[position]
}