package io.github.suneom.MovieRnR.custom_class.Detail;

import io.github.suneom.MovieRnR.custom_class.Movie.MovieData;

public class DetailData {
    MovieData movie;
    PostingOwner user;

    public DetailData(MovieData movie, PostingOwner user) {
        this.movie = movie;
        this.user = user;
    }

    public MovieData getMovie() {
        return movie;
    }

    public void setMovie(MovieData movie) {
        this.movie = movie;
    }

    public PostingOwner getUser() {
        return user;
    }

    public void setUser(PostingOwner user) {
        this.user = user;
    }

}
