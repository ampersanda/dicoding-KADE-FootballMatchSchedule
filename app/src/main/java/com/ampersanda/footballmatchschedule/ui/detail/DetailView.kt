package com.ampersanda.footballmatchschedule.ui.detail

import com.ampersanda.footballmatchschedule.data.Event

interface DetailView {
    fun changeStar()
    fun showAlertAskDeleteFavourite()
    fun updateUI(event: Event)
}