package io.github.suneom.MovieRnR.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.suneom.MovieRnR.R;
import io.github.suneom.MovieRnR.activity.DetailActivity;
import io.github.suneom.MovieRnR.activity.MainActivity;
import io.github.suneom.MovieRnR.application.MyApplication;
import io.github.suneom.MovieRnR.custom_class.Comment.CommentResponse;
import io.github.suneom.MovieRnR.custom_class.Detail.DetailResponse;
import io.github.suneom.MovieRnR.custom_class.Login.LoginResponse;
import io.github.suneom.MovieRnR.custom_class.Movie.Movie;
import io.github.suneom.MovieRnR.custom_class.Movie.MovieData;
import io.github.suneom.MovieRnR.custom_class.Movie.PostReqResult;
import io.github.suneom.MovieRnR.recycler_view.Adapter.CommentAdapter;
import io.github.suneom.MovieRnR.recycler_view.Adapter.MovieAdapter;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Credentials;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
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

    //Posting 관련 Method

    public static void requestRecentPostings(MovieAdapter adapter){
        StringRequest request = new StringRequest(Request.Method.GET, MyApplication.SERVER_URL+"post"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                PostReqResult result = gson.fromJson(response, PostReqResult.class);

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

    public static void requestNewPosting(String title, String genres, String rates, String overview, Context context){
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

                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                } catch(Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public static void requestPostingDetail(int posting_id, Activity activity){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    builder.cookieJar(myCookieJar);
                    OkHttpClient client = builder.build();

                    String url = MyApplication.SERVER_URL+"post"+"/"+posting_id;

                    okhttp3.Request request = new okhttp3.Request.Builder()
                            .url(url)
                            .build();

                    okhttp3.Response response = client.newCall(request).execute();

                    String result = response.body().string();

                    Gson gson = new Gson();
                    DetailResponse info = gson.fromJson(result, DetailResponse.class);

                    Log.d("POSTING DETAIL", result);

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ((DetailActivity) activity).setInfo(info.getData().getMovie(), info.getData().getUser());
                        }
                    });

                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //Comment 관련 Method

    public static void requestCommentList(CommentAdapter adapter, int posting_id, Activity activity){

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    builder.cookieJar(myCookieJar);
                    OkHttpClient client = builder.build();

                    String url = MyApplication.SERVER_URL+"comment"+"/"+posting_id;

                    okhttp3.Request request = new okhttp3.Request.Builder()
                            .url(url)
                            .build();

                    okhttp3.Response response = client.newCall(request).execute();

                    String result = response.body().string();

                    Gson gson = new Gson();
                    CommentResponse info = gson.fromJson(result, CommentResponse.class);

                    // MainThread에서만 View를 조작할 수 있기때문에 다음과 같이 activity를 가져와서 MainThread에서 recycler view를 조작한다.
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.setItems(info.getData());

                            adapter.notifyDataSetChanged();

                            // Comment가 1개라도 존재한다면 Comment가 없다는 TextView를 숨긴다.
                            if(adapter.getItemCount() != 0){
                                TextView noCommentAlert = activity.findViewById(R.id.comment_empty_alert);
                                noCommentAlert.setVisibility(View.GONE);
                            }
                        }
                    });

                } catch(Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    public static void requestNewComment(String contents, String posting_id){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient.Builder builder= new OkHttpClient.Builder();
                    builder.cookieJar(myCookieJar);
                    OkHttpClient client = builder.build();

                    String url = MyApplication.SERVER_URL + "comment";

                    FormBody formBody = new FormBody.Builder()
                            .add("contents", contents)
                            .add("movie_id", posting_id)
                            .build();

                    okhttp3.Request request = new okhttp3.Request.Builder()
                            .url(url)
                            .post(formBody)
                            .build();

                    okhttp3.Response response = client.newCall(request).execute();

                    String result = response.body().string();

                    Log.d("New Comment",result);

                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //Authentication 관련 Method

    public static void requestLoginPost(String id, String password, Context context){

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


                    String result = response.body().string();

                    Gson gson = new Gson();
                    LoginResponse info = gson.fromJson(result, LoginResponse.class);


                    MyApplication.my_info = info.data;

                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);

                } catch(Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public static void requestLoginGet(Context context){
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

                    LoginResponse info = gson.fromJson(result, LoginResponse.class);
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
                } catch(Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
}
