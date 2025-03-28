package com.prd.moviesaffinity.di;

import static com.prd.moviesaffinity.data.local.MovieDatabase.MIGRATION_1_2;
import static com.prd.moviesaffinity.utils.Constant.BASE_URL;
import static com.prd.moviesaffinity.utils.Constant.DATABASE_NAME;

import android.content.Context;

import androidx.room.Room;

import com.prd.moviesaffinity.data.MovieRepository;
import com.prd.moviesaffinity.data.local.LocalDataSource;
import com.prd.moviesaffinity.data.local.MovieDatabase;
import com.prd.moviesaffinity.data.remote.MovieService;
import com.prd.moviesaffinity.data.remote.RemoteDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
abstract class AppModule {

    @Provides
    @Singleton
    static Retrofit provideRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    static MovieService provideMovieService(Retrofit retrofit){
        return retrofit.create(MovieService.class);
    }

    @Singleton
    @Provides
    static MovieRepository provideMovieRepository(LocalDataSource localDataSource, RemoteDataSource remoteDataSource){
        return new MovieRepository(localDataSource, remoteDataSource);
    }

    @Singleton
    @Provides
    static LocalDataSource provideLocalDataSource(MovieDatabase db){
        return new LocalDataSource(db.movieDao());
    }

    @Singleton
    @Provides
    static RemoteDataSource provideRemoteDataSource(MovieService movieService){
        return new RemoteDataSource(movieService);
    }

    @Singleton
    @Provides
    static MovieDatabase provideDataBase(@ApplicationContext Context context) {
        return Room.databaseBuilder(
                context,
                MovieDatabase.class,
                DATABASE_NAME)
                .addMigrations(MIGRATION_1_2)
                .build();
    }
}
