package com.example.pokedexproject.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.pokedexproject.database.PokemonDatabase
import com.example.pokedexproject.database.PokemonEntity
import com.example.pokedexproject.database.asDomainModel
import com.example.pokedexproject.models.Pokemon
import com.example.pokedexproject.network.PokemonApiService
import com.example.pokedexproject.network.PokemonDto
import com.example.pokedexproject.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

private const val TAG = "PokemonRepository"

class PokemonRepository(private val database: PokemonDatabase) {
    val pokemons: LiveData<List<Pokemon>> = Transformations.map(database.pokemonDao.getPokemons()) {
        it.asDomainModel()
    }

    suspend fun refreshPokemons() {
        Timber.d("$TAG: refreshPokemons() is called")
        withContext(Dispatchers.IO) {
            if (pokemons.value?.isNotEmpty() == true) {
                return@withContext
            }

            val pokemonList = PokemonApiService.retrofitService.getPokemonsList(Constants.POKEMON_LIMIT)
            for (result in pokemonList.results) {
                val pokemonDetails = PokemonApiService.retrofitService.getPokemonDetail(result.name)
                database.pokemonDao.insert(pokemon = convertPokemonDtoToEntity(pokemonDetails))
            }
        }
    }

    private fun convertPokemonDtoToEntity(pokemonDto: PokemonDto) : PokemonEntity {
        val type = pokemonDto.types[0].type.name
        val image = pokemonDto.sprites.frontDefault
        val abilities = pokemonDto.abilities.map { ability -> ability.ability.name }
        val moves = pokemonDto.moves.map { move -> move.move.name }
        val stats = pokemonDto.stats.map { stat -> PokemonEntity.Stat(stat.stat.name, stat.baseStat) }

        return PokemonEntity(
            name = pokemonDto.name,
            apiId = pokemonDto.id,
            image = image,
            type = type,
            height = pokemonDto.height,
            weight = pokemonDto.weight,
            abilities = abilities,
            moves = moves,
            stats = stats
        )
    }
}