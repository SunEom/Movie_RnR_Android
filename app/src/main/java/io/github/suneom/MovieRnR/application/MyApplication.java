package io.github.suneom.MovieRnR.application;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import io.github.suneom.MovieRnR.custom_class.Login.LoginUserInfo;
import io.github.suneom.MovieRnR.fragment.HomeFragment;

public class MyApplication extends Application {

    public final static String TAG = "MyApplication";

    public final static String SERVER_URL = "https://movie-rnr-server.herokuapp.com/";

    public static RequestQueue requestQueue;

    public static LoginUserInfo my_info = null;

    public final static HomeFragment homeFragment = new HomeFragment();

    @Override
    public void onCreate() {
        super.onCreate();

        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

    }

}
