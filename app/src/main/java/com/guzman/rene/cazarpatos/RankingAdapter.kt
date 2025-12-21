package com.guzman.rene.cazarpatos

import android.graphics.Typeface
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RankingAdapter(private val players: List<Player>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_HEADER = 0
        const val VIEW_TYPE_ITEM = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) VIEW_TYPE_HEADER else VIEW_TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rankinglist, parent, false)
        return RankingViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val rankingHolder = holder as RankingViewHolder

        if (getItemViewType(position) == VIEW_TYPE_HEADER) {
            // Header row
            val positionText = SpannableString("Pos")
            positionText.setSpan(UnderlineSpan(), 0, positionText.length, 0)
            rankingHolder.textViewPosicion.text = positionText
            rankingHolder.textViewPosicion.setTextColor(holder.itemView.context.getColor(R.color.colorAccent))
            rankingHolder.textViewPosicion.setTypeface(null, Typeface.BOLD)

            val ducksText = SpannableString(holder.itemView.context.getString(R.string.textducks))
            ducksText.setSpan(UnderlineSpan(), 0, ducksText.length, 0)
            rankingHolder.textViewPatosCazados.text = ducksText
            rankingHolder.textViewPatosCazados.setTextColor(holder.itemView.context.getColor(R.color.colorAccent))
            rankingHolder.textViewPatosCazados.setTypeface(null, Typeface.BOLD)

            val playerText = SpannableString(holder.itemView.context.getString(R.string.textplayer))
            playerText.setSpan(UnderlineSpan(), 0, playerText.length, 0)
            rankingHolder.textViewUsuario.text = playerText
            rankingHolder.textViewUsuario.setTextColor(holder.itemView.context.getColor(R.color.colorAccent))
            rankingHolder.textViewUsuario.setTypeface(null, Typeface.BOLD)
        } else {
            // Data row
            val playerIndex = position - 1
            val player = players[playerIndex]

            rankingHolder.textViewPosicion.text = position.toString()
            rankingHolder.textViewPosicion.setTextColor(holder.itemView.context.getColor(android.R.color.white))
            rankingHolder.textViewPosicion.setTypeface(null, Typeface.NORMAL)

            rankingHolder.textViewPatosCazados.text = player.huntedDucks.toString()
            rankingHolder.textViewPatosCazados.setTextColor(holder.itemView.context.getColor(android.R.color.white))
            rankingHolder.textViewPatosCazados.setTypeface(null, Typeface.NORMAL)

            rankingHolder.textViewUsuario.text = player.username
            rankingHolder.textViewUsuario.setTextColor(holder.itemView.context.getColor(android.R.color.white))
            rankingHolder.textViewUsuario.setTypeface(null, Typeface.NORMAL)
        }
    }

    override fun getItemCount(): Int {
        return players.size + 1 // +1 for header
    }

    class RankingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewPosicion: TextView = itemView.findViewById(R.id.textViewPosicion)
        val textViewPatosCazados: TextView = itemView.findViewById(R.id.textViewPatosCazados)
        val textViewUsuario: TextView = itemView.findViewById(R.id.textViewUsuario)
    }
}
