package com.prd.moviesaffinity.data;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.prd.moviesaffinity.data.local.LocalDataSource;
import com.prd.moviesaffinity.data.model.CastApiResponse;
import com.prd.moviesaffinity.data.model.Movie;
import com.prd.moviesaffinity.data.model.MovieApiResponse;
import com.prd.moviesaffinity.data.model.ReviewApiResponse;
import com.prd.moviesaffinity.data.model.TrailerApiResponse;
import com.prd.moviesaffinity.data.remote.MovieDataSource;
import com.prd.moviesaffinity.data.remote.RemoteDataSource;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;

public class MovieRepository {

    private final RemoteDataSource remoteDataSource;
    private final LocalDataSource localDataSource;

    // Get PagedList configuration
    private final PagedList.Config pagedListConfig =
            (new PagedList.Config.Builder())
                    .setEnablePlaceholders(false)
                    .setPageSize(MovieDataSource.PAGE_SIZE).build();

    @Inject
    public MovieRepository(LocalDataSource localDataSource, RemoteDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    public LiveData<PagedList<Movie>> getRemoteMovies(String sort) {
        // Build the paged list
        return new LivePagedListBuilder<>(remoteDataSource.getRemoteMovies(sort), pagedListConfig).build();
    }

    public Call<MovieApiResponse> getSearchedList(String query) {
        return remoteDataSource.getSearchedList(query);
    }

    public Call<TrailerApiResponse> getTrailerList() {
        return remoteDataSource.getTrailerList();
    }

    public Call<ReviewApiResponse> getReviewList() {
        return remoteDataSource.getReviewList();
    }

    public Call<CastApiResponse> getCastList() {
        return remoteDataSource.getCastList();
    }

    public LiveData<List<Movie>> getAllMovies() {
        return localDataSource.getAllMovies();
    }

    public void insert(Movie movie) {
         localDataSource.insert(movie);
    }

    public void deleteById(int movieId) {
        localDataSource.deleteById(movieId);
    }

    public void deleteAll() {
        localDataSource.deleteAll();
    }
}
