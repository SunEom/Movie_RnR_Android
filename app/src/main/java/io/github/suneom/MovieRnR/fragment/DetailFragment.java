package io.github.suneom.MovieRnR.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.github.suneom.MovieRnR.R;
import io.github.suneom.MovieRnR.application.MyApplication;
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

    EditText comment_input;
    Button comment_button;

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
        adapter.setFragmentManager(getActivity().getSupportFragmentManager());

        recyclerView.setAdapter(adapter);

        progressBar = rootView.findViewById(R.id.detail_progressBar);
        scrollView = rootView.findViewById(R.id.content_scrollView);

        title = rootView.findViewById(R.id.detail_title);
        genres = rootView.findViewById(R.id.detail_genres);
        rates = rootView.findViewById(R.id.detail_rates);
        overview = rootView.findViewById(R.id.detail_overview);
        created = rootView.findViewById(R.id.detail_createdAt);
        nickname = rootView.findViewById(R.id.detail_createdBy);

        comment_button = rootView.findViewById(R.id.comment_button);
        comment_input = rootView.findViewById(R.id.comment_input);

        if(MyApplication.my_info != null){
            comment_input.setEnabled(true);
            comment_button.setEnabled(true);

            comment_input.setHint("Leave a comment ...");
        }
        comment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(comment_input.getText() == null || comment_input.getText().toString().equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                    builder.setMessage("댓글을 입력해주세요!");
                    builder.setNeutralButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

                    AlertDialog alertDialog = builder.create();

                    alertDialog.show();
                    return;
                }

                String content = comment_input.getText().toString();
                sRequest.requestNewComment(content, String.valueOf(movieId),adapter, getActivity());
                comment_input.setText("");
            }
        });

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
