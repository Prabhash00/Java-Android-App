package com.prd.moviesaffinity.ui.favorite;

import static com.prd.moviesaffinity.data.local.MovieDatabase.databaseWriteExecutor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.prd.moviesaffinity.data.MovieRepository;
import com.prd.moviesaffinity.data.model.Movie;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
class FavoriteViewModel extends ViewModel {

    private final MovieRepository movieRepository;

    @Inject
    public FavoriteViewModel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public LiveData<List<Movie>> getAllFavoriteMovies() {
        return movieRepository.getAllMovies();
    }

    public void deleteAll() {
        databaseWriteExecutor.execute(movieRepository::deleteAll);
    }
}
