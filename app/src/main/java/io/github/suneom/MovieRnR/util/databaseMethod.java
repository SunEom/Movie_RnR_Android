package io.github.suneom.MovieRnR.util;

import io.github.suneom.MovieRnR.application.MyApplication;

public class databaseMethod {
    public static void insertLoginInfo(String id, String password){
        MyApplication.database.execSQL("insert into users(id, password) values('"+id+"', '"+password+"')");
    }

    public static void deleteLoginInfo(){
        MyApplication.database.execSQL("Delete from users");
    }
}
