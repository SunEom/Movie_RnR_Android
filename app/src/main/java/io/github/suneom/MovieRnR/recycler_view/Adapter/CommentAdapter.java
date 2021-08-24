package io.github.suneom.MovieRnR.recycler_view.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.github.suneom.MovieRnR.R;
import io.github.suneom.MovieRnR.custom_class.Comment;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.Viewholder> {

    ArrayList<Comment> items = new ArrayList<Comment>();

    public void setItems(ArrayList<Comment> items){
        this.items = items;
    }

    public void addItem(Comment item){
        items.add(item);
    }

    public Comment getItem(int position){
        return items.get(position);
    }

    public void setItem(int position, Comment item){
        items.set(position, item);
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.comment_item, parent, false);

        return new Viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.Viewholder holder, int position) {
        Comment item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        TextView commenter, content;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            commenter = itemView.findViewById(R.id.comment_commenter);
            content = itemView.findViewById(R.id.comment_content);
        }

        public void setItem(Comment comment){
            commenter.setText(comment.getNickname());
            content.setText(comment.getContents());
        }
    }
}
