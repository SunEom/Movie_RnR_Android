package io.github.suneom.MovieRnR.custom_class.Comment;

import java.util.ArrayList;

public class CommentResponse {
    public int code;
    public ArrayList<Comment> data;

    public CommentResponse(int code, ArrayList<Comment> data) {
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
