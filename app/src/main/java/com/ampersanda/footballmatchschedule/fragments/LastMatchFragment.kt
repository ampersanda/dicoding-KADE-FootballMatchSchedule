package com.ampersanda.footballmatchschedule.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ampersanda.footballmatchschedule.APIConfiguration
import com.ampersanda.footballmatchschedule.DetailActivity
import com.ampersanda.footballmatchschedule.R
import com.ampersanda.footballmatchschedule.adapters.MatchAdapter
import com.ampersanda.footballmatchschedule.dataclasses.EventResponse
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.StringRequestListener
import com.google.gson.Gson

class LastMatchFragment : Fragment() {

    private lateinit var layoutView: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressLoading: ProgressBar

    private fun setLoading() {
        progressLoading.visibility = View.VISIBLE
        recyclerView.visibility = View.INVISIBLE
    }

    fun hideLoading() {
        progressLoading.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        layoutView = inflater.inflate(R.layout.fragment_last_match, container, false)
        recyclerView = layoutView.findViewById(R.id.frag_recycler_last_match)
        progressLoading = layoutView.findViewById(R.id.frag_loading_last_match)

        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        setLoading()

        AndroidNetworking.get(APIConfiguration.getLastFifteenMatchURL(APIConfiguration.LEAGUE_ENGLISH_PREMIERE))
                .build()
                .getAsString(object : StringRequestListener {
                    override fun onResponse(response: String?) {
                        val gson = Gson()
                        val eventResponse: EventResponse = gson.fromJson(response, EventResponse::class.java)

                        recyclerView.adapter = MatchAdapter(eventResponse.eventList) {
                            event -> startActivity(context?.let { DetailActivity.newIntent(it, event) })
                        }

                        hideLoading()
                    }

                    override fun onError(anError: ANError?) {
                        hideLoading()
                    }
                })

        return layoutView
    }
}
