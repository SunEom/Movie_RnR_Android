package io.github.suneom.MovieRnR.fragment;

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
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import io.github.suneom.MovieRnR.R;
import io.github.suneom.MovieRnR.activity.MainActivity;
import io.github.suneom.MovieRnR.application.MyApplication;
import io.github.suneom.MovieRnR.custom_class.Login.LoginUserInfo;
import io.github.suneom.MovieRnR.custom_class.Profile.ProfileData;
import io.github.suneom.MovieRnR.fragment.Profile.BasicInfoFragment;
import io.github.suneom.MovieRnR.util.sRequest;

public class ProfileFragment extends Fragment {
    View rootView;

    ImageView profile_background_imageView;
    TextView profile_title_nickname;

    Spinner spinner;
    ArrayAdapter spinnerAdapter;
    ScrollView profile_scrollView;
    ProgressBar profile_progressBar;

    BasicInfoFragment basicInfoFragment = new BasicInfoFragment();

    public int user_id;

    ProfileData data;

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
            //로그인 되어있고 요청한 정보가 자신의 정보인 경우
            setMyInfo();
            setProfileTitleNickname(this.data.nickname);
            initFragmentContainer();
            onLoadingFinish();
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

    public void initViewitems(){
        spinner = rootView.findViewById(R.id.profile_spinner);
        String[] spinnerItems = getResources().getStringArray(R.array.my_array);
        spinnerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, spinnerItems);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Spinner",String.valueOf(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        profile_background_imageView = rootView.findViewById(R.id.profile_background_image);

        profile_scrollView = rootView.findViewById(R.id.profile_scrollview);
        profile_progressBar = rootView.findViewById(R.id.profile_progressbar);

        profile_title_nickname = rootView.findViewById(R.id.profile_title_nickname);
    }

    public void setProfileTitleNickname(String nickname){
        profile_title_nickname.setText(nickname);
    }

    public void setBackgroundImageSize(){
        int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        int height = width/16*9;

        profile_background_imageView.getLayoutParams().height = height;
        profile_background_imageView.requestLayout();
    }

    public void onLoadingFinish(){
        profile_progressBar.setVisibility(View.GONE);
        profile_scrollView.setVisibility(View.VISIBLE);
    }


    public void setMyInfo(){
        LoginUserInfo myData = MyApplication.my_info;
        data = new ProfileData(myData.biography,myData.facebook,myData.gender,myData.id,myData.instagram, myData.nickname, myData.twitter);
        basicInfoFragment.data = data;
    }

    public void setInfo(ProfileData info){
        data = info;
        basicInfoFragment.data = info;
    }

    public void initFragmentContainer(){
        ((MainActivity)getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.profile_fragment_container, basicInfoFragment).commit();
    }
}
