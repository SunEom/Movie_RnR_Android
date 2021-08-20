package io.github.suneom.MovieRnR.application;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import io.github.suneom.MovieRnR.fragment.HomeFragment;
import io.github.suneom.MovieRnR.recycler_view.Adapter.MovieAdapter;

public class MyApplication extends Application {

    public static final MovieAdapter movieAdapter = new MovieAdapter();

    public final static String TAG = "MyApplication";

    public final static String SERVER_URL = "https://movie-rnr-server.herokuapp.com/";

    public static RequestQueue requestQueue;

    public final static HomeFragment homeFragment = new HomeFragment();
    public final static MovieAdapter.SearchFragment searchFragment = new MovieAdapter.SearchFragment();

    @Override
    public void onCreate() {
        super.onCreate();

        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
    }


}
