package com.example;

import java.sql.Connection;
import java.util.Scanner;
import com.example.db.DBConnection;

// Luồng hoạt dộng Main -> service -> Repository -> DB
// Main chạy console
// service là nơi chứa logic nghiệp vụ
// Repository là nơi chứa logic truy cập dữ liệu
// model đại diện bảng trong cơ sở dữ liệu (các thuộc tính, getter, setter, constructor, toString)

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static String loggedInRole = null; // Biến lưu loại tài khoản: SV, GV, ADMIN
    private static boolean running = true;

    public static void main(String[] args) {
        System.setProperty("file.encoding", "UTF-8");
        try {
            Connection conn = DBConnection.getConnection();
            System.out.println("Database connection successful!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Warning: DB connection error. System will continue to open the Menu interface.");
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
                        System.out.println("Permission error. The system will log out.");
                        loggedInRole = null;
                }
            }
        }
        scanner.close();
    }

    private static void hienThiDangNhap() {
        System.out.println("\n=============================================");
        System.out.println("       STUDENT MANAGEMENT SYSTEM LOGIN       ");
        System.out.println("=============================================");
        System.out.println("Test hint: use 'sv', 'gv', 'admin', pass '123'");
        System.out.print("Username (or type '0' to exit): ");
        String username = scanner.nextLine();

        if (username.equals("0")) {
            running = false;
            System.out.println("Program closed. Thank you for using the system!");
            return;
        }

        System.out.print("Password: ");
        String password = scanner.nextLine();

        // Chỗ này sẽ gọi TaiKhoanService(username, password) để quyết định role.
        // Tạm thời hardcode để test luồng:
        if (username.equals("sv") && password.equals("123")) {
            loggedInRole = "SV";
            System.out.println("=> Login successful! Welcome Student.");
        } else if (username.equals("gv") && password.equals("123")) {
            loggedInRole = "GV";
            System.out.println("=> Login successful! Welcome Teacher.");
        } else if (username.equals("admin") && password.equals("123")) {
            loggedInRole = "ADMIN";
            System.out.println("=> Login successful! Welcome Admin.");
        } else {
            System.out.println("=> Invalid username or password! Please try again.");
        }
    }

    // ================== CÁC MENU HOẠT ĐỘNG ĐỘC LẬP ================== //

    private static void menuSinhVien() {
        boolean svRunning = true;
        while (svRunning) {
            System.out.println("\n================ STUDENT MENU ================");
            System.out.println("1. Course Registration");
            System.out.println("2. Cancel Course Registration");
            System.out.println("3. Pay Tuition Fee");
            System.out.println("4. View Transcripts & Calculate GPA");
            System.out.println("5. Check Class Size");
            System.out.println("6. Look up Personal Debt");
            System.out.println("0. Logout");
            System.out.println("==============================================");
            System.out.print("Select function (0-6): ");
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
            System.out.println("\n================ TEACHER MENU ================");
            System.out.println("1. Class List for Grading");
            System.out.println("2. Grade Management");
            System.out.println("3. Training Quality Report");
            System.out.println("0. Logout");
            System.out.println("==============================================");
            System.out.print("Select function (0-3): ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("\n--- [1] Operate Class List (Call HocPhanService) ---");
                    break;
                case "2":
                    System.out.println("\n--- [2] Grade Management (Call DiemService) ---");
                    break;
                case "3":
                    System.out.println("\n--- [3] Training Quality Report (Call DiemService) ---");
                    break;
                case "0":
                    loggedInRole = null;
                    gvRunning = false;
                    System.out.println("=> Logged out!");
                    break;
                default:
                    System.out.println("Invalid selection, please select again!");
            }
        }
    }

    private static void menuAdmin() {
        boolean adminRunning = true;
        while (adminRunning) {
            System.out.println("\n================= ADMIN MENU =================");
            System.out.println("1. Manage Academic Structure (Faculty, Major, Class)");
            System.out.println("2. Manage Curriculum (Course)");
            System.out.println("3. Configure Semester System (Semester, Unit Price)");
            System.out.println("4. Manage Personnel Profiles (Teacher, Student)");
            System.out.println("5. Statistics on Students with Tuition Debt");
            System.out.println("6. Tuition Revenue Report");
            System.out.println("0. Logout");
            System.out.println("==============================================");
            System.out.print("Select function (0-6): ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("\n--- [1] Manage Academic Structure (Call DaoTaoService) ---");
                    break;
                case "2":
                    System.out.println("\n--- [2] Manage Curriculum (Call DaoTaoService) ---");
                    break;
                case "3":
                    System.out.println("\n--- [3] Configure Semester System (Call DaoTaoService) ---");
                    break;
                case "4":
                    System.out.println("\n--- [4] Manage Personnel Profiles (Call NhanSuService) ---");
                    break;
                case "5":
                    System.out.println("\n--- [5] Statistics on Students with Tuition Debt (Call HocPhiService) ---");
                    break;
                case "6":
                    System.out.println("\n--- [6] Tuition Revenue Report (Call HocPhiService) ---");
                    break;
                case "0":
                    loggedInRole = null;
                    adminRunning = false;
                    System.out.println("=> Logged out!");
                    break;
                default:
                    System.out.println("Invalid selection, please select again!");
            }
        }
    }
}
