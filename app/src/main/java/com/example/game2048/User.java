package com.example.game2048;

/**
 * 用户实体类
 */
public class User {

    private int id;//id
    private String username;//用户名
    private String userpwd;//密码

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpwd() {
        return userpwd;
    }

    public void setUserpwd(String userpwd) {
        this.userpwd = userpwd;
    }


}