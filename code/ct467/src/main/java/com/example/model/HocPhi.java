package com.example.model;

public class HocPhi {
    private String maHocPhi;
    private String mssv;
    private String maHocKy;
    private Float tongTien;
    private Float daDong;
    private String trangThai;

    public HocPhi() {}

    public HocPhi(String maHocPhi, String mssv, String maHocKy, Float tongTien, Float daDong, String trangThai) {
        this.maHocPhi = maHocPhi;
        this.mssv = mssv;
        this.maHocKy = maHocKy;
        this.tongTien = tongTien;
        this.daDong = daDong;
        this.trangThai = trangThai;
    }

    public String getMaHocPhi() { return maHocPhi; }
    public void setMaHocPhi(String maHocPhi) { this.maHocPhi = maHocPhi; }

    public String getMssv() { return mssv; }
    public void setMssv(String mssv) { this.mssv = mssv; }

    public String getMaHocKy() { return maHocKy; }
    public void setMaHocKy(String maHocKy) { this.maHocKy = maHocKy; }

    public Float getTongTien() { return tongTien; }
    public void setTongTien(Float tongTien) { this.tongTien = tongTien; }

    public Float getDaDong() { return daDong; }
    public void setDaDong(Float daDong) { this.daDong = daDong; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
}
