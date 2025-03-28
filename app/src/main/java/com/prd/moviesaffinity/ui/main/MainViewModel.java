package com.prd.moviesaffinity.ui.main;

import static com.prd.moviesaffinity.data.remote.MovieDataSourceFactory.movieDataSource;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.prd.moviesaffinity.data.MovieRepository;
import com.prd.moviesaffinity.data.model.Movie;
import com.prd.moviesaffinity.data.model.MovieApiResponse;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@HiltViewModel
public class MainViewModel extends ViewModel {

    private static final String TAG = "MainViewModel";

    private final MutableLiveData<List<Movie>> mutableSearchedList = new MutableLiveData<>();

    public LiveData<PagedList<Movie>> moviePagedList;
    private final MovieRepository movieRepository;

    @Inject
    public MainViewModel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public void loadMovies(String sort){
        this.moviePagedList = movieRepository.getRemoteMovies(sort);
    }

    public void invalidateDataSource(){
        if(movieDataSource != null) movieDataSource.invalidate();
    }

    public LiveData<List<Movie>> getSearchedMovies(String query) {
        movieRepository.getSearchedList(query)
                .enqueue(new Callback<MovieApiResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieApiResponse> call, @NonNull Response<MovieApiResponse> response) {
                        Log.d(TAG, response.body() + " Movies");

                        if (response.body() != null) {
                            List<Movie> searchedList = response.body().getMovies();
                            mutableSearchedList.setValue(searchedList);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MovieApiResponse> call, @NonNull Throwable t) {
                        Log.d(TAG, t.getMessage());
                    }
                });

        return mutableSearchedList;
    }
}
