package com.example.model;

public class LoaiMonHoc {
    private String maLoaiMon;
    private String tenLoaiMon;

    public LoaiMonHoc() {}

    public LoaiMonHoc(String maLoaiMon, String tenLoaiMon) {
        this.maLoaiMon = maLoaiMon;
        this.tenLoaiMon = tenLoaiMon;
    }

    public String getMaLoaiMon() { return maLoaiMon; }
    public void setMaLoaiMon(String maLoaiMon) { this.maLoaiMon = maLoaiMon; }

    public String getTenLoaiMon() { return tenLoaiMon; }
    public void setTenLoaiMon(String tenLoaiMon) { this.tenLoaiMon = tenLoaiMon; }
}
