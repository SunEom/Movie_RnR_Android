package io.github.suneom.MovieRnR.recycler_view.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.github.suneom.MovieRnR.R;
import io.github.suneom.MovieRnR.activity.MainActivity;
import io.github.suneom.MovieRnR.application.MyApplication;
import io.github.suneom.MovieRnR.custom_class.Comment.Comment;
import io.github.suneom.MovieRnR.fragment.DetailFragment;
import io.github.suneom.MovieRnR.fragment.ProfileFragment;
import io.github.suneom.MovieRnR.util.sRequest;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.Viewholder> {

    ArrayList<Comment> items = new ArrayList<Comment>();

    FragmentManager manager;

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

    public void setFragmentManager (FragmentManager manager) {
        this.manager = manager;
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
        Button edit, delete, cancel, save;
        EditText comment_edit;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            commenter = itemView.findViewById(R.id.comment_commenter);
            content = itemView.findViewById(R.id.comment_content);

            edit = itemView.findViewById(R.id.comment_edit_button);
            delete = itemView.findViewById(R.id.comment_delete_button);
            cancel = itemView.findViewById(R.id.comment_edit_cancel_button);
            save = itemView.findViewById(R.id.comment_save_button);

            comment_edit = itemView.findViewById(R.id.comment_edit_input);
        }

        public void setItem(Comment comment){
            commenter.setText(comment.getNickname());
            content.setText(comment.getContents());

            commenter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProfileFragment profileFragment = new ProfileFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", comment.getCommenter());
                    profileFragment.setArguments(bundle);

                    manager.beginTransaction().replace(R.id.fragment_container,profileFragment).commit();
                }
            });

            if(MyApplication.my_info != null && MyApplication.my_info.my_id == comment.getCommenter()){
                edit.setVisibility(View.VISIBLE);
                delete.setVisibility(View.VISIBLE);

                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        content.setVisibility(View.GONE);
                        edit.setVisibility(View.GONE);
                        delete.setVisibility(View.GONE);
                        comment_edit.setVisibility(View.VISIBLE);
                        comment_edit.setText(content.getText().toString());
                        cancel.setVisibility(View.VISIBLE);
                        save.setVisibility(View.VISIBLE);

                        // 댓글 수정 저장을 위한 Listener
                        save.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Context context = v.getContext();
                                String content_value = comment_edit.getText().toString();
                                String posting_id = String.valueOf(comment.getMovie_id());
                                String comment_id = String.valueOf(comment.getId());

                                sRequest.requestPatchComment(content_value, posting_id, comment_id,CommentAdapter.this, (MainActivity)context);

                                content.setVisibility(View.VISIBLE);
                                edit.setVisibility(View.VISIBLE);
                                delete.setVisibility(View.VISIBLE);
                                comment_edit.setVisibility(View.GONE);
                                cancel.setVisibility(View.GONE);
                                save.setVisibility(View.GONE);
                            }
                        });

                        // 댓글 수정 취소를 위한 Listener
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                content.setVisibility(View.VISIBLE);
                                edit.setVisibility(View.VISIBLE);
                                delete.setVisibility(View.VISIBLE);
                                comment_edit.setVisibility(View.GONE);
                                cancel.setVisibility(View.GONE);
                                save.setVisibility(View.GONE);
                            }
                        });
                    }
                });

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Context context = v.getContext();
                        String posting_id = String.valueOf(comment.getMovie_id());
                        String comment_id = String.valueOf(comment.getId());

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);

                        builder.setMessage("Do you really want to delete this comment?");

                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });

                        builder.setPositiveButton("Sure", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sRequest.requestDeleteComment(posting_id, comment_id, CommentAdapter.this, (MainActivity)context);
                            }
                        });


                        AlertDialog alertDialog = builder.create();

                        alertDialog.show();
                        return;
                    }
                });
            }
        }
    }
}
