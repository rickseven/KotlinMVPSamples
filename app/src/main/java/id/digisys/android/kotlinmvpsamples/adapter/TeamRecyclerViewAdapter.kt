package id.digisys.android.kotlinmvpsamples.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.digisys.android.kotlinmvpsamples.R
import id.digisys.android.kotlinmvpsamples.model.team.Team
import id.digisys.android.kotlinmvpsamples.ui.team.detail.DetailTeamActivity
import kotlinx.android.synthetic.main.item_team.view.*

class TeamRecyclerViewAdapter(private val teamList: List<Team>, private val context: Context?) : RecyclerView.Adapter<TeamRecyclerViewAdapter.TeamViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        return TeamViewHolder(LayoutInflater.from(context).inflate(R.layout.item_team, parent, false))
    }

    override fun getItemCount(): Int = teamList.size

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bind(teamList[position])
    }

    inner class TeamViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bind(team: Team){
            itemView.tvTeam.text = team.strTeam
            Glide.with(itemView.context)
                    .load(team.strTeamBadge)
                    .apply(RequestOptions().placeholder(R.drawable.logo_football))
                    .into(itemView.imgTeam)

            itemView.cvTeam.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context,DetailTeamActivity::class.java)
                intent.putExtra("team", team)
                context.startActivity(intent)
            }
        }
    }
}