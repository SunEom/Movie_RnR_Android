package io.github.suneom.MovieRnR.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import io.github.suneom.MovieRnR.R;
import io.github.suneom.MovieRnR.util.sRequest;
import io.github.suneom.MovieRnR.util.sUtil;

public class JoinFragment extends Fragment {
    View rootView;

    EditText id, password, passwordCheck, nickname;
    Button idConfirm, nickConfirm, saveButton;

    RadioGroup genderRadioGroup;
    String genderValue;

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
        saveButton = rootView.findViewById(R.id.join_save_button);

        genderRadioGroup = rootView.findViewById(R.id.gender_group);
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

        genderRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.gender_man:
                        Log.d("Gender","Man");
                        genderValue = "Man";
                        break;
                    case R.id.gender_woman:
                        Log.d("Gender","Woman");
                        genderValue = "Woman";
                        break;
                    default:
                        break;
                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String idValue = id.getText().toString();
                String passwordValue = password.getText().toString();
                String passwordCheckValue = passwordCheck.getText().toString();
                String nicknameValue = nickname.getText().toString();

                if(idValue.equals("")){
                    sUtil.CreateNewSimpleAlertDialog(getContext(), "You did not enter ID", "Please enter the ID");
                    return;
                }

                if(passwordValue.equals("")){
                    sUtil.CreateNewSimpleAlertDialog(getContext(), "You did not enter password", "Please enter the password");
                    return;
                }

                if(passwordCheckValue.equals("")){
                    sUtil.CreateNewSimpleAlertDialog(getContext(), "You did not enter password check", "Please enter the password check");
                    return;
                }

                if(nicknameValue.equals("")){
                    sUtil.CreateNewSimpleAlertDialog(getContext(), "You did not enter nickname", "Please enter the nickname");
                    return;
                }

                if(genderValue == null || genderValue.equals("")){
                    sUtil.CreateNewSimpleAlertDialog(getContext(), "You did not select gender", "Please select the gender");
                    return;
                }

                if(!isIdChecked){
                    sUtil.CreateNewSimpleAlertDialog(getContext(), "You did not check 'ID' duplication.", "Click Confirmation button");
                    return;
                }

                if(!passwordValue.equals(passwordCheckValue)){
                    sUtil.CreateNewSimpleAlertDialog(getContext(), "Passwords are not the same", "Please check passwords again");
                    return;
                }

                if(!isNickChecked){
                    sUtil.CreateNewSimpleAlertDialog(getContext(), "You did not check 'Nickname' duplication.", "Click Confirmation button");
                    return;
                }

                sRequest.requestJoinUser(idValue, passwordValue, nicknameValue, genderValue, JoinFragment.this);

            }
        });

    }
}
