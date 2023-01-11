package com.example.pokedexproject.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pokedexproject.models.Pokemon

class PokemonDetailViewModel constructor(private val pokemon: Pokemon) : ViewModel() {
    // Saving Pokemon as a LiveData property in case it will ever be updated
    private val _pokemonProperty : MutableLiveData<Pokemon> = MutableLiveData(pokemon)
    val pokemonProperty: LiveData<Pokemon>
        get() = _pokemonProperty

    class Factory(private val pokemon: Pokemon) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PokemonDetailViewModel::class.java)) {
                return PokemonDetailViewModel(pokemon) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}