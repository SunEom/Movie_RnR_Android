package io.github.suneom.MovieRnR.util;

import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import io.github.suneom.MovieRnR.application.MyApplication;
import io.github.suneom.MovieRnR.custom_class.Movie;
import io.github.suneom.MovieRnR.custom_class.MovieData;
import io.github.suneom.MovieRnR.custom_class.PostReqResult;
import io.github.suneom.MovieRnR.recycler_view.Adapter.MovieAdapter;

public class sRequest {
    private final static String TAG = "Request";

    public static void requestRecentPostings(MovieAdapter adapter){
        StringRequest request = new StringRequest(Request.Method.GET, MyApplication.SERVER_URL+"post"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG,"Result : "+ response);

                Gson gson = new Gson();
                PostReqResult result = gson.fromJson(response, PostReqResult.class);
                Log.d(TAG,String.valueOf(result.data.size()));

                if(result.code == 200){
                    for(int i=0; i<result.data.size(); i++){
                        MovieData data = result.data.get(i);
                        adapter.addItem(new Movie(data.title, data.genres, data.overview, data.rates, data.commentCount));
                    }
                    adapter.notifyDataSetChanged();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,"ERROR!! : " + error.getMessage());
            }
        });

        request.setShouldCache(false);
        MyApplication.requestQueue.add(request);
    }

    public static void requestSearchPostings(MovieAdapter adapter , final String keyword){
        StringRequest request = new StringRequest(Request.Method.POST, MyApplication.SERVER_URL+"search"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG,"Result : "+ response);

                Gson gson = new Gson();
                PostReqResult result = gson.fromJson(response, PostReqResult.class);
                Log.d(TAG,String.valueOf(result.data.size()));

                if(result.code == 200){
                    for(int i=0; i<result.data.size(); i++){
                        MovieData data = result.data.get(i);
                        adapter.addItem(new Movie(data.title, data.genres, data.overview, data.rates, data.commentCount));
                    }
                    adapter.notifyDataSetChanged();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,"ERROR!! : " + error.getMessage());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("keyword", keyword);

                return params;
            }
        };

        request.setShouldCache(false);
        MyApplication.requestQueue.add(request);
    }

    public static void requestLogin(String id, String password){
        StringRequest request = new StringRequest(Request.Method.POST, MyApplication.SERVER_URL + "auth/login",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Login", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Login", error.getMessage());
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id);
                params.put("password",password);
                return params;
            }
        };
        request.setShouldCache(false);
        MyApplication.requestQueue.add(request);
    }
}
