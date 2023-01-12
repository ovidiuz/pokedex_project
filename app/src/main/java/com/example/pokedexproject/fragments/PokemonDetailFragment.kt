package com.example.pokedexproject.fragments

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.pokedexproject.R
import com.example.pokedexproject.databinding.DetailFragmentBinding
import com.example.pokedexproject.models.Pokemon
import com.example.pokedexproject.network.LoadImage
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

    private fun refreshViews(pokemon: Pokemon) {
        Timber.d("$TAG: Setting up pokemon detail view with pokemon ${pokemon.name}")
        // Setup the Pokemon name
        binding.pokemonName.text = pokemon.name.capitalize()

        // Setup the height and weight
        // Converting the height from decimeters to centimeters
        val height = pokemon.height * 10
        // Converting the weight from hectograms to kilograms
        val weight = pokemon.weight / 10.0
        binding.heightText.text = "${height.toString().capitalize()} cm"
        binding.weightText.text = "${weight.toString().capitalize()} kg"

        // Setup the Pokemon Type
        binding.pokemonTypeText.text = pokemon.type.capitalize()

        // Setup the Pokemon abilities
        Timber.d("$TAG: Setting up the pokemon abilities")
        for (ability in pokemon.abilities) {
            val textView = TextView(requireContext())
            textView.text = "${ability.capitalize()}"
            textView.textSize = resources.getDimension(R.dimen.tiny_text_size)
            textView.setTextColor(resources.getColor(R.color.white))
            textView.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            binding.abilitiesContainer.addView(textView)
        }

        // Setup the Pokemon moves
        Timber.d("$TAG: Setting up the pokemon moves")
        for (move in pokemon.moves) {
            val textView = TextView(requireContext())
            textView.text = "${move.capitalize()}"
            textView.textSize = resources.getDimension(R.dimen.tiny_text_size)
            textView.setTextColor(resources.getColor(R.color.white))
            textView.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            binding.movesContainer.addView(textView)
        }

        // Setup the Pokemon stats
        Timber.d("$TAG: Setting up the pokemon stats")
        for (stat in pokemon.stats) {
            val textView = TextView(requireContext())
            val bar = ProgressBar(requireContext(), null, android.R.attr.progressBarStyleHorizontal)
            bar.progress = stat.baseStat ?: 0
            bar.progressTintList = ColorStateList.valueOf(Color.WHITE)
            textView.text = "${stat.name.capitalize()}: ${stat.baseStat}"
            textView.textSize = resources.getDimension(R.dimen.tiny_text_size)
            textView.setTextColor(resources.getColor(R.color.white))
            textView.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            binding.statsContainer.addView(textView)
            binding.statsContainer.addView(bar)
        }

        // Setup the image
        Timber.d("$TAG: Setting up the pokemon image from url: ${pokemon.image}")
        LoadImage.loadImage(requireContext(), binding.pokemonImg, pokemon.image)
    }
}