package io.github.suneom.MovieRnR;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

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
}
