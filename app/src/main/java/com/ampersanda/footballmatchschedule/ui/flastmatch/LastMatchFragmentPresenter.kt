package com.ampersanda.footballmatchschedule.ui.flastmatch

import com.ampersanda.footballmatchschedule.APIConfiguration
import com.ampersanda.footballmatchschedule.data.EventResponse
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.StringRequestListener
import com.google.gson.Gson

class LastMatchFragmentPresenter (
        val fview : LastMatchFragmentView
){
    fun getMatches(onGetMatchDone: (isDone: Boolean, eventResponse : EventResponse?) -> Unit){
        AndroidNetworking.get(APIConfiguration.getLastFifteenMatchURL(APIConfiguration.LEAGUE_ENGLISH_PREMIERE))
                .build()
                .getAsString(object : StringRequestListener {
                    override fun onResponse(response: String?) {
                        val gson = Gson()
                        val eventResponse : EventResponse = gson.fromJson(response, EventResponse::class.java)

                        onGetMatchDone(true, eventResponse)

                        fview.hideLoading()
                    }

                    override fun onError(anError: ANError?) {
                        fview.hideLoading()

                        onGetMatchDone(true, null)
                    }
                })
    }
}