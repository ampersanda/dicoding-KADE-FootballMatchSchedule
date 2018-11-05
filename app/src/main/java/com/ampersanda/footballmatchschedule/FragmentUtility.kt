package com.ampersanda.footballmatchschedule

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

object FragmentUtility {
    fun changeFragment(fm : FragmentManager, fragment : Fragment){
        val transaction : FragmentTransaction = fm.beginTransaction()
        transaction.replace(R.id.main_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}