package io.github.suneom.MovieRnR.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import io.github.suneom.MovieRnR.R;
import io.github.suneom.MovieRnR.util.sRequest;

public class JoinFragment extends Fragment {
    View rootView;

    EditText id, password, passwordCheck, nickname;
    Button idConfirm, nickConfirm;

    public boolean isIdChecked = false;
    public boolean isNickChecked = false;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_join, container, false);

        initViews();
        setListener();

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

    public void setListener(){
        idConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sRequest.requestConfirmIdDuplication(id.getText().toString(), JoinFragment.this);
            }
        });

        nickConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sRequest.requestConfirmNickDuplication(nickname.getText().toString(), JoinFragment.this);
            }
        });

        id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                isIdChecked = false;
            }
        });

        nickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                isNickChecked = false;
            }
        });
    }
}
