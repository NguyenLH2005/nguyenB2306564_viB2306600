package com.example.service;

import com.example.repository.DiemRepository;
import com.example.repository.HocPhanRepository;
import java.util.Scanner;

public class HocPhanService {
    private HocPhanRepository repository;

    public HocPhanService() {
        this.repository = new HocPhanRepository();
    }

    public void xemDanhSachLopGiangDay(Scanner scanner, String maGV) {
        System.out.print("Nhap ma hoc ky (VD: HK1_2425): ");
        String maHocKy = scanner.nextLine();

        System.out.println("\nDANH SACH LOP HOC PHAN DANG GIANG DAY");
        repository.xuatDanhSachLHP(maGV, maHocKy);
    }

    public void thucHienDangKy(Scanner scanner, String mssv) {
        repository.hienThiHPHienTai();
        System.out.print("Nhap ma Lop hoc phan muon dang ky: ");
        String maLHP = scanner.nextLine();

        // Kiểm tra xem sinh viên đã đăng ký lớp học phần này chưa
        if ((new DiemRepository()).kiemTraDaDangKy(mssv, maLHP)) {
            System.out.println("=> Ban da dang ky hoc phan nay roi!");
            return; // Dừng, không cho đăng ký tiếp
        }

        // Nếu chưa đăng ký thì tiến hành đăng ký
        if (repository.dangKyHocPhan(mssv, maLHP)) {
            System.out.println("=> Dang ky mon hoc thanh cong!");
        } else {
            System.out.println("=> Dang ky that bai. Vui long kiem tra lai dieu kien tien quyet hoac gioi han lop.");
        }
    }

    public void thucHienHuyDangKy(Scanner scanner, String mssv) {
        (new DiemRepository()).hienThiHocPhanDaDangKyHienTai(mssv);
        System.out.print("Nhap ma Lop hoc phan muon huy dang ky: ");
        String maLHP = scanner.nextLine();
        if (repository.huyDangKyHocPhan(mssv, maLHP)) {
            System.out.println("=> Huy dang ky mon hoc thanh cong!");
        } else {
            System.out.println("=> Huy dang ky that bai.");
        }
    }

    public void kiemTraSiSoLHP(Scanner scanner) {
        System.out.print("Nhap ma Lop hoc phan can kiem tra: ");
        String maLHP = scanner.nextLine();
        String result = repository.kiemTraSiSo(maLHP);
        if (!"Lỗi".equals(result)) {
            System.out.println("Si so hien tai (Da dang ky / Toi da): " + result);
        } else {
            System.out.println("Khong the kiem tra si so (Sai ma LHP hoac loi he thong).");
        }
    }
}
