package com.example.foodrecipes;

public class Users {
    public Users(int userId, String userName, String password, int favId) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.favId = favId;
    }
    public Users(){
    }

    private int userId;
    private String userName;
    private String password;
    private int favId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getFavId() {
        return favId;
    }

    public void setFavId(int favId) {
        this.favId = favId;
    }
}
