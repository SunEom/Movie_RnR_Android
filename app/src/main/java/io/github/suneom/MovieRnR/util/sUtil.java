package io.github.suneom.MovieRnR.util;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import io.github.suneom.MovieRnR.fragment.DetailFragment;

public class sUtil {
    public static int createRandomMovieCardImageIndex(){
        double dValue = Math.random();
        int iValue = (int)(dValue * 8);
        return iValue;
    }

    public static String ParseDateTilDate(String date){
        return date.substring(0,10);
    }

    public static void CreateNewSimpleAlertDialog(Context context, String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        if(!title.equals("")){
            builder.setTitle(title);
        }
        if(!message.equals("")){
            builder.setMessage(message);
        }
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog alertDialog = builder.create();

        alertDialog.show();
        return;
    }

    public static void CreatePostDeleteCheckAlertDialog( int movieId, DetailFragment fragment){
        AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getContext());

        builder.setMessage("Do you really want to delete this post?");

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.setPositiveButton("Sure", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sRequest.requestDeletePosting(movieId,fragment);
            }
        });


        AlertDialog alertDialog = builder.create();

        alertDialog.show();
        return;
    }
}
