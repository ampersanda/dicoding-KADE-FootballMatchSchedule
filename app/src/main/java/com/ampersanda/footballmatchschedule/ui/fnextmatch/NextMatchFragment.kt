package com.ampersanda.footballmatchschedule.ui.fnextmatch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ampersanda.footballmatchschedule.ui.detail.DetailActivity
import com.ampersanda.footballmatchschedule.R
import com.ampersanda.footballmatchschedule.ui.adapters.MatchAdapter

class NextMatchFragment : Fragment(), NextMatchFragmentView {
    private lateinit var layoutView : View
    private lateinit var recyclerView : RecyclerView
    private lateinit var progressLoading : ProgressBar
    private val presenter = NextMatchFragmentPresenter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        layoutView = inflater.inflate(R.layout.fragment_next_match, container, false)
        recyclerView = layoutView.findViewById(R.id.frag_recycler_next_match)
        progressLoading = layoutView.findViewById(R.id.frag_loading_next_match)

        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        setLoading()

        presenter.getMatches { isDone, eventResponse ->
            if (isDone && eventResponse != null){
                recyclerView.adapter = MatchAdapter(eventResponse.eventList){
                    event -> startActivity(context?.let { DetailActivity.newIntent(it, event) })
                }
            }
        }

        return layoutView
    }

    override fun setLoading(){
        progressLoading.visibility = View.VISIBLE
        recyclerView.visibility = View.INVISIBLE
    }

    override fun hideLoading(){
        progressLoading.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }
}
