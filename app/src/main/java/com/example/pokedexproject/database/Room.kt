package com.example.pokedexproject.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pokedexproject.util.Constants

@Dao
interface PokemonDao {
    @Query("SELECT * FROM ${Constants.TABLE_NAME} ORDER BY api_id ASC")
    fun getPokemons(): LiveData<List<PokemonEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pokemon: PokemonEntity)
}

@Database(entities = [PokemonEntity::class], version = 12, exportSchema = false)
@TypeConverters(Converters::class)
abstract class PokemonDatabase: RoomDatabase() {
    abstract val pokemonDao: PokemonDao
}

private lateinit var INSTANCE: PokemonDatabase

fun getDatabase(context: Context): PokemonDatabase {
    synchronized(PokemonDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext, PokemonDatabase::class.java, Constants.TABLE_NAME).build()
        }
    }
    return INSTANCE
}