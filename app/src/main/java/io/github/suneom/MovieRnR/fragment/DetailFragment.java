package io.github.suneom.MovieRnR.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.github.suneom.MovieRnR.R;
import io.github.suneom.MovieRnR.custom_class.Detail.PostingOwner;
import io.github.suneom.MovieRnR.custom_class.Movie.MovieData;
import io.github.suneom.MovieRnR.recycler_view.Adapter.CommentAdapter;
import io.github.suneom.MovieRnR.util.sRequest;
import io.github.suneom.MovieRnR.util.sUtil;

public class DetailFragment extends Fragment {

    RecyclerView recyclerView;
    CommentAdapter adapter;
    TextView title, genres, rates, overview, created, nickname;
    ProgressBar progressBar;
    ScrollView scrollView;

    int movieId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            movieId = bundle.getInt("id", -1);
        }

        recyclerView = rootView.findViewById(R.id.recyclerView_comment);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        adapter = new CommentAdapter();

        recyclerView.setAdapter(adapter);

        progressBar = rootView.findViewById(R.id.detail_progressBar);
        scrollView = rootView.findViewById(R.id.content_scrollView);

        title = rootView.findViewById(R.id.detail_title);
        genres = rootView.findViewById(R.id.detail_genres);
        rates = rootView.findViewById(R.id.detail_rates);
        overview = rootView.findViewById(R.id.detail_overview);
        created = rootView.findViewById(R.id.detail_createdAt);
        nickname = rootView.findViewById(R.id.detail_createdBy);

        sRequest.requestCommentList(adapter, movieId, getActivity());
        sRequest.requestPostingDetail(movieId, getActivity(), this);

        return rootView;
    }

    public void setInfo(MovieData movie, PostingOwner owner){
        title.setText(movie.title);
        genres.setText(movie.genres);
        rates.setText("⭐ " + String.valueOf(movie.rates)+"/ 10");
        overview.setText(movie.overview);
        created.setText(sUtil.ParseDateTilDate(movie.created));
        nickname.setText(owner.getNickname());
    }

    public void setVisiblityAfterLoad(){
        progressBar.setVisibility(View.GONE);
        scrollView.setVisibility(View.VISIBLE);
    }
}
