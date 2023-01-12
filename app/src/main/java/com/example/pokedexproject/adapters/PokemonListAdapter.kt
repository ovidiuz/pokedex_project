package com.example.pokedexproject.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedexproject.R
import com.example.pokedexproject.databinding.RowItemBinding
import com.example.pokedexproject.models.Pokemon
import com.example.pokedexproject.network.LoadImage
import com.example.pokedexproject.util.Constants
import timber.log.Timber

private const val TAG = "PokemonListAdapter"

class PokemonListAdapter : ListAdapter<Pokemon, PokemonListAdapter.ViewHolder>(PokemonDiffCallback()) {
    class ViewHolder private constructor(private val binding: RowItemBinding, private val navController: NavController) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemon: Pokemon) {
            Timber.d("$TAG: calling PokemonListAdapter.ViewHolder.bind() with pokemon ${pokemon.name}")

            // Setup the views inside the RecyclerView row
            binding.rowItemCardTitle.text = pokemon.name.capitalize()
            binding.rowItemCardType.text = "Type: ${pokemon.type.capitalize()}"
            binding.rowItemCardView.setOnClickListener {
                val bundle = Bundle()
                bundle.putParcelable(Constants.BUNDLE_PROPERTY, pokemon)
                navController.navigate(R.id.action_list_to_detail, bundle)
            }

            // setup image
            pokemon.image.let { LoadImage.loadImage(itemView.context, binding.rowItemCardImage, it) }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                Timber.d("$TAG: calling PokemonListAdapter.ViewHolder.from()")
                val navController = parent.findFragment<Fragment>().findNavController()
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RowItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding, navController)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Timber.d("$TAG: onCreateViewHolder()")
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Timber.d("$TAG: onBindViewHolder()")
        val item = getItem(position)
        holder.bind(item)
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
