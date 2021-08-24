package io.github.suneom.MovieRnR.custom_class;

public class Comment {
    String commenter;
    String content;

    public Comment(String commenter, String content) {
        this.commenter = commenter;
        this.content = content;
    }

    public String getCommenter() {
        return commenter;
    }

    public void setCommenter(String commenter) {
        this.commenter = commenter;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
