package io.github.suneom.MovieRnR.custom_class.Profile;

import java.util.ArrayList;

public class ProfileResponse {
    public int code;
    public ArrayList<ProfileData> data;

    public ProfileResponse(int code, ArrayList<ProfileData> data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ArrayList<ProfileData> getData() {
        return data;
    }

    public void setData(ArrayList<ProfileData> data) {
        this.data = data;
    }
}
