package io.github.suneom.MovieRnR;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    ArrayList<Movie> items = new ArrayList<Movie>();

    public void setItems(ArrayList<Movie> items){
        this.items = items;
    }

    public void addItem(Movie item){
        items.add(item);
    }

    public Movie getItem(int position){
        return items.get(position);
    }

    public void setItem(int position, Movie item){
        items.set(position, item);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.movie_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.ViewHolder holder, int position) {
        Movie item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView_title;
        TextView textView_description;
        TextView textView_comment_count;
        TextView textView_rates;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView_title = itemView.findViewById(R.id.textView_title);
            textView_description = itemView.findViewById(R.id.textView_description);
            textView_comment_count = itemView.findViewById(R.id.textView_comment_count);
            textView_rates = itemView.findViewById(R.id.textView_rates);
        }

        public void setItem(Movie item){
            textView_title.setText(item.getTitle());
            textView_description.setText(item.getDescription());
            textView_comment_count.setText(item.getCommentCount());
            textView_rates.setText(String.valueOf(item.getRates()));
        }
    }
}
