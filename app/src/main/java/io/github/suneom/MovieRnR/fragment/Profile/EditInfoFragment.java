package io.github.suneom.MovieRnR.fragment.Profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import io.github.suneom.MovieRnR.R;
import io.github.suneom.MovieRnR.custom_class.Profile.ProfileData;

public class EditInfoFragment extends Fragment {
    View rootView;

    EditText nickname, biography, instagram, facebook, twitter;

    RadioGroup genderRadio;

    public ProfileData data;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.profile_edit,container, false);

        initView();
        setInfo();

        return rootView;
    }

    void initView(){
        nickname = rootView.findViewById(R.id.profile_nickname_edit);
        genderRadio = rootView.findViewById(R.id.edit_gender_group);
        biography = rootView.findViewById(R.id.profile_biography_edit);
        instagram = rootView.findViewById(R.id.my_instagram_edit);
        facebook = rootView.findViewById(R.id.my_facebook_edit);
        twitter = rootView.findViewById(R.id.my_twitter_edit);
    }

    public void setInfo() {
        nickname.setText(data.nickname);
        if(data.gender.equals("Man")){
            genderRadio.check(R.id.profile_edit_gender_man);
        } else if(data.gender.equals("Woman")){
            genderRadio.check(R.id.profile_edit_gender_woman);
        }
        biography.setText(data.biography);
        instagram.setText(data.instagram);
        facebook.setText(data.facebook);
        twitter.setText(data.twitter);
    }
}
