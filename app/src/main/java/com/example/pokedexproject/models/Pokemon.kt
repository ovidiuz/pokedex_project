package com.example.pokedexproject.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pokemon(val name: String,
                   val apiId: Int,
                   val image: String,
                   val type: String,
                   val height: Int,
                   val weight: Int,
                   val abilities: List<String>,
                   val moves: List<String>,
                   val stats: List<Stat>) : Parcelable {
    @Parcelize
    data class Stat (
        val name: String,
        val baseStat: Int) : Parcelable
}