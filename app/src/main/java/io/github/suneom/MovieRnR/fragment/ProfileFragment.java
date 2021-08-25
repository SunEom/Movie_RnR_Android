package io.github.suneom.MovieRnR.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import io.github.suneom.MovieRnR.R;
import io.github.suneom.MovieRnR.application.MyApplication;

public class ProfileFragment extends Fragment {
    View rootView;

    ImageView profile_background_imageView, instagram, facebook, twitter;

    TextView profile_title_nickname, id, nickname, gender, biography;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        initViewitems();
        setBackgroundImageSize();

        if(MyApplication.my_info != null){
            setViewItems();
            setSnsOnClickListener();
        }


        return rootView;
    }

    public void setSnsOnClickListener(){

        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = MyApplication.my_info.instagram;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = MyApplication.my_info.facebook;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = MyApplication.my_info.twitter;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }


    public void initViewitems(){
        profile_background_imageView = rootView.findViewById(R.id.profile_background_image);
        profile_title_nickname = rootView.findViewById(R.id.profile_title_nickname);
        id = rootView.findViewById(R.id.profile_id);
        nickname = rootView.findViewById(R.id.profile_nickname);
        gender = rootView.findViewById(R.id.profile_gender);
        biography = rootView.findViewById(R.id.profile_biography);
        instagram = rootView.findViewById(R.id.my_instagram);
        facebook = rootView.findViewById(R.id.my_facebook);
        twitter = rootView.findViewById(R.id.my_twitter);
    }

    public void setViewItems(){
        profile_title_nickname.setText(MyApplication.my_info.getNickname());
        id.setText(MyApplication.my_info.getUser_id());
        nickname.setText(MyApplication.my_info.getNickname());
        gender.setText(MyApplication.my_info.getGender());
        biography.setText(MyApplication.my_info.getBiography());
    }

    public void setBackgroundImageSize(){
        int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        int height = width/16*9;

        profile_background_imageView.getLayoutParams().height = height;
        profile_background_imageView.requestLayout();
    }
}
