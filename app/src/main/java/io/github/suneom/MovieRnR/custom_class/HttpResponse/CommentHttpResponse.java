package io.github.suneom.MovieRnR.custom_class.HttpResponse;

import java.util.ArrayList;

import io.github.suneom.MovieRnR.custom_class.Comment;

public class CommentHttpResponse {
    public int code;
    public ArrayList<Comment> data;

    public CommentHttpResponse(int code, ArrayList<Comment> data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ArrayList<Comment> getData() {
        return data;
    }

    public void setData(ArrayList<Comment> data) {
        this.data = data;
    }
}
