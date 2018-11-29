package com.ampersanda.footballmatchschedule.ui.ffavouritematch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ampersanda.footballmatchschedule.LocalDatabase
import com.ampersanda.footballmatchschedule.R
import com.ampersanda.footballmatchschedule.data.Event
import com.ampersanda.footballmatchschedule.ui.adapters.MatchAdapter
import com.ampersanda.footballmatchschedule.ui.detail.DetailActivity

class FavouriteMatchFragment : Fragment(), FavouriteMatchFragmentView {

    private lateinit var layoutView: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressLoading: ProgressBar
    private lateinit var labelNoData: TextView
    private var eventList : MutableList<Event> = mutableListOf()
    private val presenter = FavouriteMatchFragmentPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        layoutView = inflater.inflate(R.layout.fragment_favourite_match, container, false)

        recyclerView = layoutView.findViewById(R.id.frag_recycler_favourite_match)
        progressLoading = layoutView.findViewById(R.id.frag_loading_favourite_match)
        labelNoData = layoutView.findViewById(R.id.frag_label_no_data)

        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        return layoutView
    }

    override fun setLoading() {
        progressLoading.visibility = View.VISIBLE
        recyclerView.visibility = View.INVISIBLE
        labelNoData.visibility = View.INVISIBLE
    }

    override fun hideLoading() {
        progressLoading.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    override fun setLoadingNoData() {
        recyclerView.visibility = View.INVISIBLE
        labelNoData.visibility = View.VISIBLE
    }

    override fun onStart() {
        super.onStart()

        setLoading()
        val database = LocalDatabase(context!!)

        presenter.selectAllFromLocalDB(database, eventList) { isDataAvailable: Boolean, eventList: MutableList<Event> ->
            if (isDataAvailable){
                hideLoading()

                recyclerView.adapter = MatchAdapter(eventList) { event ->
                    startActivity(context?.let { DetailActivity.newIntent(it, event) })
                }
            } else {
                hideLoading()
                setLoadingNoData()
            }
        }
    }
}
