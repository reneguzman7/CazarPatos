package com.guzman.rene.cazarpatos

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.guzman.rene.cazarpatos.database.RankingPlayerDBHelper

class RankingActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ranking)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Limpiar y grabar datos en SQLite
        GrabarRankingSQLite()
        
        // Leer datos de SQLite y mostrar en RecyclerView
        LeerRankingsSQLite()
        
        // Pruebas unitarias (opcional para debug)
        OperacionesSqLite()
    }

    private fun OperacionesSqLite() {
        RankingPlayerDBHelper(this).deleteAllRanking()
        RankingPlayerDBHelper(this).insertRankingByQuery(Player("Jugador9", 10))
        val patosCazados = RankingPlayerDBHelper(this).readDucksHuntedByPlayer("Jugador9")
        RankingPlayerDBHelper(this).updateRanking(Player("Jugador9", 5))
        RankingPlayerDBHelper(this).deleteRanking("Jugador9")
        RankingPlayerDBHelper(this).insertRanking(Player("Jugador9", 7))
        val players = RankingPlayerDBHelper(this).readAllRankingByQuery()
    }

    private fun GrabarRankingSQLite() {
        // Limpiar antes de insertar
        RankingPlayerDBHelper(this).deleteAllRanking()
        
        // Crear lista de jugadores con tu nombre.apellido en el primero
        val jugadores = arrayListOf(
            Player("rene.guzman", 11),
            Player("Jugador2", 6),
            Player("Jugador3", 3),
            Player("Jugador4", 9)
        )
        
        // Ordenar por patos cazados descendente
        jugadores.sortByDescending { it.huntedDucks }
        
        // Insertar cada jugador en la BD
        for (jugador in jugadores) {
            RankingPlayerDBHelper(this).insertRanking(jugador)
        }
    }

    private fun LeerRankingsSQLite() {
        val jugadoresSQLite = RankingPlayerDBHelper(this).readAllRanking()
        val recyclerViewRanking = findViewById<RecyclerView>(R.id.recyclerViewRanking)
        recyclerViewRanking.layoutManager = LinearLayoutManager(this)
        recyclerViewRanking.adapter = RankingAdapter(jugadoresSQLite)
        recyclerViewRanking.setHasFixedSize(true)
    }
}
