package io.github.suneom.MovieRnR.Listener;

import android.view.View;

import io.github.suneom.MovieRnR.recycler_view.Adapter.MovieAdapter;

public interface OnMovieItemClickListener {
    public void onItemClick(MovieAdapter.ViewHolder holder, View view,int position);
}
