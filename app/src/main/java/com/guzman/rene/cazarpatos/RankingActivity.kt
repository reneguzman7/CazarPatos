package com.guzman.rene.cazarpatos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RankingActivity : AppCompatActivity() {

    private lateinit var recyclerViewRanking: RecyclerView
    private lateinit var rankingAdapter: RankingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)

        // Initialize RecyclerView
        recyclerViewRanking = findViewById(R.id.recyclerViewRanking)

        // Create player list
        val players = ArrayList<Player>()
        players.add(Player("rene.guzman", 150))
        players.add(Player("juan.perez", 120))
        players.add(Player("maria.lopez", 95))
        players.add(Player("carlos.rodriguez", 80))
        players.add(Player("ana.martinez", 65))

        // Sort by huntedDucks in descending order
        val sortedPlayers = players.sortedByDescending { it.huntedDucks }

        // Configure RecyclerView
        recyclerViewRanking.layoutManager = LinearLayoutManager(this)
        rankingAdapter = RankingAdapter(sortedPlayers)
        recyclerViewRanking.adapter = rankingAdapter
        recyclerViewRanking.setHasFixedSize(true)
    }
}
