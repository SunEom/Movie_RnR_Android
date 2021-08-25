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
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import io.github.suneom.MovieRnR.application.MyApplication;
import io.github.suneom.MovieRnR.R;
import io.github.suneom.MovieRnR.fragment.DetailFragment;
import io.github.suneom.MovieRnR.fragment.HomeFragment;
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

    HomeFragment homeFragment;
    SearchFragment searchFragment;
    PostingFragment postingFragment;
    LogInFragment logInFragment;
    ProfileFragment profileFragment;

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
            if(curFragment.getArguments().getString("keyword") == null){
                //HomeFragment 에서 DetailFragment로 접근한 경우
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).commit();
            } else {
                //SearchFragment 에서 DetailFragment로 접근한 경우
                SearchFragment searchFragment = new SearchFragment();
                int id = getSupportFragmentManager().findFragmentById(R.id.fragment_container).getArguments().getInt("id");
                String keyword = getSupportFragmentManager().findFragmentById(R.id.fragment_container).getArguments().getString("keyword");

                Bundle bundle = new Bundle();
                bundle.putString("keyword",keyword);
                searchFragment.setArguments(bundle);

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,searchFragment).commit();
            }
        } else if(curFragment instanceof SearchFragment){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).commit();
        } else {
            super.onBackPressed();
        }
    }

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



    public void settingBasicUI(){

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close); // Drawer Nav에 추가할 Toggle 버튼 생성
        drawer.addDrawerListener(toggle); // Drawer Nav에 Toggle 버튼 추가
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        //navigation 메뉴 선택시 실행되는 Listener 설정
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                switch(id){
                    case R.id.mypage:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, profileFragment).commit();
                        break;
                    case R.id.posting:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, postingFragment).commit();
                        break;
                    case R.id.logout:
                        Toast.makeText(getApplicationContext(), "Log out",Toast.LENGTH_LONG).show();
                        sRequest.requestLogout();
                        break;
                    case R.id.login:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, logInFragment).commit();
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
    }
}