package io.github.suneom.MovieRnR.fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import io.github.suneom.MovieRnR.R;
import io.github.suneom.MovieRnR.activity.MainActivity;
import io.github.suneom.MovieRnR.application.MyApplication;
import io.github.suneom.MovieRnR.util.sRequest;
import io.github.suneom.MovieRnR.util.sUtil;

public class LogInFragment extends Fragment {

    EditText id, password;
    Button loginButton;
    CheckBox remember;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        id = rootView.findViewById(R.id.login_edittext_id);
        password = rootView.findViewById(R.id.login_edittext_password);
        loginButton = rootView.findViewById(R.id.login_button);
        remember =rootView.findViewById(R.id.login_remember_check);

        id.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                password.requestFocus();
                return true;
            }
        });

        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                loginButton.performClick();
                return true;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_value = id.getText().toString();
                String password_value = password.getText().toString();
                
                if(id_value.equals("")){
                    sUtil.CreateNewSimpleAlertDialog(getContext(), "","아이디를 입력해주세요");
                    return;
                }
                if(password_value.equals("")){
                    sUtil.CreateNewSimpleAlertDialog(getContext(), "","비밀번호를 입력해주세요");
                    return;
                }

                ((MainActivity)getActivity()).startLoading();
                sRequest.requestLoginPost(id_value, password_value, remember.isChecked(), getActivity());
            }
        });

        Button new_account_button = rootView.findViewById(R.id.new_account_button);
        new_account_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JoinFragment joinFragment = new JoinFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, joinFragment).commit();
            }
        });

        return rootView;
    }
}
