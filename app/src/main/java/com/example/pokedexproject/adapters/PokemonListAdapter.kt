package com.example.pokedexproject.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.pokedexproject.models.Pokemon
import timber.log.Timber

private const val TAG = "PokemonListAdapter"

class PokemonListAdapter : ListAdapter<Pokemon, ViewHolder>(PokemonDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Timber.d("$TAG: onCreateViewHolder()")
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Timber.d("$TAG: onBindViewHolder()")
        TODO("Not yet implemented")
    }
}

class PokemonDiffCallback : DiffUtil.ItemCallback<Pokemon>() {
    override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
        return oldItem == newItem
    }
}
