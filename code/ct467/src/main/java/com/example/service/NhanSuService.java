package com.example.service;

import com.example.model.GiangVien;
import com.example.model.SinhVien;
import com.example.repository.NhanSuRepository;

import java.util.Scanner;

public class NhanSuService {
    private NhanSuRepository repository;

    public NhanSuService() {
        this.repository = new NhanSuRepository();
    }

    public void themSinhVien(Scanner scanner) {
        System.out.print("Nhập năm bắt đầu học (VD: 23, 24): ");
        String namHoc = scanner.nextLine();
        
        String newMssv = repository.generateMSSV(namHoc);
        System.out.println("=> MSSV được cấp tự động: " + newMssv);

        System.out.print("Nhập họ tên sinh viên: ");
        String hoTen = scanner.nextLine();

        System.out.print("Nhập mã lớp (VD: LOP01): ");
        String maLop = scanner.nextLine();

        SinhVien sv = new SinhVien();
        sv.setMssv(newMssv);
        sv.setHoTen(hoTen);
        sv.setMaLop(maLop);
        // NgaySinh can be ignored or asked if needed.

        boolean success = repository.createSinhVien(sv);
        if (success) {
            System.out.println("=> Thêm sinh viên thành công! Tài khoản đăng nhập đã được cấp (MK: 123456)");
        } else {
            System.out.println("=> Thêm sinh viên thất bại!");
        }
    }

    public void themGiangVien(Scanner scanner) {
        System.out.print("Nhập mã Khoa của giảng viên (VD: 01): ");
        String maKhoa = scanner.nextLine();
        
        String newMaGv = repository.generateMaGV(maKhoa);
        System.out.println("=> Mã GV được cấp tự động: " + newMaGv);

        System.out.print("Nhập họ tên giảng viên: ");
        String hoTen = scanner.nextLine();

        GiangVien gv = new GiangVien();
        gv.setMaGV(newMaGv);
        gv.setHoTen(hoTen);
        gv.setMaKhoa(maKhoa);

        boolean success = repository.createGiangVien(gv);
        if (success) {
            System.out.println("=> Thêm giảng viên thành công! Tài khoản đăng nhập đã được cấp (MK: 123456)");
        } else {
            System.out.println("=> Thêm giảng viên thất bại!");
        }
    }
}
