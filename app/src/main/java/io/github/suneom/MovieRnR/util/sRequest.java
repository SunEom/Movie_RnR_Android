package io.github.suneom.MovieRnR.util;

import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.suneom.MovieRnR.application.MyApplication;
import io.github.suneom.MovieRnR.custom_class.HttpResponse;
import io.github.suneom.MovieRnR.custom_class.LoginUserInfo;
import io.github.suneom.MovieRnR.custom_class.Movie;
import io.github.suneom.MovieRnR.custom_class.MovieData;
import io.github.suneom.MovieRnR.custom_class.PostReqResult;
import io.github.suneom.MovieRnR.recycler_view.Adapter.MovieAdapter;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Credentials;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class sRequest {
    private final static String TAG = "Request";
    private final static MyCookieJar myCookieJar = new MyCookieJar();

    static class MyCookieJar implements CookieJar {

        private List<Cookie> cookies;

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            this.cookies =  cookies;
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            if (cookies != null)
                return cookies;
            return new ArrayList<Cookie>();

        }
    }



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

    public static void requestLoginPost(String id, String password){
//        StringRequest request = new StringRequest(Request.Method.POST, MyApplication.SERVER_URL + "auth/login",
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.d("Login POST", response);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d("Login POST ERROR", error.getMessage());
//                    }
//                }){
//            @Nullable
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("id", id);
//                params.put("password",password);
//                return params;
//            }
//        };
//        request.setShouldCache(false);
//        MyApplication.requestQueue.add(request);




        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    builder.cookieJar(myCookieJar);
                    OkHttpClient client = builder.build();

                    RequestBody formBody = new FormBody.Builder()
                            .add("id", id)
                            .add("password", password)
                            .build();

                    String url = MyApplication.SERVER_URL+"auth/login";

                    okhttp3.Request request = new okhttp3.Request.Builder()
                            .addHeader("Authorization", Credentials.basic(id, password))
                            .url(url)
                            .post(formBody)
                            .build();

                    okhttp3.Response response = client.newCall(request).execute();


                    Log.d("Login POST","request : " + request.toString());
                    Log.d("Login POST","Response : " + response.body().string());
                    String result = response.body().string();

                    Gson gson = new Gson();
                    HttpResponse info = gson.fromJson(result, HttpResponse.class);


                    Log.d("Login POST", info.data.nickname);

                    MyApplication.my_info = info.data;

                } catch(Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public static void requestLoginGet(){
//        StringRequest request = new StringRequest(Request.Method.GET, MyApplication.SERVER_URL + "auth/login",
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.d("Login GET", response);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        VolleyLog.d("Login GET ERROR", error.getMessage());
//                    }
//                }){
//            @Nullable
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                return params;
//            }
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> headers = new HashMap<String, String>();
//                headers.put("User-Agent","Mozilla/5.0");
//                return headers;
//            }
//        };
//        request.setShouldCache(false);
//        MyApplication.requestQueue.add(request);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    builder.cookieJar(myCookieJar);
                    OkHttpClient client = builder.build();

                    String url = MyApplication.SERVER_URL + "auth/login";

                    okhttp3.Request request = new okhttp3.Request.Builder()
                            .url(url)
                            .build();

                    okhttp3.Response response = client.newCall(request)
                            .execute();

                    String result = response.body().string();

                    Gson gson = new Gson();

                    HttpResponse info = gson.fromJson(result, HttpResponse.class);

                    Log.d("Login GET", info.data.nickname);

                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public static void requestNewPosting(String title, String genres, String rates, String overview){
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    builder.cookieJar(myCookieJar);
                    OkHttpClient client = builder.build();

                    RequestBody formBody = new FormBody.Builder()
                            .add("title", title)
                            .add("genres", genres)
                            .add("rates", rates)
                            .add("overview", overview)
                            .build();

                    String url = MyApplication.SERVER_URL+"post";

                    okhttp3.Request request = new okhttp3.Request.Builder()
                            .url(url)
                            .post(formBody)
                            .build();

                    okhttp3.Response response = client.newCall(request).execute();


                    Log.d("Posting POST","request : " + request.toString());
                    Log.d("Posting POST","Response : " + response.body().string());

                } catch(Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }


    public static void requestLogout(){
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    builder.cookieJar(myCookieJar);
                    OkHttpClient client = builder.build();

                    String url = MyApplication.SERVER_URL+"auth/logout";

                    okhttp3.Request request = new okhttp3.Request.Builder()
                            .url(url)
                            .build();

                    okhttp3.Response response = client.newCall(request).execute();


                    Log.d("Logout","request : " + request.toString());
                    Log.d("Logout","Response : " + response.body().string());

                } catch(Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
}
