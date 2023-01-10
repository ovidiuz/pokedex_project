package com.example.pokedexproject.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.pokedexproject.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException

private const val TAG = "PokemonListviewModel"

class PokemonListViewModel constructor(private val repository: PokemonRepository) : ViewModel() {
    val pokemonList = repository.pokemons

    init {
        refreshDataFromRepository()
    }

    private fun refreshDataFromRepository() {
        viewModelScope.launch(Dispatchers.IO) {
            Timber.d("$TAG: calling refreshDataFromRepository")
            try {
                repository.refreshPokemons()
            } catch (networkError: IOException) {
                Timber.e("$TAG: caught IOException error = $networkError")
            }
        }
    }

    class Factory(private val repository: PokemonRepository) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PokemonListViewModel::class.java)) {
                return PokemonListViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}