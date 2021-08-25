package io.github.suneom.MovieRnR.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import io.github.suneom.MovieRnR.R;
import io.github.suneom.MovieRnR.custom_class.Detail.PostingOwner;
import io.github.suneom.MovieRnR.custom_class.Movie.MovieData;
import io.github.suneom.MovieRnR.recycler_view.Adapter.CommentAdapter;
import io.github.suneom.MovieRnR.util.sRequest;
import io.github.suneom.MovieRnR.util.sUtil;

public class DetailActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CommentAdapter adapter;
    TextView title, genres, rates, overview, created, nickname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail);

        int id = getIntent().getIntExtra("id",-1);

        recyclerView = findViewById(R.id.recyclerView_comment);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        adapter = new CommentAdapter();

        recyclerView.setAdapter(adapter);

        title = findViewById(R.id.detail_title);
        genres = findViewById(R.id.detail_genres);
        rates = findViewById(R.id.detail_rates);
        overview = findViewById(R.id.detail_overview);
        created = findViewById(R.id.detail_createdAt);
        nickname = findViewById(R.id.detail_createdBy);

//        sRequest.requestCommentList(adapter, id, this);
//        sRequest.requestPostingDetail(id, this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    public void setInfo(MovieData movie, PostingOwner owner){
        title.setText(movie.title);
        genres.setText(movie.genres);
        rates.setText("⭐ " + String.valueOf(movie.rates)+"/ 10");
        overview.setText(movie.overview);
        created.setText(sUtil.ParseDateTilDate(movie.created));
        nickname.setText(owner.getNickname());
    }
}