package io.github.suneom.MovieRnR.util;

public class sUtil {
    public static int createRandomMovieCardImageIndex(){
        double dValue = Math.random();
        int iValue = (int)(dValue * 8);
        return iValue;
    }
}