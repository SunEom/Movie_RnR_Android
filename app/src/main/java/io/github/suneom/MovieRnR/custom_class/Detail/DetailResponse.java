package io.github.suneom.MovieRnR.custom_class.Detail;

public class DetailResponse {
    int code;
    DetailData data;

    public DetailResponse(int code, DetailData data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DetailData getData() {
        return data;
    }

    public void setData(DetailData data) {
        this.data = data;
    }
}
