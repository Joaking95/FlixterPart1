package ht.bekend.flixter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import ht.bekend.flixter.Models.Movie;
import okhttp3.Headers;


import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import java.lang.reflect.Modifier;

public class DetailActivity extends YouTubeBaseActivity {

    public static String YOUTUBE_API_KEY = "AIzaSyDXzlFOMcjv0AtfsDkcoPzf6QRO05Ooq4g";
    public static String VIDEO_URL = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    YouTubePlayerView youTubePlayerView;
    TextView tvDTitle;
    TextView tvDOverview;
    RatingBar rbBAR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        tvDTitle = findViewById(R.id.tvDTitle);
        tvDOverview = findViewById(R.id.tvDOverview);
        rbBAR = findViewById(R.id.ratingBar);
        youTubePlayerView = findViewById(R.id.player);
        Movie movie= Parcels.unwrap(getIntent().getParcelableExtra("movie"));
        tvDTitle.setText(movie.getTitle());
        tvDOverview.setText(movie.getOverview());
        rbBAR.setRating((int) movie.getVote_average());

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.format(VIDEO_URL, movie.getId()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                try {
                    JSONArray results = json.jsonObject.getJSONArray("results");
                    if(results.length()== 0){
                        return;
                    }
                     String youtubeKey = results.getJSONObject(0).getString("key");
                     Log.d("DetailActivity",youtubeKey);
                     initializeYoutube(youtubeKey);
                } catch (JSONException e) {
                    Log.e("DetailActivity","failled to parse JSON",e);
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

            }
        });
    }

    private void initializeYoutube(final String youtubeKey) {

        youTubePlayerView.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d("DetailActivity","onInitializationSuccess");

                // do any work here to cue video, play video, etc.
                youTubePlayer.cueVideo(youtubeKey);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d("DetailActivity","onInitializationFailure");
            }
        });
    }
}