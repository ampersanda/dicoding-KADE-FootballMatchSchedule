package com.ampersanda.footballmatchschedule.ui.main.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ampersanda.footballmatchschedule.ui.flastmatch.LastMatchFragment
import com.ampersanda.footballmatchschedule.ui.fnextmatch.NextMatchFragment

class MainFragmentAdapter(sections: Array<String>, fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    private var fragmentList: MutableList<Fragment> = mutableListOf()
    private var titleList: MutableList<String> = mutableListOf()

    init {
        for (s in sections) titleList.add(s)

        fragmentList.add(LastMatchFragment())
        fragmentList.add(NextMatchFragment())
    }

    override fun getItem(position: Int): Fragment = fragmentList[position]
    override fun getCount(): Int = fragmentList.size
    override fun getPageTitle(position: Int): CharSequence? = titleList[position]
}