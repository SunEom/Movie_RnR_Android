package io.github.suneom.MovieRnR;

import android.app.Application;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

public class MyApplication extends Application {

    public static final MovieAdapter movieAdapter = new MovieAdapter();

    public final static String TAG = "MyApplication";

    public final static String SERVER_URL = "https://movie-rnr-server.herokuapp.com/";

    public static RequestQueue requestQueue;

    public final static HomeFragment homeFragment = new HomeFragment();
    public final static SearchFragment searchFragment = new SearchFragment();

    @Override
    public void onCreate() {
        super.onCreate();

        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
    }


}
