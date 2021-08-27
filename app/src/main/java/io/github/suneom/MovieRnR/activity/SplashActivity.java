package io.github.suneom.MovieRnR.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import io.github.suneom.MovieRnR.application.MyApplication;
import io.github.suneom.MovieRnR.util.sRequest;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDb();

        Cursor cursor = MyApplication.database.rawQuery("select id, password from users order by _id DESC limit 1",null);
        if(cursor.getCount()!=0){
            cursor.moveToNext();
            String id = cursor.getString(0);
            String password = cursor.getString(1);

            sRequest.requestLoginPost(id, password, this);

        }

    }

    public void setDb(){
        String db_name = "moviernr.db";

        MyApplication.database = openOrCreateDatabase(db_name, MODE_PRIVATE, null);
        MyApplication.database.execSQL("create table if not exists users(_id integer primary key autoincrement, id text, password text)");

    }

    public void startMainActivity(){
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

