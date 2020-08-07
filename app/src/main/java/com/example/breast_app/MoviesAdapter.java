package com.example.breast_app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    private List<MovieModel> moviesList;
    private boolean withTop;
    private Context context;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, year;
        ImageView imageView, topImage;
        View view;
        MyViewHolder(View view) {
            super(view);
            this.view = view;
            imageView = view.findViewById(R.id.imageview);
            title = view.findViewById(R.id.title);
            year = view.findViewById(R.id.year);
        }
    }
    public MoviesAdapter(List<MovieModel> moviesList, boolean withTop, Context context) {
        this.moviesList = moviesList;
        this.withTop = withTop;
        this.context = context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView ;
        final MoviesAdapter.MyViewHolder holder;
        if (withTop) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.blogs_list, parent, false);

            holder = new MyViewHolder(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();

                    Intent intent = new Intent(context, ArticleDisplay.class);
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });
        }
        else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.movies_list, parent, false);

            holder = new MyViewHolder(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();

                    Intent intent = new Intent(context, YoutubeVideo.class);
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });
        }
        return holder;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MovieModel movie = moviesList.get(position);
        holder.imageView.setImageDrawable(movie.getImage());
        if (withTop) {
            holder.topImage = holder.view.findViewById(R.id.topImage);
            holder.topImage.setImageDrawable(movie.getTopImage());
        }
        holder.title.setText(movie.getTitle());
        holder.year.setText(movie.getYear());
    }
    @Override
    public int getItemCount() {
        return moviesList.size();
    }

}