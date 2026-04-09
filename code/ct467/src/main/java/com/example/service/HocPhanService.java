package com.example.service;

import com.example.repository.HocPhanRepository;
import java.util.Scanner;

public class HocPhanService {
    private HocPhanRepository repository;

    public HocPhanService() {
        this.repository = new HocPhanRepository();
    }

    public void xemDanhSachLopGiangDay(Scanner scanner, String maGV) {
        System.out.print("Nhập mã học kỳ (VD: HK1_2425): ");
        String maHocKy = scanner.nextLine();

        System.out.println("\nDANH SÁCH LỚP HỌC PHẦN ĐANG GIẢNG DẠY");
        repository.xuatDanhSachLHP(maGV, maHocKy);
    }

    public void thucHienDangKy(Scanner scanner, String mssv) {
        System.out.print("Nhập mã Lớp học phần muốn đăng ký: ");
        String maLHP = scanner.nextLine();
        if (repository.dangKyHocPhan(mssv, maLHP)) {
            System.out.println("=> Đăng ký môn học thành công!");
        } else {
            System.out.println("=> Đăng ký thất bại. Vui lòng kiểm tra lại điều kiện tiên quyết hoặc giới hạn lớp.");
        }
    }

    public void thucHienHuyDangKy(Scanner scanner, String mssv) {
        System.out.print("Nhập mã Lớp học phần muốn hủy đăng ký: ");
        String maLHP = scanner.nextLine();
        if (repository.huyDangKyHocPhan(mssv, maLHP)) {
            System.out.println("=> Hủy đăng ký môn học thành công!");
        } else {
            System.out.println("=> Hủy đăng ký thất bại.");
        }
    }

    public void kiemTraSiSoLHP(Scanner scanner) {
        System.out.print("Nhập mã Lớp học phần cần kiểm tra: ");
        String maLHP = scanner.nextLine();
        String result = repository.kiemTraSiSo(maLHP);
        if (!"Lỗi".equals(result)) {
            System.out.println("Sĩ số hiện tại (Đã đăng ký / Tối đa): " + result);
        } else {
            System.out.println("Không thể kiểm tra sĩ số (Sai mã LHP hoặc lỗi hệ thống).");
        }
    }
}
