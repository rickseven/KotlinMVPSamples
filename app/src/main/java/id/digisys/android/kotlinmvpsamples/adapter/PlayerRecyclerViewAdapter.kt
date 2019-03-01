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
import id.digisys.android.kotlinmvpsamples.model.player.Player
import id.digisys.android.kotlinmvpsamples.ui.team.player.detail.DetailPlayerTeamActivity
import kotlinx.android.synthetic.main.item_player.view.*

class PlayerRecyclerViewAdapter(private val playerList: List<Player>, private val context: Context?): RecyclerView.Adapter<PlayerRecyclerViewAdapter.PlayerViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        return PlayerViewHolder(LayoutInflater.from(context).inflate(R.layout.item_player, parent, false))
    }

    override fun getItemCount(): Int = playerList.size

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bind(playerList[position])
    }

    inner class PlayerViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bind(player: Player){
            itemView.tvPlayerName.text = player.strPlayer
            itemView.tvPlayerPosition.text = player.strPosition
            Glide.with(itemView.context)
                .load(player.strCutout)
                .apply(RequestOptions().placeholder(R.drawable.logo_football))
                .apply(RequestOptions().override(120, 140))
                .into(itemView.imageCutout)

            itemView.cvPlayer.setOnClickListener{
                val context = itemView.context
                val intent = Intent(context, DetailPlayerTeamActivity::class.java)
                intent.putExtra("player", player)
                context.startActivity(intent)
            }
        }
    }
}