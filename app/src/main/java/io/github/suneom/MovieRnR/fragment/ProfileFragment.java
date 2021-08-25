package io.github.suneom.MovieRnR.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import io.github.suneom.MovieRnR.custom_class.Login.LoginUserInfo;
import io.github.suneom.MovieRnR.custom_class.Profile.ProfileData;
import io.github.suneom.MovieRnR.util.sRequest;

public class ProfileFragment extends Fragment {
    View rootView;

    ImageView profile_background_imageView, instagram, facebook, twitter;

    TextView profile_title_nickname, id, nickname, gender, biography, id_title;

    int user_id;

    public ProfileData data;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        initViewitems();
        setBackgroundImageSize();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            user_id = bundle.getInt("id", -1);
        }


        if(MyApplication.my_info != null && MyApplication.my_info.id == user_id){
            //로그인이 되어있는데 요청한 정보가 자신의 정보인 경우
            setMyInfo(MyApplication.my_info);
            setSnsOnClickListener();
        }
        else if(MyApplication.my_info != null && MyApplication.my_info.id != user_id){
            //로그인이 되어있는데 요청한 정보가 자신의 정보가 아닌 경우
            sRequest.requestProfileData(user_id, this);
        } else if(MyApplication.my_info == null){
            //로그인이 되어있지 않은 경우
            sRequest.requestProfileData(user_id, this);
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

    public void setOtherSnsOnClickListener(ProfileData data){

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


    public void initViewitems(){
        profile_background_imageView = rootView.findViewById(R.id.profile_background_image);
        profile_title_nickname = rootView.findViewById(R.id.profile_title_nickname);
        id = rootView.findViewById(R.id.profile_id);
        nickname = rootView.findViewById(R.id.profile_nickname);
        gender = rootView.findViewById(R.id.profile_gender);
        biography = rootView.findViewById(R.id.profile_biography);
        id_title = rootView.findViewById(R.id.profile_id_title);
        instagram = rootView.findViewById(R.id.my_instagram);
        facebook = rootView.findViewById(R.id.my_facebook);
        twitter = rootView.findViewById(R.id.my_twitter);
    }

    public void setMyInfo(LoginUserInfo info){
        profile_title_nickname.setText(info.getNickname());
        id.setText(info.getUser_id());
        nickname.setText(info.getNickname());
        gender.setText(info.getGender());
        biography.setText(info.getBiography());
    }

    public void setInfo(ProfileData info){
        profile_title_nickname.setText(info.nickname);
        nickname.setText(info.nickname);
        gender.setText(info.gender);
        biography.setText(info.biography);
        id.setVisibility(View.GONE);
        id_title.setVisibility(View.GONE);
    }

    public void setBackgroundImageSize(){
        int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        int height = width/16*9;

        profile_background_imageView.getLayoutParams().height = height;
        profile_background_imageView.requestLayout();
    }
}
