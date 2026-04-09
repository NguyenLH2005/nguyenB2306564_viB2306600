package com.example.model;

public class MonHoc {
    private String maMon;
    private String tenMon;
    private Integer soTinChi;
    private String maLoaiMon;

    public MonHoc() {}

    public MonHoc(String maMon, String tenMon, Integer soTinChi, String maLoaiMon) {
        this.maMon = maMon;
        this.tenMon = tenMon;
        this.soTinChi = soTinChi;
        this.maLoaiMon = maLoaiMon;
    }

    public String getMaMon() { return maMon; }
    public void setMaMon(String maMon) { this.maMon = maMon; }

    public String getTenMon() { return tenMon; }
    public void setTenMon(String tenMon) { this.tenMon = tenMon; }

    public Integer getSoTinChi() { return soTinChi; }
    public void setSoTinChi(Integer soTinChi) { this.soTinChi = soTinChi; }

    public String getMaLoaiMon() { return maLoaiMon; }
    public void setMaLoaiMon(String maLoaiMon) { this.maLoaiMon = maLoaiMon; }
}
