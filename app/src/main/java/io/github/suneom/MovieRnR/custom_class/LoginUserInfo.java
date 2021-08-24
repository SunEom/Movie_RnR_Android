package io.github.suneom.MovieRnR.custom_class;

public class LoginUserInfo {
    public int id;
    public String user_id;
    public String password;
    public String nickname;
    public String gender;
    public int aboutId;
    public String biography;
    public String instagram;
    public String facebook;
    public String twitter;
    public int my_id;

    public LoginUserInfo(int id, String user_id, String password, String nickname, String gender, int aboutId, String biography, String instagram, String facebook, String twitter, int my_id) {
        this.id = id;
        this.user_id = user_id;
        this.password = password;
        this.nickname = nickname;
        this.gender = gender;
        this.aboutId = aboutId;
        this.biography = biography;
        this.instagram = instagram;
        this.facebook = facebook;
        this.twitter = twitter;
        this.my_id = my_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAboutId() {
        return aboutId;
    }

    public void setAboutId(int aboutId) {
        this.aboutId = aboutId;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public int getMy_id() {
        return my_id;
    }

    public void setMy_id(int my_id) {
        this.my_id = my_id;
    }
}
