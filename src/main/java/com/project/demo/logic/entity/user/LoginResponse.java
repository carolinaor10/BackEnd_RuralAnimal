package com.project.demo.logic.entity.user;

public class LoginResponse {
    private String token;

    private TblUser authUser;

    private long expiresIn;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public TblUser getAuthUser() {
        return authUser;
    }

    public void setAuthUser(TblUser authUser) {
        this.authUser = authUser;
    }
}
