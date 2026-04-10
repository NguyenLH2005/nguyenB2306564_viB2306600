package com.example.service;

import com.example.repository.DiemRepository;
import com.example.repository.HocPhanRepository;
import java.util.Scanner;

public class HocPhanService {
    private HocPhanRepository repository;

    public HocPhanService() {
        this.repository = new HocPhanRepository();
    }

    public void menuQuanLyLopHocPhan(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("\n===== MENU QUAN LY LOP HOC PHAN =====");
            System.out.println("1. Xem danh sach tat ca cac lop hoc phan");
            System.out.println("2. Them lop hoc phan moi");
            System.out.println("3. Sua thong tin lop hoc phan");
            System.out.println("4. Xoa lop hoc phan");
            System.out.println("0. Quay lai menu chinh");
            System.out.print("Chon chuc nang (0-4): ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    repository.layTatCaHocPhan();
                    break;
                case "2":
                    thucHienThemHocPhan(scanner);
                    break;
                case "3":
                    thucHienSuaHocPhan(scanner);
                    break;
                case "4":
                    thucHienXoaHocPhan(scanner);
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("Lua chon khong hop le, vui long chon lai!");
            }
        }
    }

    private void thucHienThemHocPhan(Scanner scanner) {
        System.out.print("Nhap Ma LHP (VD: LHP01): ");
        String maLHP = scanner.nextLine().trim();
        System.out.print("Nhap Ma Mon (VD: CT101): ");
        String maMon = scanner.nextLine().trim();
        System.out.print("Nhap Ma Hoc Ky (VD: HK1_2425): ");
        String maHocKy = scanner.nextLine().trim();
        System.out.print("Nhap Ma Giao Vien (VD: GV01): ");
        String maGV = scanner.nextLine().trim();
        System.out.print("Nhap So Luong Toi Da (Mac dinh 40): ");
        String soLuongStr = scanner.nextLine().trim();
        int soLuongMax = 40;
        if (!soLuongStr.isEmpty()) {
            try {
                soLuongMax = Integer.parseInt(soLuongStr);
            } catch (Exception e) {
                System.out.println("So luong khong hop le, dung mac dinh la 40.");
            }
        }
        repository.themHocPhan(maLHP, maMon, maHocKy, maGV, soLuongMax);
    }

    private void thucHienSuaHocPhan(Scanner scanner) {
        System.out.print("Nhap Ma LHP can sua: ");
        String maLHP = scanner.nextLine().trim();
        System.out.print("Nhap Ma Mon moi: ");
        String maMon = scanner.nextLine().trim();
        System.out.print("Nhap Ma Hoc Ky moi: ");
        String maHocKy = scanner.nextLine().trim();
        System.out.print("Nhap Ma GV moi: ");
        String maGV = scanner.nextLine().trim();
        System.out.print("Nhap So Luong Toi Da moi: ");
        String soLuongStr = scanner.nextLine().trim();
        int soLuongMax = 40;
        if (!soLuongStr.isEmpty()) {
            try {
                soLuongMax = Integer.parseInt(soLuongStr);
            } catch (Exception e) {
                System.out.println("So luong khong hop le, dung mac dinh la 40.");
            }
        }
        repository.suaHocPhan(maLHP, maMon, maHocKy, maGV, soLuongMax);
    }

    private void thucHienXoaHocPhan(Scanner scanner) {
        System.out.print("Nhap Ma LHP can xoa: ");
        String maLHP = scanner.nextLine().trim();
        System.out.print("Ban co chac chan muon xoa lop hoc phan nay khong? (y/n): ");
        String confirm = scanner.nextLine().trim();
        if (confirm.equalsIgnoreCase("y")) {
            repository.xoaHocPhan(maLHP);
        } else {
            System.out.println("Da huy xoa.");
        }
    }

    public void xemDanhSachLopGiangDay(Scanner scanner, String maGV) {
        System.out.print("Nhap ma hoc ky (VD: HK1_2425): ");
        String maHocKy = scanner.nextLine().trim();

        System.out.println("\nDANH SACH LOP HOC PHAN DANG GIANG DAY");
        repository.xuatDanhSachLHP(maGV, maHocKy);
    }

    public void thucHienDangKy(Scanner scanner, String mssv) {
        repository.hienThiHPHienTai();
        System.out.print("Nhap ma Lop hoc phan muon dang ky: ");
        String maLHP = scanner.nextLine().trim();

        // Kiểm tra xem sinh viên đã đăng ký lớp học phần này chưa
        if ((new DiemRepository()).kiemTraDaDangKy(mssv, maLHP)) {
            System.out.println("=> Ban da dang ky hoc phan nay roi!");
            return; // Dừng, không cho đăng ký tiếp
        }

        // Nếu chưa đăng ký thì tiến hành đăng ký
        if (repository.dangKyHocPhan(mssv, maLHP)) {
            System.out.println("=> Dang ky mon hoc thanh cong!");
        } else {
            System.out.println("=> Dang ky that bai");
        }
    }

    public void thucHienHuyDangKy(Scanner scanner, String mssv) {
        (new DiemRepository()).hienThiHocPhanDaDangKyHienTai(mssv);
        System.out.print("Nhap ma Lop hoc phan muon huy dang ky: ");
        String maLHP = scanner.nextLine().trim();
        if (repository.huyDangKyHocPhan(mssv, maLHP)) {
            System.out.println("=> Huy dang ky mon hoc thanh cong!");
        } else {
            System.out.println("=> Huy dang ky that bai.");
        }
    }

    public void kiemTraSiSoLHP(Scanner scanner) {
        (new HocPhanRepository()).layTatCaHocPhan();
        System.out.print("Nhap ma Lop hoc phan can kiem tra: ");
        String maLHP = scanner.nextLine().trim();
        String result = repository.kiemTraSiSo(maLHP);
        if (!"Lỗi".equals(result)) {
            System.out.println("Si so hien tai (Da dang ky / Toi da): " + result);
        } else {
            System.out.println("Khong the kiem tra si so (Sai ma LHP hoac loi he thong).");
        }
    }

    public void thucHienHocLai(Scanner scanner, String mssv) {
        int tinChiHocLai = repository.tinhTinChiHocLai(mssv);
        if (tinChiHocLai > 0) {
            System.out.println("=> Ban can hoc lai " + tinChiHocLai + " tin chi.");
        } else {
            System.out.println("=> Ban khong can hoc lai mon nao.");
            return;
        }
        if ((new HocPhanRepository()).layHocPhanCanHocLai(mssv) == false) {
            return;
        }
        System.out.print("Nhap ma Lop hoc phan muon hoc lai: ");
        String maLHP = scanner.nextLine().trim();
        if (repository.dangKyHocPhan(mssv, maLHP)) {
            System.out.println("=> Dang ky hoc lai thanh cong!");
        } else {
            System.out.println("=> Dang ky hoc lai that bai.");
        }
    }
}
