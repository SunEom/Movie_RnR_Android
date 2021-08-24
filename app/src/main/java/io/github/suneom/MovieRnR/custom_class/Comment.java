package io.github.suneom.MovieRnR.custom_class;

public class Comment {
    int commenter;
    String contents;
    String created;
    int id;
    String nickname;
    int movie_id;

    public Comment(int commenter, String contents, String created, int id, String nickname, int movie_id) {
        this.commenter = commenter;
        this.contents = contents;
        this.created = created;
        this.id = id;
        this.nickname = nickname;
        this.movie_id = movie_id;
    }

    public int getCommenter() {
        return commenter;
    }

    public void setCommenter(int commenter) {
        this.commenter = commenter;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }
}
