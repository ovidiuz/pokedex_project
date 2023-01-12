package com.example.pokedexproject.viewmodels

import androidx.lifecycle.*
import com.example.pokedexproject.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException

private const val TAG = "PokemonListviewModel"

class PokemonListViewModel constructor(private val repository: PokemonRepository) : ViewModel() {
    val pokemonList = repository.pokemons

    private var _eventNetworkError = MutableLiveData<Boolean>(false)
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        refreshDataFromRepository()
    }

    private fun refreshDataFromRepository() {
        viewModelScope.launch(Dispatchers.IO) {
            Timber.d("$TAG: calling refreshDataFromRepository")
            try {
                repository.refreshPokemons()
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            } catch (networkError: IOException) {
                Timber.e("$TAG: caught IOException error = $networkError")
                if(pokemonList.value.isNullOrEmpty())
                    _eventNetworkError.postValue(true)
            }
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.postValue(true)
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