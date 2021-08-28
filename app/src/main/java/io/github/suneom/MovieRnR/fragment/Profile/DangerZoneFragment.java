package io.github.suneom.MovieRnR.fragment.Profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import io.github.suneom.MovieRnR.R;
import io.github.suneom.MovieRnR.util.sRequest;

public class DangerZoneFragment extends Fragment {
    View rootView;

    Button deleteButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.profile_danger, container,false);

        deleteButton = rootView.findViewById(R.id.profile_delete_button);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sRequest.requestDeleteAccount(getContext());
            }
        });

        return  rootView;
    }
}
