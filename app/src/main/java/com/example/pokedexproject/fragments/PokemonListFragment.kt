package com.example.pokedexproject.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pokedexproject.R
import com.example.pokedexproject.adapters.PokemonListAdapter
import com.example.pokedexproject.database.getDatabase
import com.example.pokedexproject.databinding.ListFragmentBinding
import com.example.pokedexproject.repository.PokemonRepository
import com.example.pokedexproject.viewmodels.PokemonListViewModel
import timber.log.Timber

private const val TAG = "PokemonsListFragment"

class PokemonListFragment : Fragment(R.layout.list_fragment) {
    private val viewModel: PokemonListViewModel by lazy {
        val repository = PokemonRepository(getDatabase(requireContext()))
        val viewModelFactory = PokemonListViewModel.Factory(repository)
        ViewModelProvider(this, viewModelFactory).get(PokemonListViewModel::class.java)
    }
    private var viewModelAdapter: PokemonListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Timber.d("$TAG: onViewCreated")
        viewModel.pokemonList.observe(viewLifecycleOwner, Observer {
            it?.let { viewModelAdapter?.submitList(it) }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.d("$TAG: onCreateView")

        val binding: ListFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.list_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        viewModelAdapter = PokemonListAdapter()
        binding.listFragmentRecyclerview.adapter = viewModelAdapter
        return binding.root
    }
}