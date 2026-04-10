package com.example;

import java.sql.Connection;
import java.util.Scanner;
import com.example.db.DBConnection;
import com.example.service.*;
import com.example.model.Account;

// Luồng hoạt dộng Main -> service -> Repository -> DB
// Main chạy console
// service là nơi chứa logic nghiệp vụ
// Repository là nơi chứa logic truy cập dữ liệu
// model đại diện bảng trong cơ sở dữ liệu (các thuộc tính, getter, setter, constructor, toString)

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static String loggedInRole = null;
    private static String loggedInUsername = null;
    private static boolean running = true;

    // Services
    private static TaiKhoanService taiKhoanService = new TaiKhoanService();
    private static DaoTaoService daoTaoService = new DaoTaoService();
    private static NhanSuService nhanSuService = new NhanSuService();
    private static HocPhanService hocPhanService = new HocPhanService();
    private static DiemService diemService = new DiemService();
    private static HocPhiService hocPhiService = new HocPhiService();
    private static SinhVienService sinhVienService = new SinhVienService();

    public static void main(String[] args) {
        System.setProperty("file.encoding", "UTF-8");
        try {
            Connection conn = DBConnection.getConnection();
            System.out.println("Ket noi co so du lieu thanh cong!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Canh bao: Loi ket noi DB. He thong se tiep tuc mo giao dien Menu.");
        }

        while (running) {
            if (loggedInRole == null) {
                // Chưa đăng nhập thì hiện màn hình đăng nhập
                hienThiDangNhap();
            } else {
                // Đã đăng nhập thì tùy loại tài khoản hiển thị menu tương ứng
                switch (loggedInRole) {
                    case "SV":
                        menuSinhVien();
                        break;
                    case "GV":
                        menuGiangVien();
                        break;
                    case "ADMIN":
                        menuAdmin();
                        break;
                    default:
                        System.out.println("Loi quyen quyen truy cap. He thong se dang xuat.");
                        loggedInRole = null;
                }
            }
        }
        scanner.close();
    }

    private static void hienThiDangNhap() {
        System.out.println("\n=============================================");
        System.out.println("       HE THONG QUAN LY SINH VIEN            ");
        System.out.println("=============================================");
        System.out.println("Goi y: dung 'sv', 'gv', 'admin', pass '123'");
        System.out.print("Ten dang nhap (hoac go '0' de thoat): ");
        String username = scanner.nextLine().trim();

        if (username.equals("0")) {
            running = false;
            System.out.println("Da dong chuong trinh. Cam on ban da su dung he thong!");
            return;
        }

        System.out.print("Mat khau: ");
        String password = scanner.nextLine().trim();

        Account acc = taiKhoanService.login(username, password);
        if (acc != null) {
            loggedInRole = acc.getMaLoaiTK();
            loggedInUsername = acc.getUsername();
            System.out.println("=> Dang nhap thanh cong! Quyen: " + loggedInRole);
        } else {
            System.out.println("=> Sai tai khoan hoac mat khau! Vui long thu lai.");
        }
    }

    // ================== CÁC MENU HOẠT ĐỘNG ĐỘC LẬP ================== //

    private static void menuSinhVien() {
        boolean svRunning = true;
        while (svRunning) {
            System.out.println("\n================ MENU SINH VIEN ================");
            System.out.println("1. Dang ky hoc phan");
            System.out.println("2. Huy dang ky hoc phan");
            System.out.println("3. Dong tien hoc phi");
            System.out.println("4. Xem bang diem & Tinh GPA");
            System.out.println("5. Xem va dang ky hoc lai");
            System.out.println("0. Dang xuat");
            System.out.println("==============================================");
            System.out.print("Chon chuc nang (0-6): ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    hocPhanService.thucHienDangKy(scanner, loggedInUsername);
                    break;
                case "2":
                    hocPhanService.thucHienHuyDangKy(scanner, loggedInUsername);
                    break;
                case "3":
                    hocPhiService.thucHienDongTien(scanner, loggedInUsername);
                    break;
                case "4":
                    sinhVienService.xemBangDiemVaGPA(loggedInUsername);
                    break;
                case "5":
                    hocPhanService.thucHienHocLai(scanner, loggedInUsername);
                    break;
                case "0":
                    loggedInRole = null;
                    svRunning = false;
                    System.out.println("=> Da dang xuat!");
                    break;
                default:
                    System.out.println("Lua chon khong hop le, vui long chon lai!");
            }
        }
    }

    private static void menuGiangVien() {
        boolean gvRunning = true;
        while (gvRunning) {
            System.out.println("\n================ MENU GIANG VIEN ================");
            System.out.println("1. Danh sach lop de nhap diem");
            System.out.println("2. Quan ly diem so");
            System.out.println("3. Bao cao chat luong dao tao");
            System.out.println("0. Dang xuat");
            System.out.println("==============================================");
            System.out.print("Chon chuc nang (0-3): ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    hocPhanService.xemDanhSachLopGiangDay(scanner, loggedInUsername);
                    break;
                case "2":
                    diemService.thucHienNhapDiem(scanner);
                    break;
                case "3":
                    diemService.xuatBaoCaoChatLuong(scanner);
                    break;
                case "0":
                    loggedInRole = null;
                    loggedInUsername = null;
                    gvRunning = false;
                    System.out.println("=> Da dang xuat!");
                    break;
                default:
                    System.out.println("Lua chon chua dung!");
            }
        }
    }

    private static void menuAdmin() {
        boolean adminRunning = true;
        while (adminRunning) {
            System.out.println("\n================= MENU ADMIN =================");
            System.out.println("1. Quan ly cau truc dao tao (Khoa, Nganh, Lop)");
            System.out.println("2. Quan ly khung chuong trinh (Mon hoc)");
            System.out.println("3. Cau hinh he thong hoc ky (Hoc ky, Don gia)");
            System.out.println("4. Quan ly ho so nhan su (Giang vien, Sinh vien)");
            System.out.println("5. Quan ly lop hoc phan");
            System.out.println("6. Thong ke sinh vien no hoc phi");
            System.out.println("7. Bao cao doanh thu hoc phi");
            System.out.println("0. Dang xuat");
            System.out.println("==============================================");
            System.out.print("Chon chuc nang (0-6): ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    daoTaoService.menuQuanLyCauTruc(scanner);
                    break;
                case "2":
                    daoTaoService.menuQuanLyMonHoc(scanner);
                    break;
                case "3":
                    daoTaoService.menuQuanLyHocKy(scanner);
                    break;
                case "4":
                    nhanSuService.menuQuanLyNhanSu(scanner);
                    break;
                case "5":
                    hocPhanService.menuQuanLyLopHocPhan(scanner);
                    break;
                case "6":
                    hocPhiService.xemThongKeNoHocPhi(scanner);
                    break;
                case "7":
                    hocPhiService.xemBaoCaoDoanhThu(scanner);
                    break;
                case "0":
                    loggedInRole = null;
                    loggedInUsername = null;
                    adminRunning = false;
                    System.out.println("=> Da dang xuat!");
                    break;
                default:
                    System.out.println("Lua chon chua dung!");
            }
        }
    }
}
