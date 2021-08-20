package io.github.suneom.MovieRnR.recycler_view.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import io.github.suneom.MovieRnR.R;

public class HeaderViewHolder extends RecyclerView.ViewHolder {
    TextView headerViewHolderTitle;

    public HeaderViewHolder(@NonNull View itemView,String title) {
        super(itemView);

        headerViewHolderTitle = itemView.findViewById(R.id.header_viewholder_title);
        headerViewHolderTitle.setText(title);
    }
}
