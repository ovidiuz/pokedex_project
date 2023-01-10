package com.example.pokedexproject.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromListString(value: List<String>): String {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().toJson(value, type)
    }

    @TypeConverter
    fun toListString(value: String?): List<String>? {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson<List<String>>(value, type)
    }

    @TypeConverter
    fun fromPokemonStat(value: List<PokemonEntity.Stat>?): String? {
        val type = object : TypeToken<List<PokemonEntity.Stat>>() {}.type
        return Gson().toJson(value, type)
    }

    @TypeConverter
    fun toPokemonStat(value: String?): List<PokemonEntity.Stat>? {
        val type = object : TypeToken<List<PokemonEntity.Stat>>() {}.type
        return Gson().fromJson<List<PokemonEntity.Stat>>(value, type)
    }
}