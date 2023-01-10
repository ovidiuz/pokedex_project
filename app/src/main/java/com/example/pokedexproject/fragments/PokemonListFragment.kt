package com.example.pokedexproject.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pokedexproject.R
import timber.log.Timber

private const val TAG = "PokemonsListFragment"

class PokemonListFragment : Fragment(R.layout.list_fragment) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //TODO("implement me")
        return super.onCreateView(inflater, container, savedInstanceState)
        Timber.d("$TAG: onViewCreated")
    }
}