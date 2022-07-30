package ht.bekend.flixter;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ht.bekend.flixter.Adapter.AdapterMovie;
import ht.bekend.flixter.Models.Movie;
import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    public static final String URL_NOW_PLAY ="https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed ";
    public static final String TAG = "MainActivity";
    List<Movie> movies;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rvMovies = findViewById(R.id.rvMovies);
        movies = new ArrayList<>();

        AdapterMovie adapterMovie = new AdapterMovie(this, movies);

        rvMovies.setAdapter(adapterMovie);

        rvMovies.setLayoutManager(new LinearLayoutManager(this));



        AsyncHttpClient client = new AsyncHttpClient();
        client.get(URL_NOW_PLAY, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "SUCCES");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG,"results"+results.toString());
                    movies.addAll(Movie.fromJsonArray(results));
                    adapterMovie.notifyDataSetChanged();
                    Log.i(TAG, "MOVIES" +movies.size());
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(TAG, "hit jsonArray success", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
               Log.d(TAG, "ERROR");
            }
        });
    }
}