package io.github.suneom.MovieRnR.custom_class;

public class HttpResponse {
    public int code;
    public LoginUserInfo data;

    public HttpResponse(int code, LoginUserInfo data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public LoginUserInfo getData() {
        return data;
    }

    public void setData(LoginUserInfo data) {
        this.data = data;
    }
}
