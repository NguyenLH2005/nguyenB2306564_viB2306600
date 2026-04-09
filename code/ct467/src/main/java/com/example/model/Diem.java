package com.example.model;

public class Diem {
    private String mssv;
    private String maLHP;
    private Float diem;

    public Diem() {}

    public Diem(String mssv, String maLHP, Float diem) {
        this.mssv = mssv;
        this.maLHP = maLHP;
        this.diem = diem;
    }

    public String getMssv() { return mssv; }
    public void setMssv(String mssv) { this.mssv = mssv; }

    public String getMaLHP() { return maLHP; }
    public void setMaLHP(String maLHP) { this.maLHP = maLHP; }

    public Float getDiem() { return diem; }
    public void setDiem(Float diem) { this.diem = diem; }
}
