package com.ampersanda.footballmatchschedule.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ampersanda.footballmatchschedule.APIConfiguration
import com.ampersanda.footballmatchschedule.R
import com.ampersanda.footballmatchschedule.dataclasses.Event

class MatchAdapter(private val listOfLastMatch: MutableList<Event>, private var clickListener: (Event) -> Unit) : RecyclerView.Adapter<MatchAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dateMatch: TextView = itemView.findViewById(R.id.match_item_date)

        private val nameHome: TextView = itemView.findViewById(R.id.match_item_home)
        private val nameAway: TextView = itemView.findViewById(R.id.match_item_away)

        private val scoreHome: TextView = itemView.findViewById(R.id.match_item_home_score)
        private val scoreAway: TextView = itemView.findViewById(R.id.match_item_away_score)


        fun bind(event: Event, clickListener: (Event) -> Unit) {
            dateMatch.text = APIConfiguration.getFormattedDate(event.dateEvent)

            nameHome.text = event.strHomeTeam
            nameAway.text = event.strAwayTeam

            scoreHome.text = APIConfiguration.getBlankStringWhenNull(event.intHomeScore)
            scoreAway.text = APIConfiguration.getBlankStringWhenNull(event.intAwayScore)

            itemView.setOnClickListener { _ ->
                clickListener(event)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchAdapter.ViewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.match_item, parent, false))

    override fun getItemCount(): Int = listOfLastMatch.size

    override fun onBindViewHolder(holder: MatchAdapter.ViewHolder, position: Int) {
        holder.bind(listOfLastMatch[position], clickListener)
    }

}