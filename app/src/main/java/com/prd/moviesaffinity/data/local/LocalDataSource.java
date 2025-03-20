package com.prd.moviesaffinity.data.local;

import androidx.lifecycle.LiveData;

import com.prd.moviesaffinity.data.model.Movie;

import java.util.List;

import javax.inject.Inject;

public class LocalDataSource {

    private final MovieDao mMovieDao;

    @Inject
    public LocalDataSource(MovieDao mMovieDao){
        this.mMovieDao = mMovieDao;
    }

    public void insert(Movie movie) {
        mMovieDao.insert(movie);
    }

    public void deleteById(int movie_id){
        mMovieDao.deleteById(movie_id);
    }

    public LiveData<List<Movie>> getAllMovies(){
        return mMovieDao.getAllMovies();
    }

    public void deleteAll(){
        mMovieDao.deleteAll();
    }
}
