package io.github.suneom.MovieRnR.fragment.Profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.github.suneom.MovieRnR.Listener.OnMovieItemClickListener;
import io.github.suneom.MovieRnR.R;
import io.github.suneom.MovieRnR.fragment.DetailFragment;
import io.github.suneom.MovieRnR.recycler_view.Adapter.MovieAdapter;
import io.github.suneom.MovieRnR.util.sRequest;

public class ProfilePostingFragment extends Fragment {
    View rootView;

    RecyclerView recyclerView;
    MovieAdapter adapter;

    public int user_id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.profile_postings, container,false);

        initItemView();

        return  rootView;
    }

    public void initItemView(){
        recyclerView = rootView.findViewById(R.id.profile_postings_recyclerview);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        adapter = new MovieAdapter();
        adapter.header_title = "My Postings";
        adapter.setOnItemClickListener(new OnMovieItemClickListener() {
            @Override
            public void onItemClick(MovieAdapter.ViewHolder holder, View view, int position) {
                DetailFragment detailFragment = new DetailFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("id", holder.id);
                detailFragment.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,detailFragment).commit();
            }
        });

        recyclerView.setAdapter(adapter);

        sRequest.requestMyPostings(user_id, adapter, getActivity());
    }
}
