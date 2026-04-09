package com.example.model;

public class Account {
    private String username;
    private String password;
    private String maLoaiTK;

    public Account() {}

    public Account(String username, String password, String maLoaiTK) {
        this.username = username;
        this.password = password;
        this.maLoaiTK = maLoaiTK;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getMaLoaiTK() { return maLoaiTK; }
    public void setMaLoaiTK(String maLoaiTK) { this.maLoaiTK = maLoaiTK; }
}
