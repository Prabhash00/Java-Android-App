package com.prd.moviesaffinity.data.remote;

import static com.prd.moviesaffinity.utils.Constant.API_KEY;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.prd.moviesaffinity.data.model.Movie;
import com.prd.moviesaffinity.data.model.MovieApiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDataSource extends PageKeyedDataSource<Integer, Movie> {

    private static final String TAG = "MovieDataSource";

    private static final int FIRST_PAGE = 1;
    public static final int PAGE_SIZE = 20;

    private final MovieService movieService;
    private final String sort;

    public MovieDataSource(MovieService movieService, String sort) {
        this.movieService = movieService;
        this.sort = sort;
    }

    @Override
    public void loadInitial(@NonNull final LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Movie> callback) {
        movieService.getMovies(sort, FIRST_PAGE, API_KEY)
                .enqueue(new Callback<MovieApiResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieApiResponse> call, @NonNull Response<MovieApiResponse> response) {
                        Log.d(TAG , "Succeeded movies");
                        if (response.body().getMovies() == null) {
                            return;
                        }

                        if (response.body() != null) {
                            // Fetch database and pass the result null for the previous page
                            callback.onResult(response.body().getMovies(), null, FIRST_PAGE + 1);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MovieApiResponse> call, @NonNull Throwable t) {
                        Log.d(TAG , t.getMessage());
                    }
                });


    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Movie> callback) {
        movieService.getMovies(sort, params.key, API_KEY)
                .enqueue(new Callback<MovieApiResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieApiResponse> call, @NonNull Response<MovieApiResponse> response) {
                        /*
                         If the current page is greater than one,
                         we are decrementing the page number
                         else there is no previous page.
                        */
                        Integer adjacentKey = (params.key > 1) ? params.key - 1 : null;
                        if (response.body() != null) {

                            // Passing the loaded database and the previous page key
                            callback.onResult(response.body().getMovies(), adjacentKey);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MovieApiResponse> call, @NonNull Throwable t) {
                        Log.d(TAG, t.getMessage());
                    }
                });
    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Movie> callback) {
        movieService.getMovies(sort, params.key, API_KEY)
                .enqueue(new Callback<MovieApiResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieApiResponse> call, @NonNull Response<MovieApiResponse> response) {
                        if (response.body() != null) {
                            // If the response has next page, increment the next page number
                            Integer key = response.body().getMovies().size() == PAGE_SIZE ? params.key + 1 : null;

                            // Passing the loaded database and next page value
                            callback.onResult(response.body().getMovies(), key);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MovieApiResponse> call, @NonNull Throwable t) {
                        Log.d(TAG, t.getMessage());
                    }
                });
    }
}
