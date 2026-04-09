package com.example.model;

public class LoaiTaiKhoan {
    private String maLoaiTK;
    private String tenLoaiTK;

    public LoaiTaiKhoan() {}

    public LoaiTaiKhoan(String maLoaiTK, String tenLoaiTK) {
        this.maLoaiTK = maLoaiTK;
        this.tenLoaiTK = tenLoaiTK;
    }

    public String getMaLoaiTK() { return maLoaiTK; }
    public void setMaLoaiTK(String maLoaiTK) { this.maLoaiTK = maLoaiTK; }

    public String getTenLoaiTK() { return tenLoaiTK; }
    public void setTenLoaiTK(String tenLoaiTK) { this.tenLoaiTK = tenLoaiTK; }
}
