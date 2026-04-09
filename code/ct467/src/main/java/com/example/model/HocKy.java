package com.example.model;

public class HocKy {
    private String maHocKy;
    private String tenHocKy;
    private String maNamHoc;
    private Boolean trangThai;

    public HocKy() {}

    public HocKy(String maHocKy, String tenHocKy, String maNamHoc, Boolean trangThai) {
        this.maHocKy = maHocKy;
        this.tenHocKy = tenHocKy;
        this.maNamHoc = maNamHoc;
        this.trangThai = trangThai;
    }

    public String getMaHocKy() { return maHocKy; }
    public void setMaHocKy(String maHocKy) { this.maHocKy = maHocKy; }

    public String getTenHocKy() { return tenHocKy; }
    public void setTenHocKy(String tenHocKy) { this.tenHocKy = tenHocKy; }

    public String getMaNamHoc() { return maNamHoc; }
    public void setMaNamHoc(String maNamHoc) { this.maNamHoc = maNamHoc; }

    public Boolean getTrangThai() { return trangThai; }
    public void setTrangThai(Boolean trangThai) { this.trangThai = trangThai; }
}
