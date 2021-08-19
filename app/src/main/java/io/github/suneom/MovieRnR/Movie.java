package io.github.suneom.MovieRnR;

public class Movie {
    String title;
    String genres;
    String description;
    float rates;
    int commentCount;

    public Movie(String title, String genres, String description, float rates, int commentCount) {
        this.title = title;
        this.genres = genres;
        this.description = description;
        this.rates = rates;
        this.commentCount = commentCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getRates() {
        return rates;
    }

    public void setRates(float rates) {
        this.rates = rates;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}
