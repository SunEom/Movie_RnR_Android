package io.github.suneom.MovieRnR;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;

public class MainActivity extends AppCompatActivity {

    ActionBar actionBar;
    Window window;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //변수 초기화
        actionBar = getSupportActionBar();
        window = getWindow();

        settingBasicUI();
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