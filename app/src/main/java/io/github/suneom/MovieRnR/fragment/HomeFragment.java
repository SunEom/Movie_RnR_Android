package io.github.suneom.MovieRnR.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.github.suneom.MovieRnR.recycler_view.Adapter.MovieAdapter;
import io.github.suneom.MovieRnR.R;
import io.github.suneom.MovieRnR.util.sRequest;

public class HomeFragment extends Fragment {
    private final static String TAG = "HomeFragment";

    RecyclerView recyclerView;
    MovieAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = rootView.findViewById(R.id.recyclerView_home);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        adapter = new MovieAdapter();
        adapter.header_title = "Recent Postings";
        recyclerView.setAdapter(adapter);

        sRequest.requestRecentPostings(adapter);

        return rootView;
    }
}
