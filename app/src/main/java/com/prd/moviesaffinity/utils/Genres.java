package com.prd.moviesaffinity.utils;

import java.util.HashMap;

public class Genres {

    private static final HashMap<Integer, String> genres = new HashMap<Integer, String>() {{
        put(28, "Action");
        put(12, "Adventure");
        put(16, "Animation");
        put(35, "Comedy");
        put(80, "Crime");
        put(99, "Documentary");
        put(18, "Drama");
        put(10751, "Family");
        put(14, "Fantasy");
        put(36, "History");
        put(27, "Horror");
        put(10402, "Music");
        put(9648, "Mystery");
        put(10749, "Romance");
        put(878, "Science Fiction");
        put(10770, "TV Movie");
        put(53, "Thriller");
        put(10752, "War");
        put(37, "Western");
    }};

    public static HashMap<Integer, String> getRealGenres() {
        return genres;
    }
}
