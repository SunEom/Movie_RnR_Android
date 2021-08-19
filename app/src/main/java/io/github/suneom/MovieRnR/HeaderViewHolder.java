package io.github.suneom.MovieRnR;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HeaderViewHolder extends RecyclerView.ViewHolder {
    TextView headerViewHolderTitle;

    public HeaderViewHolder(@NonNull View itemView,String title) {
        super(itemView);

        headerViewHolderTitle = itemView.findViewById(R.id.header_viewholder_title);
        headerViewHolderTitle.setText(title);
    }
}
