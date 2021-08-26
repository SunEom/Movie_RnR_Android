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
import io.github.suneom.MovieRnR.util.sRequest;

public class LogInFragment extends Fragment {

    EditText id, password;
    Button loginButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        id = rootView.findViewById(R.id.login_edittext_id);
        password = rootView.findViewById(R.id.login_edittext_password);
        loginButton = rootView.findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_value = id.getText().toString();
                String password_value = password.getText().toString();
                sRequest.requestLoginPost(id_value, password_value, getContext());
            }
        });

        Button button = rootView.findViewById(R.id.new_account_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JoinFragment joinFragment = new JoinFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, joinFragment).commit();
            }
        });

        return rootView;
    }
}
