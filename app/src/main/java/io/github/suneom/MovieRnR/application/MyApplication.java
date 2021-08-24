package io.github.suneom.MovieRnR.application;

import android.app.Application;
import android.content.Context;
import android.net.wifi.hotspot2.pps.Credential;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.Volley;

import io.github.suneom.MovieRnR.custom_class.LoginUserInfo;
import io.github.suneom.MovieRnR.fragment.HomeFragment;
import io.github.suneom.MovieRnR.recycler_view.Adapter.MovieAdapter;
import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;

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
