package io.github.suneom.MovieRnR.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import io.github.suneom.MovieRnR.R;
import io.github.suneom.MovieRnR.custom_class.Comment;
import io.github.suneom.MovieRnR.recycler_view.Adapter.CommentAdapter;
import io.github.suneom.MovieRnR.util.sRequest;

public class DetailActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CommentAdapter adapter;
    TextView noCommentAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        recyclerView = findViewById(R.id.recyclerView_comment);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        adapter = new CommentAdapter();

        recyclerView.setAdapter(adapter);

        sRequest.requestCommentList(adapter,1, this);


    }
}