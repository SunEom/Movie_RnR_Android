package io.github.suneom.MovieRnR.fragment.Profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import io.github.suneom.MovieRnR.R;
import io.github.suneom.MovieRnR.application.MyApplication;
import io.github.suneom.MovieRnR.custom_class.Login.LoginUserInfo;
import io.github.suneom.MovieRnR.custom_class.Profile.ProfileData;
import io.github.suneom.MovieRnR.fragment.ProfileFragment;
import io.github.suneom.MovieRnR.util.sRequest;

public class BasicInfoFragment extends Fragment {
    View rootView;

    TextView id, nickname, gender, biography, id_title;
    ImageView instagram, facebook, twitter;

    public ProfileData data;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.profile_basic, container, false);

        initViewitems();
        setSnsOnClickListener();
        setInfo(data);

        return rootView;
    }

    public void initViewitems(){
        id = rootView.findViewById(R.id.profile_id);
        nickname = rootView.findViewById(R.id.profile_nickname);
        gender = rootView.findViewById(R.id.profile_gender);
        biography = rootView.findViewById(R.id.profile_biography);
        id_title = rootView.findViewById(R.id.profile_id_title);
        instagram = rootView.findViewById(R.id.my_instagram);
        facebook = rootView.findViewById(R.id.my_facebook);
        twitter = rootView.findViewById(R.id.my_twitter);
    }

    public void setSnsOnClickListener(){

        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = data.instagram;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = data.facebook;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = data.twitter;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }

    public void setInfo(ProfileData info){
        nickname.setText(info.nickname);
        gender.setText(info.gender);
        biography.setText(info.biography);
        id.setVisibility(View.GONE);
        id_title.setVisibility(View.GONE);
    }

}
