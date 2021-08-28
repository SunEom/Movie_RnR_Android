package io.github.suneom.MovieRnR.fragment.Profile;

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
import io.github.suneom.MovieRnR.util.sUtil;

public class PasswordChangeFragment extends Fragment {
    View rootView;

    EditText curPassword, newPassword, newPasswordCheck;
    Button saveButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.profile_password, container,false);

        curPassword = rootView.findViewById(R.id.profile_current_password);
        newPassword = rootView.findViewById(R.id.profile_new_password);
        newPasswordCheck = rootView.findViewById(R.id.profile_new_password_check);

        saveButton = rootView.findViewById(R.id.profile_password_save_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curPassword_value = curPassword.getText().toString();
                String newPassword_value = newPassword.getText().toString();
                String newPasswordCheck_value = newPasswordCheck.getText().toString();

                if(curPassword_value.equals("")){
                    sUtil.CreateNewSimpleAlertDialog(getContext(),"","Please enter current password");
                    return ;
                }

                if(newPassword_value.equals("")){
                    sUtil.CreateNewSimpleAlertDialog(getContext(),"","Please enter new password");
                    return ;
                }

                if(newPasswordCheck_value.equals("")){
                    sUtil.CreateNewSimpleAlertDialog(getContext(),"","Please enter new password check");
                    return ;
                }

                if(!newPassword_value.equals(newPasswordCheck_value)){
                    sUtil.CreateNewSimpleAlertDialog(getContext(),"New passwords are different","Please check again");
                    return ;
                }

                sRequest.requestPatchPassword(curPassword_value, newPassword_value,getContext());
            }
        });

        return  rootView;
    }
}
