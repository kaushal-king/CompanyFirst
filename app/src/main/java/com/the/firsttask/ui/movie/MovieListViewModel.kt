package com.the.firsttask.ui.movie

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.the.firsttask.database.MovieEntity
import com.the.firsttask.database.RoomDb

class MovieListViewModel(application: Application) : AndroidViewModel(application) {
    var allMovie: MutableLiveData<List<MovieEntity>> = MutableLiveData()

    init {
        getAllMovie()
    }


    fun getAllMovieObservers(): MutableLiveData<List<MovieEntity>> {
        return allMovie
    }

    fun getAllMovie() {
        val movieDao = RoomDb.getAppDatabase((getApplication()))?.movieDao()
        val list = movieDao?.getAllMovie()
        allMovie.postValue(list!!)

    }

    fun insertMovieInfo(entity: MovieEntity) {
        val movieDao = RoomDb.getAppDatabase((getApplication()))?.movieDao()
        movieDao?.insertMovie(entity)
        getAllMovie()
    }

    fun insertMoviesInfo(entity: List<MovieEntity>) {
        val movieDao = RoomDb.getAppDatabase((getApplication()))?.movieDao()
        movieDao?.insertMovies(entity)
        getAllMovie()
    }

    fun deleteMovieInfo(entity: MovieEntity) {
        val movieDao = RoomDb.getAppDatabase((getApplication()))?.movieDao()
        movieDao?.deleteMovie(entity)
        getAllMovie()
    }

    fun updateMovieInfo(entity: MovieEntity) {
        val movieDao = RoomDb.getAppDatabase((getApplication()))?.movieDao()
        movieDao?.updateMovie(entity)
        getAllMovie()
    }
}
