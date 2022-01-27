package com.the.firsttask.database

import androidx.room.*


@Dao
interface MovieDao {

    @Query("SELECT * FROM movieinfo ")
    fun getAllMovie():List<MovieEntity>?

//    @Query("SELECT * FROM movieinfo ")
//    fun getAllMovies():LiveData<List<MovieEntity>>


    @Insert
    fun insertMovie(movieEntity: MovieEntity?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movieEntity: List<MovieEntity>?)

    @Delete
    fun deleteMovie(movieEntity: MovieEntity?)

    @Update
    fun updateMovie(movieEntity: MovieEntity?)

}