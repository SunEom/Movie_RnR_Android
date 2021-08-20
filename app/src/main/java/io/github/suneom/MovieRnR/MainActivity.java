package io.github.suneom.MovieRnR;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.input.InputManager;
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
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Toolbar actionBar;
    Window window;

    HomeFragment homeFragment;
    SearchFragment searchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //변수 초기화
        actionBar = findViewById(R.id.toolbar);
        window = getWindow();
        homeFragment = MyApplication.homeFragment;

        settingBasicUI();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).commit();

    }



    public void settingBasicUI(){

        TextView actionBar_title = actionBar.findViewById(R.id.action_bar_title);
        actionBar_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).commit();
            }
        });

        EditText actionBar_searchBar = actionBar.findViewById(R.id.search_keyword_input);
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