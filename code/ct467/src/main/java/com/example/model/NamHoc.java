package com.example.model;

public class NamHoc {
    private String maNamHoc;
    private String tenNamHoc;

    public NamHoc() {}

    public NamHoc(String maNamHoc, String tenNamHoc) {
        this.maNamHoc = maNamHoc;
        this.tenNamHoc = tenNamHoc;
    }

    public String getMaNamHoc() { return maNamHoc; }
    public void setMaNamHoc(String maNamHoc) { this.maNamHoc = maNamHoc; }

    public String getTenNamHoc() { return tenNamHoc; }
    public void setTenNamHoc(String tenNamHoc) { this.tenNamHoc = tenNamHoc; }
}
