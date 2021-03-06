package io.github.suneom.MovieRnR.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import io.github.suneom.MovieRnR.activity.MainActivity;
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
    Button comment_button, delete_button, edit_button;

    LinearLayout button_group;

    public int movieId;
    public int postingOwnerId;

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
        delete_button = rootView.findViewById(R.id.detail_delete_button);
        edit_button = rootView.findViewById(R.id.detail_edit_button);

        button_group = rootView.findViewById(R.id.detail_button_group);

        if(MyApplication.my_info != null){
            comment_input.setEnabled(true);
            comment_button.setEnabled(true);

            comment_input.setHint("Leave a comment ...");
        }

        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditFragment editFragment = new EditFragment();
                editFragment.movieId = movieId;
                editFragment.titleValue = title.getText().toString();
                editFragment.genresValue = genres.getText().toString();
                editFragment.ratesValue = rates.getText().toString();
                editFragment.overviewValue = overview.getText().toString();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, editFragment).commit();
            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sUtil.CreatePostDeleteCheckAlertDialog(movieId, DetailFragment.this);
            }
        });

        comment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(comment_input.getText() == null || comment_input.getText().toString().equals("")) {
                    sUtil.CreateNewSimpleAlertDialog(getContext(), "", "????????? ??????????????????!");
                }

                String content = comment_input.getText().toString();
                sRequest.requestNewComment(content, String.valueOf(movieId),adapter, getActivity());
                comment_input.setText("");
                ((InputMethodManager)((MainActivity)v.getContext()).getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(comment_input.getWindowToken(), 0);
            }
        });

        sRequest.requestCommentList(adapter, movieId, getActivity());
        sRequest.requestPostingDetail(movieId, getActivity(), this);

        return rootView;
    }

    public void setInfo(MovieData movie, PostingOwner owner) {
        title.setText(movie.title);
        genres.setText(movie.genres);
        rates.setText("??? " + String.valueOf(movie.rates) + "/ 10");
        overview.setText(movie.overview);
        created.setText(sUtil.ParseDateTilDate(movie.created));
        nickname.setText("By " + owner.getNickname());

        nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileFragment profileFragment = new ProfileFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("id", owner.getId());
                profileFragment.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,profileFragment).commit();
            }
        });

        if(MyApplication.my_info != null && MyApplication.my_info.id == postingOwnerId){
            button_group.setVisibility(View.VISIBLE);
        }
    }

    public void setVisiblityAfterLoad(){
        progressBar.setVisibility(View.GONE);
        scrollView.setVisibility(View.VISIBLE);
    }
}
