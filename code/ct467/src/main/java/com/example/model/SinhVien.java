package com.example.model;

import java.time.LocalDate;

public class SinhVien {
    private String mssv;
    private String hoTen;
    private LocalDate ngaySinh;
    private String maLop;
    private String tenLop;


    public SinhVien() {}

    public SinhVien(String mssv, String hoTen, LocalDate ngaySinh, String maLop) {
        this.mssv = mssv;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.maLop = maLop;
    }

    public String getMssv() { return mssv; }
    public void setMssv(String mssv) { this.mssv = mssv; }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public LocalDate getNgaySinh() { return ngaySinh; }
    public void setNgaySinh(LocalDate ngaySinh) { this.ngaySinh = ngaySinh; }

    public String getMaLop() { return maLop; }
    public void setMaLop(String maLop) { this.maLop = maLop; }

    public String getTenLop() { return tenLop; }
    public void setTenLop(String tenLop) { this.tenLop = tenLop; }
}
