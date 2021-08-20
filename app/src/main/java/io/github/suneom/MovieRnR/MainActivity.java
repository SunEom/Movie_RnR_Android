package io.github.suneom.MovieRnR;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ActionBar actionBar;
    Window window;

    HomeFragment homeFragment;
    SearchFragment searchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //변수 초기화
        actionBar = getSupportActionBar();
        window = getWindow();
        homeFragment = MyApplication.homeFragment;

        settingBasicUI();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        View v = menu.findItem(R.id.search_bar).getActionView();
        if(v!= null ){
            EditText searchKeyword = v.findViewById(R.id.search_keyword_input);

            if(searchKeyword != null){
                searchKeyword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        searchFragment = null;
                        searchFragment = new SearchFragment();

                        Bundle bundle = new Bundle();
                        bundle.putString("keyword", searchKeyword.getText().toString());

                        searchFragment.setArguments(bundle);

                        getSupportFragmentManager().beginTransaction().replace(R.id.container, searchFragment).commit();

                        return true;
                    }
                });
            }
        }

        return true;
    }



    public void settingBasicUI(){

        // 액션바 설정
        actionBar.setTitle("MOVIE RNR 🎬");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#9E9E9E")));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Status Bar 설정
            window.setStatusBarColor(Color.BLACK);
        }
        
        //어플리케이션 기본 배경색 설정
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#E1E2E1")));
    }
}