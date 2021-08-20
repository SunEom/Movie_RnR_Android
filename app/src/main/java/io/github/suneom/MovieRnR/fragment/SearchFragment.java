package io.github.suneom.MovieRnR.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.github.suneom.MovieRnR.recycler_view.Adapter.MovieAdapter;
import io.github.suneom.MovieRnR.R;
import io.github.suneom.MovieRnR.util.sRequest;

public class SearchFragment extends Fragment {
    private static final String TAG ="SearchFragment";

    MovieAdapter adapter;
    RecyclerView recyclerView;

    String keyword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home,container, false);

        EditText editText = getActivity().findViewById(R.id.search_keyword_input);
        keyword = getArguments().getString("keyword");

        Log.d(TAG,keyword);

        recyclerView = rootView.findViewById(R.id.recyclerView_home);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        adapter = new MovieAdapter();
        adapter.header_title = "Search Result for : "+keyword;
        recyclerView.setAdapter(adapter);

        if(keyword != null && !keyword.equals("")){
            sRequest.requestSearchPostings(adapter, keyword);
        }

        return rootView;
    }
}