package com.example.service;

import com.example.repository.DiemRepository;

import java.util.Scanner;

public class DiemService {
    private DiemRepository repository;

    public DiemService() {
        this.repository = new DiemRepository();
    }

    public void xuatBaoCaoChatLuong(Scanner scanner) {
        System.out.print("Nhap ma Lop hoc phan can bao cao: ");
        String maLHP = scanner.nextLine();
        
        System.out.println("\nBAO CAO CHAT LUONG DAO TAO - MA LOP: " + maLHP);
        repository.baoCaoChatLuongLHP(maLHP);
    }

    public void thucHienNhapDiem(Scanner scanner) {
        System.out.print("Nhap ma Lop hoc phan: ");
        String maLHP = scanner.nextLine();
        
        repository.xemDanhSachSinhVienVaDiem(maLHP);
        
        System.out.print("Nhap MSSV sinh vien can cham diem: ");
        String mssv = scanner.nextLine();
        System.out.print("Nhap diem (0.0 - 10.0): ");
        float diem = Float.parseFloat(scanner.nextLine());
        
        if (diem < 0 || diem > 10) {
            System.out.println("=> Diem khong hop le!");
            return;
        }

        if (repository.nhapDiem(mssv, maLHP, diem)) {
            System.out.println("=> Nhap/Cap nhat diem thanh cong cho SV: " + mssv);
        } else {
            System.out.println("=> Nhap diem that bai. Vui long kiem tra lai MSSV hoac Ma LHP.");
        }
    }
}
