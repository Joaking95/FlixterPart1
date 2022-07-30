package ht.bekend.flixter.Adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.RoundedCorner;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.Rotate;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import org.parceler.Parcels;

import java.util.List;

import ht.bekend.flixter.DetailActivity;
import ht.bekend.flixter.MainActivity;
import ht.bekend.flixter.Models.Movie;
import ht.bekend.flixter.R;

public class AdapterMovie extends RecyclerView.Adapter<AdapterMovie.ViewHolder>{


    Context context;
    List<Movie> movies;

    public AdapterMovie(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View movieview = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       Movie movie = movies.get(position);
       holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle;
        TextView tvOverv;
        ImageView ivIm;
        RelativeLayout container;
        TextView tvDOveriew;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverv = itemView.findViewById(R.id.tvOverview);
            ivIm = itemView.findViewById(R.id.ivPoster);
            container = itemView.findViewById(R.id.container);
            tvDOveriew = itemView.findViewById(R.id.tvDOverview);
        }


        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverv.setText(movie.getOverview());
            String ImageUrl;
            int radius= 10;
            int margin = 30;
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                ImageUrl = movie.getBackdropPath();
            } else {
                ImageUrl = movie.getPosterPath();
            }
            Glide.with(context).load(ImageUrl).placeholder(R.drawable.item_placeholder)
                    .error(R.drawable.error_404).apply(RequestOptions.bitmapTransform(new RoundedCorners(45))).into(ivIm);
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v ) {
                      checkRippleAnimation(v);
                }
                public void checkRippleAnimation(View view){
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("movie", Parcels.wrap(movie));
                    context.startActivity(intent);
                }

            });

        }
        
    }
}
