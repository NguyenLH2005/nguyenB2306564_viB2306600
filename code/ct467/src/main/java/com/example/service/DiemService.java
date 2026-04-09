package com.example.service;

import com.example.repository.DiemRepository;

import java.util.Scanner;

public class DiemService {
    private DiemRepository repository;

    public DiemService() {
        this.repository = new DiemRepository();
    }

    public void xuatBaoCaoChatLuong(Scanner scanner) {
        System.out.print("Nhập mã Lớp học phần cần báo cáo: ");
        String maLHP = scanner.nextLine();
        
        System.out.println("\nBÁO CÁO CHẤT LƯỢNG ĐÀO TẠO - MÃ LỚP: " + maLHP);
        repository.baoCaoChatLuongLHP(maLHP);
    }

    public void thucHienNhapDiem(Scanner scanner) {
        System.out.print("Nhập mã Lớp học phần: ");
        String maLHP = scanner.nextLine();
        System.out.print("Nhập MSSV sinh viên cần chấm điểm: ");
        String mssv = scanner.nextLine();
        System.out.print("Nhập điểm (0.0 - 10.0): ");
        float diem = Float.parseFloat(scanner.nextLine());
        
        if (diem < 0 || diem > 10) {
            System.out.println("=> Điểm không hợp lệ!");
            return;
        }

        if (repository.nhapDiem(mssv, maLHP, diem)) {
            System.out.println("=> Nhập/Cập nhật điểm thành công cho SV: " + mssv);
        } else {
            System.out.println("=> Nhập điểm thất bại. Vui lòng kiểm tra lại MSSV hoặc Mã LHP.");
        }
    }
}
