package com.example.model;

public class LopHocPhan {
    private String maLHP;
    private String maMon;
    private String maHocKy;
    private String maGV;
    private Integer soLuongMax;

    public LopHocPhan() {}

    public LopHocPhan(String maLHP, String maMon, String maHocKy, String maGV, Integer soLuongMax) {
        this.maLHP = maLHP;
        this.maMon = maMon;
        this.maHocKy = maHocKy;
        this.maGV = maGV;
        this.soLuongMax = soLuongMax;
    }

    public String getMaLHP() { return maLHP; }
    public void setMaLHP(String maLHP) { this.maLHP = maLHP; }

    public String getMaMon() { return maMon; }
    public void setMaMon(String maMon) { this.maMon = maMon; }

    public String getMaHocKy() { return maHocKy; }
    public void setMaHocKy(String maHocKy) { this.maHocKy = maHocKy; }

    public String getMaGV() { return maGV; }
    public void setMaGV(String maGV) { this.maGV = maGV; }

    public Integer getSoLuongMax() { return soLuongMax; }
    public void setSoLuongMax(Integer soLuongMax) { this.soLuongMax = soLuongMax; }
}
