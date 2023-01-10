package com.example.pokedexproject.database

import androidx.room.TypeConverter
import com.example.pokedexproject.models.Pokemon
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

fun List<PokemonEntity>.asDomainModel(): List<Pokemon> {
    return map {
        Pokemon(
            name = it.name,
            apiId = it.apiId,
            image = it.image,
            type = it.type,
            height = it.height,
            weight = it.weight,
            abilities = it.abilities,
            moves = it.moves,
            stats = it.stats.map { stat -> Pokemon.Stat(stat.name, stat.baseStat) }
        )
    }
}