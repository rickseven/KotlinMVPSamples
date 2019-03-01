package id.digisys.android.kotlinmvpsamples.adapter

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.digisys.android.kotlinmvpsamples.R
import id.digisys.android.kotlinmvpsamples.model.event.Event
import id.digisys.android.kotlinmvpsamples.ui.match.detail.DetailMatchActivity
import id.digisys.android.kotlinmvpsamples.utility.DateHelper
import kotlinx.android.synthetic.main.item_match.view.*
import java.text.SimpleDateFormat
import java.util.*

class MatchRecyclerViewAdapter(private val eventList: List<Event>, private val context: Context?): RecyclerView.Adapter<MatchRecyclerViewAdapter.MatchViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        return MatchViewHolder(LayoutInflater.from(context).inflate(R.layout.item_match, parent, false))
    }

    override fun getItemCount(): Int = eventList.size

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        val event = eventList[position]
        if(event.strSport.toString().toLowerCase().trim() == "soccer")
            holder.bind(event)
    }

    inner class MatchViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bind(event: Event){
            val createDate = SimpleDateFormat("dd/MM/yy HH:mm", Locale.getDefault()).format(Date())
            val currentDate = SimpleDateFormat("dd/MM/yy HH:mm", Locale.getDefault()).parse(createDate)
            event.strDate = event.strDate ?: event.dateEvent.let { DateHelper.formatDateToString(it, "dd/MM/yy") }
            event.strTime = if(event.strTime.isNullOrEmpty()) "00:00" else event.strTime.toString().substring(0,5)
            val dateEvent = SimpleDateFormat("dd/MM/yy HH:mm", Locale.getDefault()).parse(event.strDate +" "+ event.strTime)

            if(dateEvent.after(currentDate)){
                itemView.tvDate.setTextColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
            }

            if(event.intHomeScore.toString() == "null")
                itemView.tvHomeScore.visibility = View.GONE

            if (event.intAwayScore.toString() == "null")
                itemView.tvAwayScore.visibility = View.GONE

            itemView.tvDate.text = dateEvent.let { DateHelper.formatDateToString(it) }
            itemView.tvHomeTeam.text = event.strHomeTeam
            itemView.tvHomeScore.text = event.intHomeScore
            itemView.tvAwayScore.text = event.intAwayScore
            itemView.tvAwayTeam.text = event.strAwayTeam
            itemView.cvMatch.setOnClickListener{
                val context = itemView.context
                val intent = Intent(context, DetailMatchActivity::class.java)
                intent.putExtra("event", event)
                context.startActivity(intent)
            }
        }
    }
}