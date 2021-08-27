package io.github.suneom.MovieRnR.recycler_view.Adapter;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.github.suneom.MovieRnR.Listener.OnMovieItemClickListener;
import io.github.suneom.MovieRnR.custom_class.Movie.Movie;
import io.github.suneom.MovieRnR.R;
import io.github.suneom.MovieRnR.recycler_view.ViewHolder.HeaderViewHolder;
import io.github.suneom.MovieRnR.util.sRequest;
import io.github.suneom.MovieRnR.util.sUtil;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OnMovieItemClickListener{
    ArrayList<Movie> items = new ArrayList<Movie>();
    OnMovieItemClickListener listener;

    public String header_title;

    int[] MovieCardImageRes = {
            R.drawable.movie_photo_1,
            R.drawable.movie_photo_2,
            R.drawable.movie_photo_3,
            R.drawable.movie_photo_4,
            R.drawable.movie_photo_5,
            R.drawable.movie_photo_6,
            R.drawable.movie_photo_7,
            R.drawable.movie_photo_8,
    };

    private final int TYPE_HEADER = 0;
    private final int TYPE_ITEM = 1;

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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == 0){
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_header, parent, false);
            return new HeaderViewHolder(itemView, header_title);
        } else if( viewType == 1){
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.movie_item, parent, false);
            return new ViewHolder(itemView, (OnMovieItemClickListener) this);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolder){
            Movie item = items.get(position-1); // 'position : 0'은 viewholder_header가 차지하고 있으므로 position-1번째 값을 가져와야한다.
            ((ViewHolder) holder).setItem(item);
        }
    }

    @Override
    public int getItemCount() {
        return items.size() + 1;
    }

    public void setOnItemClickListener(OnMovieItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder, view, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return TYPE_HEADER;
        } else{
            return TYPE_ITEM;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public int id;
        ImageView imageView_movieCard;
        TextView textView_title;
        TextView textView_description;
        TextView textView_comment_count;
        TextView textView_rates;

        public ViewHolder(@NonNull View itemView, final OnMovieItemClickListener listener) {
            super(itemView);

            imageView_movieCard = itemView.findViewById(R.id.imageView_movieCard);
            textView_title = itemView.findViewById(R.id.textView_title);
            textView_description = itemView.findViewById(R.id.textView_description);
            textView_comment_count = itemView.findViewById(R.id.textView_comment_count);
            textView_rates = itemView.findViewById(R.id.textView_rates);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if(listener != null){
                        listener.onItemClick(ViewHolder.this, v, position);
                    }
                }
            });
        }

        public void setItem(Movie item){
            id = item.getId();
            imageView_movieCard.setImageResource(MovieCardImageRes[sUtil.createRandomMovieCardImageIndex()]);
            textView_title.setText(item.getTitle());
            textView_description.setText(item.getDescription());
            textView_comment_count.setText(String.valueOf(item.getCommentCount()));
            textView_rates.setText(String.valueOf(item.getRates()));
        }
    }
}
