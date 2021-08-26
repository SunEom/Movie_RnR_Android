package io.github.suneom.MovieRnR.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import io.github.suneom.MovieRnR.R;

public class JoinFragment extends Fragment {
    View rootView;

    EditText id, password, passwordCheck, nickname;
    Button idConfirm, nickConfirm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_join, container, false);

        initViews();

        return rootView;
    }

    public void initViews(){
        id = rootView.findViewById(R.id.join_id);
        password = rootView.findViewById(R.id.join_password);
        passwordCheck = rootView.findViewById(R.id.join_password_check);
        nickname = rootView.findViewById(R.id.join_nick);

        idConfirm = rootView.findViewById(R.id.join_id_confirm_button);
        nickConfirm = rootView.findViewById(R.id.join_nick_confirm_button);
    }

}
