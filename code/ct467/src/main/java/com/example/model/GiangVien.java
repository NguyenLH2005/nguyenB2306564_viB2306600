package com.example.model;

import java.time.LocalDate;

public class GiangVien {
    private String maGV;
    private String hoTen;
    private LocalDate ngaySinh;
    private String maKhoa;
    private String tenKhoa;


    public GiangVien() {}

    public GiangVien(String maGV, String hoTen, LocalDate ngaySinh, String maKhoa) {
        this.maGV = maGV;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.maKhoa = maKhoa;
    }

    public String getMaGV() { return maGV; }
    public void setMaGV(String maGV) { this.maGV = maGV; }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public LocalDate getNgaySinh() { return ngaySinh; }
    public void setNgaySinh(LocalDate ngaySinh) { this.ngaySinh = ngaySinh; }

    public String getMaKhoa() { return maKhoa; }
    public void setMaKhoa(String maKhoa) { this.maKhoa = maKhoa; }

    public String getTenKhoa() { return tenKhoa; }
    public void setTenKhoa(String tenKhoa) { this.tenKhoa = tenKhoa; }
}
