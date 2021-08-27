package io.github.suneom.MovieRnR.application;

import android.app.Activity;
import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import androidx.fragment.app.FragmentManager;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import io.github.suneom.MovieRnR.activity.MainActivity;
import io.github.suneom.MovieRnR.custom_class.Login.LoginUserInfo;
import io.github.suneom.MovieRnR.fragment.HomeFragment;

public class MyApplication extends Application {

    public final static String TAG = "MyApplication";

    public final static String SERVER_URL = "https://movie-rnr-server.herokuapp.com/";

    public static RequestQueue requestQueue;

    public static LoginUserInfo my_info;

    public static SQLiteDatabase database;

    public final static HomeFragment homeFragment = new HomeFragment();

    @Override
    public void onCreate() {
        super.onCreate();

        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

    }

    public static void setMyInfo(LoginUserInfo info){
        my_info = info;
    }

}
