package ht.bekend.flixter.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Movie {


     String title;
     String overview;
     String poster_path;
     String backdrop_path;

     //constructor
    public Movie(JSONObject jsonObject) throws JSONException {
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        poster_path = jsonObject.getString("poster_path");
        backdrop_path = jsonObject.getString("backdrop_path");
    }
    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < movieJsonArray.length(); i++)
        {
          movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }
        return movies;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s",poster_path);
    }
    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s",backdrop_path);
    }

}
