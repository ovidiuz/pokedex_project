package com.example.pokedexproject.network

import com.example.pokedexproject.util.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {
    @GET("/api/v2/pokemon")
    suspend fun getPokemonsList(@Query("limit") limit: Int): PokemonListDto

    @GET("/api/v2/pokemon/{id}")
    suspend fun getPokemonDetail(@Path("id") id: String): PokemonDto
}

object PokemonApiService {
    private val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val retrofitService: PokemonApi = retrofit.create(PokemonApi::class.java)
}