package io.github.suneom.MovieRnR.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import io.github.suneom.MovieRnR.application.MyApplication;
import io.github.suneom.MovieRnR.R;
import io.github.suneom.MovieRnR.fragment.DetailFragment;
import io.github.suneom.MovieRnR.fragment.EditFragment;
import io.github.suneom.MovieRnR.fragment.HomeFragment;
import io.github.suneom.MovieRnR.fragment.JoinFragment;
import io.github.suneom.MovieRnR.fragment.LogInFragment;
import io.github.suneom.MovieRnR.fragment.PostingFragment;
import io.github.suneom.MovieRnR.fragment.ProfileFragment;
import io.github.suneom.MovieRnR.fragment.SearchFragment;
import io.github.suneom.MovieRnR.recycler_view.Adapter.MovieAdapter;
import io.github.suneom.MovieRnR.util.sRequest;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawer;
    Toolbar toolbar;
    Window window;

    NavigationView navigationView;

    HomeFragment homeFragment;
    SearchFragment searchFragment;
    PostingFragment postingFragment;
    LogInFragment logInFragment;
    ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //변수 초기화
        toolbar = findViewById(R.id.toolbar);
        window = getWindow();
        homeFragment = MyApplication.homeFragment;
        drawer = findViewById(R.id.drawer_layout);

        postingFragment = new PostingFragment();
        logInFragment = new LogInFragment();
        profileFragment = new ProfileFragment();


        settingBasicUI();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).commit();

    }

    @Override
    public void onBackPressed() {
        Fragment curFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
            return;
        }else if(curFragment instanceof LogInFragment){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).commit();
        }else if(curFragment instanceof PostingFragment){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).commit();
        } else if(curFragment instanceof DetailFragment){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).commit();
        } else if(curFragment instanceof SearchFragment){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).commit();
        } else if(curFragment instanceof JoinFragment){
            LogInFragment logInFragment = new LogInFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, logInFragment).commit();
        } else if(curFragment instanceof EditFragment){
            DetailFragment detailFragment = new DetailFragment();
            detailFragment.movieId = ((EditFragment) curFragment).movieId;
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, detailFragment).commit();
        } else {
            super.onBackPressed();
        }
    }

    public void setMenuAccessControl(){
        Menu menu = navigationView.getMenu();
        if(MyApplication.my_info == null){
            //로그인이 되어있지 않은 경우
            Log.d("Menu","Login");
            menu.getItem(0).setVisible(true);
            menu.getItem(1).setVisible(false);
            menu.getItem(2).setVisible(false);
            menu.getItem(3).setVisible(false);
        } else {
            //로그인이 되어있는 경우
            Log.d("Menu","NOT Login");
            menu.getItem(0).setVisible(false);
            menu.getItem(1).setVisible(true);
            menu.getItem(2).setVisible(true);
            menu.getItem(3).setVisible(true);
        }
    }

    public void settingBasicUI(){

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close); // Drawer Nav에 추가할 Toggle 버튼 생성
        drawer.addDrawerListener(toggle); // Drawer Nav에 Toggle 버튼 추가
        toggle.syncState();


        navigationView = findViewById(R.id.nav_view);

        //navigation 메뉴 선택시 실행되는 Listener 설정
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                switch(id){
                    case R.id.mypage_menu:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, profileFragment).commit();
                        item.setChecked(false);
                        break;
                    case R.id.posting_menu:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, postingFragment).commit();
                        item.setChecked(false);
                        break;
                    case R.id.logout_menu:
                        sRequest.requestLogout(MainActivity.this);
                        item.setChecked(false);
                        break;
                    case R.id.login_menu:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, logInFragment).commit();
                        item.setChecked(false);
                        break;
                    default:
                        break;
                }

                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });


        TextView actionBar_title = toolbar.findViewById(R.id.action_bar_title);
        actionBar_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).commit();
            }
        });

        ImageView actionBar_icon = toolbar.findViewById(R.id.action_bar_icon);
        actionBar_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).commit();
            }
        });

        EditText actionBar_searchBar = toolbar.findViewById(R.id.search_keyword_input);
        actionBar_searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                searchFragment = null;
                searchFragment = new SearchFragment();

                Bundle bundle = new Bundle();
                bundle.putString("keyword", actionBar_searchBar.getText().toString());

                searchFragment.setArguments(bundle);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, searchFragment).commit();

                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(), 0);

                return true;
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Status Bar 설정
            window.setStatusBarColor(Color.BLACK);
        }
        
        //어플리케이션 기본 배경색 설정
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#E1E2E1")));

        sRequest.requestLoginGet(this);

    }
}