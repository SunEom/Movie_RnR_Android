package io.github.suneom.MovieRnR.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

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
import io.github.suneom.MovieRnR.activity.MainActivity;
import io.github.suneom.MovieRnR.application.MyApplication;
import io.github.suneom.MovieRnR.custom_class.Comment.CommentResponse;
import io.github.suneom.MovieRnR.custom_class.Detail.DeleteResponse;
import io.github.suneom.MovieRnR.custom_class.Detail.DetailResponse;
import io.github.suneom.MovieRnR.custom_class.Join.DuplicationCheckResponse;
import io.github.suneom.MovieRnR.custom_class.Login.LoginResponse;
import io.github.suneom.MovieRnR.custom_class.Movie.Movie;
import io.github.suneom.MovieRnR.custom_class.Movie.MovieData;
import io.github.suneom.MovieRnR.custom_class.Movie.PostReqResult;
import io.github.suneom.MovieRnR.custom_class.Profile.ProfileData;
import io.github.suneom.MovieRnR.custom_class.Profile.ProfileResponse;
import io.github.suneom.MovieRnR.fragment.DetailFragment;
import io.github.suneom.MovieRnR.fragment.JoinFragment;
import io.github.suneom.MovieRnR.fragment.ProfileFragment;
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

    public static void requestRecentPostings(MovieAdapter adapter, Activity activity){
        StringRequest request = new StringRequest(Request.Method.GET, MyApplication.SERVER_URL+"post"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                PostReqResult result = gson.fromJson(response, PostReqResult.class);

                if(result.code == 200){
                    for(int i=0; i<result.data.size(); i++){
                        MovieData data = result.data.get(i);
                        adapter.addItem(new Movie(data.id, data.title, data.genres, data.overview, data.rates, data.commentCount));
                    }
                    adapter.notifyDataSetChanged();

                    activity.findViewById(R.id.home_progressBar).setVisibility(View.GONE);
                    activity.findViewById(R.id.recyclerView_home).setVisibility(View.VISIBLE);
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

    public static void requestSearchPostings(MovieAdapter adapter , final String keyword, Activity activity){
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
                        adapter.addItem(new Movie(data.id, data.title, data.genres, data.overview, data.rates, data.commentCount));
                    }
                    adapter.notifyDataSetChanged();
                }


                activity.findViewById(R.id.home_progressBar).setVisibility(View.GONE);
                activity.findViewById(R.id.recyclerView_home).setVisibility(View.VISIBLE);

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

    public static void requestNewPosting(String title, String genres, String rates, String overview, FragmentManager manager){
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

                    String result = response.body().string();

                    Gson gson = new Gson();
                    PostReqResult info = gson.fromJson(result, PostReqResult.class);

                    DetailFragment detailFragment = new DetailFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", info.data.get(0).id);
                    detailFragment.postingOwnerId = info.data.get(0).user_id;
                    detailFragment.setArguments(bundle);

                    manager.beginTransaction().replace(R.id.fragment_container, detailFragment).commit();
                } catch(Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public static void requestPostingDetail(int posting_id, Activity activity, DetailFragment fragment){
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

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            fragment.postingOwnerId = info.data.user.id;
                            fragment.setInfo(info.getData().getMovie(), info.getData().getUser());
                            fragment.setVisiblityAfterLoad();
                        }
                    });

                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void requestDeletePosting(int movieId, DetailFragment fragment){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    builder.cookieJar(myCookieJar);
                    OkHttpClient client = builder.build();

                    String url = MyApplication.SERVER_URL + "post/"+String.valueOf(movieId);

                    okhttp3.Request request = new okhttp3.Request.Builder()
                            .url(url)
                            .delete()
                            .build();

                    okhttp3.Response response = client.newCall(request).execute();

                    String result = response.body().string();

                    Gson gson = new Gson();
                    DeleteResponse info = gson.fromJson(result, DeleteResponse.class);

                    if(info.getCode()==200){
                        fragment.getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                sUtil.CreateNewSimpleAlertDialog(fragment.getContext(),"","Deleted Successfully!");
                                fragment.getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, MyApplication.homeFragment).commit();
                            }
                        });
                    }

                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void requestPatchPosting(String title, String genres, String rates, String overview, int movieId, FragmentManager manager){
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    builder.cookieJar(myCookieJar);
                    OkHttpClient client = builder.build();

                    RequestBody formBody = new FormBody.Builder()
                            .add("id", String.valueOf(movieId))
                            .add("title", title)
                            .add("genres", genres)
                            .add("rates", rates)
                            .add("overview", overview)
                            .build();

                    String url = MyApplication.SERVER_URL+"post/update";

                    okhttp3.Request request = new okhttp3.Request.Builder()
                            .url(url)
                            .patch(formBody)
                            .build();

                    okhttp3.Response response = client.newCall(request).execute();

                    String result = response.body().string();

                    Gson gson = new Gson();
                    PostReqResult info = gson.fromJson(result, PostReqResult.class);

                    DetailFragment detailFragment = new DetailFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", info.data.get(0).id);
                    detailFragment.postingOwnerId = info.data.get(0).user_id;
                    detailFragment.setArguments(bundle);

                    manager.beginTransaction().replace(R.id.fragment_container, detailFragment).commit();
                } catch(Exception e) {
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

    public static void requestNewComment(String contents, String posting_id, CommentAdapter adapter, Activity activity){
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

                    requestCommentList(adapter,Integer.parseInt(posting_id),activity);

                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //Authentication 관련 Method

    public static void requestLoginPost(String id, String password, Activity activity){

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

                    MyApplication.setMyInfo(info.data);

                    if(info.data != null){
                        Intent intent = new Intent(activity.getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.getApplicationContext().startActivity(intent);
                    } else {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                sUtil.CreateNewSimpleAlertDialog(activity.getApplicationContext(), "","Please check ID or Password");
                                Toast.makeText(activity.getApplicationContext(), "Please check ID or Password",Toast.LENGTH_LONG).show();
                            }
                        });
                    }


                } catch(Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public static void requestLoginGet(Activity activity){
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

                    MyApplication.setMyInfo(info.data);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ((MainActivity)activity).setMenuAccessControl();
                        }
                    });

                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public static void requestLogout(Activity activity){
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

                    MyApplication.setMyInfo(null);

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ((MainActivity)activity).setMenuAccessControl();
                        }
                    });

                } catch(Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    //Profile 관련 Method

    public static void requestProfileData(int id, ProfileFragment fragment){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    builder.cookieJar(myCookieJar);
                    OkHttpClient client = builder.build();

                    String url = MyApplication.SERVER_URL+"user/"+String.valueOf(id);

                    okhttp3.Request request = new okhttp3.Request.Builder()
                            .url(url)
                            .build();

                    okhttp3.Response response = client.newCall(request).execute();

                    String result = response.body().string();

                    Gson gson = new Gson();
                    ProfileResponse info =  gson.fromJson(result, ProfileResponse.class);

                    ProfileData data = info.data.get(0);


                    fragment.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            fragment.setInfo(data);
                            fragment.setOtherSnsOnClickListener(data);

                            fragment.onLoadingFinish();
                        }
                    });

                } catch(Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    //Join 관련 Method

    public static void requestConfirmIdDuplication(String id, JoinFragment fragment){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    builder.cookieJar(myCookieJar);
                    OkHttpClient client = builder.build();

                    String url = MyApplication.SERVER_URL + "join/id";

                    FormBody formBody = new FormBody.Builder()
                            .add("id",id)
                            .build();

                    okhttp3.Request request = new okhttp3.Request.Builder()
                            .url(url)
                            .post(formBody)
                            .build();

                    okhttp3.Response response = client.newCall(request).execute();

                    String result = response.body().string();

                    Gson gson = new Gson();
                    DuplicationCheckResponse info = gson.fromJson(result, DuplicationCheckResponse.class);

                    if(info.already){
                        fragment.getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                sUtil.CreateNewSimpleAlertDialog(fragment.getContext(),"This ID is already taken",  "Please enter another ID 😅");
                            }
                        });
                        return;
                    } else {
                        fragment.getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                sUtil.CreateNewSimpleAlertDialog(fragment.getContext(),"",  "This ID is available 😃");
                                fragment.isIdChecked = true;
                            }
                        });
                        return;
                    }

                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void requestConfirmNickDuplication(String nickname, JoinFragment fragment){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    builder.cookieJar(myCookieJar);
                    OkHttpClient client = builder.build();

                    String url = MyApplication.SERVER_URL + "join/nick";

                    FormBody formBody = new FormBody.Builder()
                            .add("nickname",nickname)
                            .build();

                    okhttp3.Request request = new okhttp3.Request.Builder()
                            .url(url)
                            .post(formBody)
                            .build();

                    okhttp3.Response response = client.newCall(request).execute();

                    String result = response.body().string();

                    Gson gson = new Gson();
                    DuplicationCheckResponse info = gson.fromJson(result, DuplicationCheckResponse.class);

                    if(info.already){
                        fragment.getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                sUtil.CreateNewSimpleAlertDialog(fragment.getContext(),"This Nickname is already taken",  "Please enter another nickname 😅");
                            }
                        });
                        return;
                    } else {
                        fragment.getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                sUtil.CreateNewSimpleAlertDialog(fragment.getContext(),"",  "This nickname is available 😃");
                                fragment.isNickChecked = true;
                            }
                        });
                        return;
                    }

                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void requestJoinUser(String id, String password, String nickname, String gender, JoinFragment fragment){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    builder.cookieJar(myCookieJar);
                    OkHttpClient client = builder.build();

                    String url = MyApplication.SERVER_URL + "join/";

                    FormBody formBody = new FormBody.Builder()
                            .add("id",id)
                            .add("password",password)
                            .add("nickname",nickname)
                            .add("gender",gender)
                            .build();

                    okhttp3.Request request = new okhttp3.Request.Builder()
                            .url(url)
                            .post(formBody)
                            .build();

                    okhttp3.Response response = client.newCall(request).execute();

                    String result = response.body().string();

                    Gson gson = new Gson();
                    LoginResponse info = gson.fromJson(result, LoginResponse.class);

                    if(info.getData() != null){
                        MyApplication.setMyInfo(info.getData());
                        fragment.getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                sUtil.CreateNewSimpleAlertDialog(fragment.getContext(), "","Successfully joined!");
                            }
                        });
                        fragment.getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, MyApplication.homeFragment).commit();
                    }

                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
