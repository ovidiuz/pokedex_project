package com.example.pokedexproject.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.pokedexproject.database.getDatabase
import com.example.pokedexproject.repository.PokemonRepository
import retrofit2.HttpException
import timber.log.Timber

private const val TAG = "RefreshDataWorker"

class RefreshDataWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "com.example.android.devbyteviewer.work.RefreshDataWorker"
    }
    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val repository = PokemonRepository(database)

        try {
            repository.refreshPokemons()
            Timber.d("$TAG: Work request for sync is run")
        } catch (e: HttpException) {
            return Result.retry()
        }

        return Result.success()
    }
}