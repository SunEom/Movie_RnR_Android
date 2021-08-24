package io.github.suneom.MovieRnR.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import io.github.suneom.MovieRnR.R;
import io.github.suneom.MovieRnR.custom_class.Comment;
import io.github.suneom.MovieRnR.recycler_view.Adapter.CommentAdapter;

public class DetailActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CommentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        recyclerView = findViewById(R.id.recyclerView_comment);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        adapter = new CommentAdapter();

        adapter.addItem(new Comment("Suneom","hello"));
        adapter.addItem(new Comment("Suneom","hello2"));
        adapter.addItem(new Comment("Suneom","hello3"));

        recyclerView.setAdapter(adapter);
    }
}