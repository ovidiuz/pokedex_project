package com.example.pokedexproject.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.pokedexproject.R
import com.example.pokedexproject.databinding.DetailFragmentBinding
import com.example.pokedexproject.models.Pokemon
import com.example.pokedexproject.util.Constants
import com.example.pokedexproject.viewmodels.PokemonDetailViewModel
import timber.log.Timber

private const val TAG = "PokemonDetailFragment"

class PokemonDetailFragment : Fragment(R.layout.detail_fragment) {
    private lateinit var binding: DetailFragmentBinding
    private var viewModel: PokemonDetailViewModel? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Timber.d("$TAG: onViewCreated")
        binding = DetailFragmentBinding.bind(view)
        binding.lifecycleOwner = viewLifecycleOwner

        requireArguments().let {
            it.getParcelable<Pokemon>(Constants.BUNDLE_PROPERTY).let { pokemon ->
                pokemon?.let {
                    Timber.i("$TAG: Creating PokemonDetailViewModel with the following $pokemon object")
                    val viewModelFactory = PokemonDetailViewModel.Factory(pokemon)
                    viewModel = viewModelFactory.create(PokemonDetailViewModel::class.java)
                }
            }
        }

        viewModel?.pokemonProperty?.observe(viewLifecycleOwner) { pokemon ->
            Timber.d("$TAG: Refreshing the existing views")
            refreshViews(pokemon)
        }
    }

    private fun refreshViews(pokemon: Pokemon?) {
        TODO("Not yet implemented")
    }
}