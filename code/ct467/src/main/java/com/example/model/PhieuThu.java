package com.example.model;

import java.time.LocalDateTime;

public class PhieuThu {
    private Integer maPhieu;
    private String maHocPhi;
    private LocalDateTime ngayThu;
    private Float soTienThu;
    private String phuongThuc;

    public PhieuThu() {}

    public PhieuThu(Integer maPhieu, String maHocPhi, LocalDateTime ngayThu, Float soTienThu, String phuongThuc) {
        this.maPhieu = maPhieu;
        this.maHocPhi = maHocPhi;
        this.ngayThu = ngayThu;
        this.soTienThu = soTienThu;
        this.phuongThuc = phuongThuc;
    }

    public Integer getMaPhieu() { return maPhieu; }
    public void setMaPhieu(Integer maPhieu) { this.maPhieu = maPhieu; }

    public String getMaHocPhi() { return maHocPhi; }
    public void setMaHocPhi(String maHocPhi) { this.maHocPhi = maHocPhi; }

    public LocalDateTime getNgayThu() { return ngayThu; }
    public void setNgayThu(LocalDateTime ngayThu) { this.ngayThu = ngayThu; }

    public Float getSoTienThu() { return soTienThu; }
    public void setSoTienThu(Float soTienThu) { this.soTienThu = soTienThu; }

    public String getPhuongThuc() { return phuongThuc; }
    public void setPhuongThuc(String phuongThuc) { this.phuongThuc = phuongThuc; }
}
