package io.github.suneom.MovieRnR.application;

import android.app.Application;
import android.content.Context;
import android.net.wifi.hotspot2.pps.Credential;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.Volley;

import io.github.suneom.MovieRnR.fragment.HomeFragment;
import io.github.suneom.MovieRnR.recycler_view.Adapter.MovieAdapter;
import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;

public class MyApplication extends Application {

    public static final MovieAdapter movieAdapter = new MovieAdapter();

    public final static String TAG = "MyApplication";

    public final static String SERVER_URL = "https://movie-rnr-server.herokuapp.com/";

    public static RequestQueue requestQueue;
//    public static RequestQueue sRequestQueue;


    public final static HomeFragment homeFragment = new HomeFragment();
    public final static MovieAdapter.SearchFragment searchFragment = new MovieAdapter.SearchFragment();

    public static Authenticator getAuthenticator(final String userId, final String password) {

        return (route, response) -> {
            String credential = Credentials.basic(userId, password);

            return response.request().newBuilder().header("Authorization", credential).build();
        };
    }



    @Override
    public void onCreate() {
        super.onCreate();

        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

//        initRequestQueue("","",getApplicationContext());

    }
//
//
//    public static void initRequestQueue(String id, String password, Context context){
//        OkHttpClient client = new OkHttpClient.Builder()
//                .authenticator(MyApplication.getAuthenticator(id, password)).build();
//
//        sRequestQueue = Volley.newRequestQueue(context, (HttpStack) client);
//    }

}
