package com.example.bootcampandroid;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private List<Movie> movies;
    private Context myContext;

    public MovieAdapter(Context context, List<Movie> list){
        this.myContext = context;
        movies = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivPoster;
        TextView tvMovieName, tvMovieDate, tvMovieDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivPoster = itemView.findViewById(R.id.ivPoster);
            tvMovieName = (TextView) itemView.findViewById(R.id.tvMovieName);
            tvMovieDate = (TextView) itemView.findViewById(R.id.tvMovieDate);
            tvMovieDesc = (TextView) itemView.findViewById(R.id.tvMovieDesc);

            //set listener for when item has been clicked
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        Movie clickedDataItem = movies.get(position);
                        Intent intent = new Intent(context, Details.class);
                        intent.putExtra("original_title",movies.get(position).getOriginalName());
                        intent.putExtra("name", movies.get(position).getName());
                        intent.putExtra("id", movies.get(position).getId());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        Toast.makeText(view.getContext(), "You clicked " + clickedDataItem.getOriginalName(), Toast.LENGTH_SHORT).show();
                    }*/
                }
            });
        }
    }

    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View newView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_layout, parent, false);

        return new ViewHolder(newView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.ViewHolder holder, int position) {

        //hold item of movie list at position to be able to put data in it
        holder.itemView.setTag(movies.get(position));

        holder.tvMovieName.setText(movies.get(position).getOriginalName());
        holder.tvMovieDate.setText(movies.get(position).getReleseDate());
        holder.tvMovieDesc.setText(movies.get(position).getDetails());
        holder.ivPoster.setImageResource(R.mipmap.poster_foreground);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
