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

    public static void main(String[] args) {
        System.setProperty("file.encoding", "UTF-8");
        try {
            Connection conn = DBConnection.getConnection();
            System.out.println("Kết nối cơ sở dữ liệu thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Cảnh báo: Lỗi kết nối DB. Hệ thống sẽ tiếp tục mở giao diện Menu.");
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
                        System.out.println("Lỗi quyền truy cập. Hệ thống sẽ đăng xuất.");
                        loggedInRole = null;
                }
            }
        }
        scanner.close();
    }

    private static void hienThiDangNhap() {
        System.out.println("\n=============================================");
        System.out.println("       HỆ THỐNG QUẢN LÝ SINH VIÊN            ");
        System.out.println("=============================================");
        System.out.println("Gợi ý: dùng 'admin', mật khẩu 'admin123'");
        System.out.print("Tên đăng nhập (hoặc gõ '0' để thoát): ");
        String username = scanner.nextLine();

        if (username.equals("0")) {
            running = false;
            System.out.println("Đã đóng chương trình. Cảm ơn bạn đã sử dụng hệ thống!");
            return;
        }

        System.out.print("Mật khẩu: ");
        String password = scanner.nextLine();

        Account acc = taiKhoanService.login(username, password);
        if (acc != null) {
            loggedInRole = acc.getMaLoaiTK();
            loggedInUsername = acc.getUsername();
            System.out.println("=> Đăng nhập thành công! Quyền: " + loggedInRole);
        } else {
            System.out.println("=> Sai tài khoản hoặc mật khẩu! Vui lòng thử lại.");
        }
    }

    // ================== CÁC MENU HOẠT ĐỘNG ĐỘC LẬP ================== //

    private static void menuSinhVien() {
        boolean svRunning = true;
        while (svRunning) {
            System.out.println("\n================ MENU SINH VIÊN ================");
            System.out.println("1. Đăng ký học phần");
            System.out.println("2. Hủy đăng ký học phần");
            System.out.println("3. Đóng tiền học phí");
            System.out.println("4. Xem bảng điểm & Tính GPA");
            System.out.println("5. Kiểm tra sĩ số lớp học phần");
            System.out.println("6. Tra cứu công nợ cá nhân");
            System.out.println("0. Đăng xuất");
            System.out.println("==============================================");
            System.out.print("Chọn chức năng (0-6): ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("\n--- [1] Course Registration (Call HocPhanService) ---");
                    break;
                case "2":
                    System.out.println("\n--- [2] Cancel Course Registration (Call HocPhanService) ---");
                    break;
                case "3":
                    System.out.println("\n--- [3] Pay Tuition Fee (Call HocPhiService) ---");
                    break;
                case "4":
                    System.out.println("\n--- [4] View Transcripts & Calculate GPA (Call SinhVienService) ---");
                    break;
                case "5":
                    System.out.println("\n--- [5] Check Class Size (Call HocPhanService) ---");
                    break;
                case "6":
                    System.out.println("\n--- [6] Look up Personal Debt (Call SinhVienService) ---");
                    break;
                case "0":
                    loggedInRole = null;
                    svRunning = false;
                    System.out.println("=> Logged out!");
                    break;
                default:
                    System.out.println("Invalid selection, please select again!");
            }
        }
    }

    private static void menuGiangVien() {
        boolean gvRunning = true;
        while (gvRunning) {
            System.out.println("\n================ MENU GIẢNG VIÊN ================");
            System.out.println("1. Danh sách lớp để nhập điểm");
            System.out.println("2. Quản lý điểm số");
            System.out.println("3. Báo cáo chất lượng đào tạo");
            System.out.println("0. Đăng xuất");
            System.out.println("==============================================");
            System.out.print("Chọn chức năng (0-3): ");
            String choice = scanner.nextLine();

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
                    System.out.println("=> Đã đăng xuất!");
                    break;
                default:
                    System.out.println("Lựa chọn chưa đúng!");
            }
        }
    }

    private static void menuAdmin() {
        boolean adminRunning = true;
        while (adminRunning) {
            System.out.println("\n================= MENU ADMIN =================");
            System.out.println("1. Quản lý cấu trúc đào tạo (Khoa, Ngành, Lớp)");
            System.out.println("2. Quản lý khung chương trình (Môn học)");
            System.out.println("3. Cấu hình hệ thống học kỳ");
            System.out.println("4. Quản lý hồ sơ nhân sự (Giảng viên, Sinh viên)");
            System.out.println("5. Thống kê sinh viên nợ học phí");
            System.out.println("6. Báo cáo doanh thu học phí");
            System.out.println("0. Đăng xuất");
            System.out.println("==============================================");
            System.out.print("Chọn chức năng (0-6): ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("\n--- [1] Quản lý Khoa ---");
                    daoTaoService.themKhoaMoi(scanner);
                    break;
                case "2":
                    System.out.println("\n--- [2] Quản lý Môn Học ---");
                    daoTaoService.themMonHoc(scanner);
                    break;
                case "3":
                    System.out.println("\n--- [3] Quản lý Hệ thống Học Kỳ ---");
                    daoTaoService.themHocKy(scanner);
                    break;
                case "4":
                    System.out.println("\n--- [4] Quản lý Hồ sơ Nhân sự ---");
                    System.out.println("1 - Thêm Giảng Viên | 2 - Thêm Sinh Viên");
                    String nChoice = scanner.nextLine();
                    if (nChoice.equals("1")) nhanSuService.themGiangVien(scanner);
                    else if (nChoice.equals("2")) nhanSuService.themSinhVien(scanner);
                    break;
                case "5":
                    System.out.println("\n--- [5] Thống kê SV Nợ Học Phí (Chức năng TV B) ---");
                    break;
                case "6":
                    System.out.println("\n--- [6] Báo cáo Doanh thu (Chức năng TV B) ---");
                    break;
                case "0":
                    loggedInRole = null;
                    loggedInUsername = null;
                    adminRunning = false;
                    System.out.println("=> Đã đăng xuất!");
                    break;
                default:
                    System.out.println("Lựa chọn chưa đúng!");
            }
        }
    }
}
