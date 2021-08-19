package io.github.suneom.MovieRnR;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<Movie> items = new ArrayList<Movie>();

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
            return new HeaderViewHolder(itemView,"Recent Postings");
        } else if( viewType == 1){
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.movie_item, parent, false);
            return new ViewHolder(itemView);
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

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return TYPE_HEADER;
        } else{
            return TYPE_ITEM;
        }
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
            textView_comment_count.setText(String.valueOf(item.getCommentCount()));
            textView_rates.setText(String.valueOf(item.getRates()));
        }
    }
}
