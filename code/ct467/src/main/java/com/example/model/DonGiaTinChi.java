package com.example.model;

public class DonGiaTinChi {
    private String maNamHoc;
    private String maLoaiMon;
    private Float soTienMotTinChi;

    public DonGiaTinChi() {}

    public DonGiaTinChi(String maNamHoc, String maLoaiMon, Float soTienMotTinChi) {
        this.maNamHoc = maNamHoc;
        this.maLoaiMon = maLoaiMon;
        this.soTienMotTinChi = soTienMotTinChi;
    }

    public String getMaNamHoc() { return maNamHoc; }
    public void setMaNamHoc(String maNamHoc) { this.maNamHoc = maNamHoc; }

    public String getMaLoaiMon() { return maLoaiMon; }
    public void setMaLoaiMon(String maLoaiMon) { this.maLoaiMon = maLoaiMon; }

    public Float getSoTienMotTinChi() { return soTienMotTinChi; }
    public void setSoTienMotTinChi(Float soTienMotTinChi) { this.soTienMotTinChi = soTienMotTinChi; }
}
