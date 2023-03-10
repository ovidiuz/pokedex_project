package com.example.pokedexproject.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pokedexproject.util.Constants

@Entity(tableName = Constants.TABLE_NAME)
data class PokemonEntity constructor(
    @PrimaryKey
    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "api_id")
    val apiId: Int,

    @ColumnInfo(name = "image")
    val image: String,

    @ColumnInfo(name = "type")
    val type: String,

    @ColumnInfo(name = "height")
    val height: Int,

    @ColumnInfo(name = "weight")
    val weight: Int,

    @ColumnInfo(name = "abilities")
    val abilities: List<String>,

    @ColumnInfo(name = "moves")
    val moves: List<String>,

    @ColumnInfo(name = "stats")
    val stats: List<Stat>,

    ) {
    data class Stat(
        val name: String,
        val baseStat: Int)
}
