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

    public static void main(String[] args) {
        try {
            Connection conn = DBConnection.getConnection();
            System.out.println("Kết nối cơ sở dữ liệu thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Cảnh báo: Lỗi kết nối DB. Hệ thống tiếp tục mở giao diện Menu.");
        }

        boolean running = true;
        while (running) {
            System.out.println("\n=============================================");
            System.out.println("         HỆ THỐNG QUẢN LÝ SINH VIÊN          ");
            System.out.println("=============================================");
            System.out.println("1. Đăng ký học phần");
            System.out.println("2. Hủy đăng ký học phần");
            System.out.println("3. Đóng tiền học phí");
            System.out.println("4. Xem bảng điểm & Tính GPA");
            System.out.println("5. Kiểm tra sĩ số Lớp học phần");
            System.out.println("6. Tra cứu công nợ cá nhân");
            System.out.println("7. Thống kê SV nợ học phí");
            System.out.println("8. Báo cáo Doanh thu Học phí");
            System.out.println("9. Danh sách LHP để Nhập điểm");
            System.out.println("10. Báo cáo Chất lượng Đào tạo");
            System.out.println("11. Đăng nhập & Điều hướng");
            System.out.println("12. Quản lý Cấu trúc Đào tạo (Khoa, Ngành, Lớp)");
            System.out.println("13. Quản lý Hồ sơ Nhân sự (Giảng viên, Sinh viên)");
            System.out.println("14. Quản lý Khung chương trình (Môn học)");
            System.out.println("15. Cấu hình Hệ thống Học kỳ");
            System.out.println("16. Quản lý Nhập điểm");
            System.out.println("0. Thoát chương trình");
            System.out.println("=============================================");
            System.out.print("Vui lòng chọn chức năng (0-16): ");

            String choice = scanner.nextLine();

            // chỉ là khung viết chức năng vào sao khi thiết lập các lớp repo và service
            // tương ứng
            switch (choice) {
                case "1": {
                    System.out.println("\n--- [1] Đăng ký học phần ---");
                    break;
                }
                case "2": {
                    System.out.println("\n--- [2] Hủy đăng ký học phần ---");
                    break;
                }
                case "3": {
                    System.out.println("\n--- [3] Đóng tiền học phí ---");
                    break;
                }
                case "4": {
                    System.out.println("\n--- [4] Xem bảng điểm & Tính GPA ---");
                    break;
                }
                case "5": {
                    System.out.println("\n--- [5] Kiểm tra sĩ số Lớp học phần ---");
                    break;
                }
                case "6": {
                    System.out.println("\n--- [6] Tra cứu công nợ cá nhân ---");
                    break;
                }
                case "7": {
                    System.out.println("\n--- [7] Thống kê SV nợ học phí ---");
                    break;
                }
                case "8": {
                    System.out.println("\n--- [8] Báo cáo Doanh thu Học phí ---");
                    break;
                }
                case "9": {
                    System.out.println("\n--- [9] Danh sách LHP để Nhập điểm ---");
                    break;
                }
                case "10": {
                    System.out.println("\n--- [10] Báo cáo Chất lượng Đào tạo ---");
                    break;
                }
                case "11": {
                    System.out.println("\n--- [11] Đăng nhập & Điều hướng ---");
                    break;
                }
                case "12": {
                    System.out.println("\n--- [12] Quản lý Cấu trúc Đào tạo (Khoa, Ngành, Lớp) ---");
                    break;
                }
                case "13": {
                    System.out.println("\n--- [13] Quản lý Hồ sơ Nhân sự (Giảng viên, Sinh viên) ---");
                    break;
                }
                case "14": {
                    System.out.println("\n--- [14] Quản lý Khung chương trình (Môn học) ---");
                    break;
                }
                case "15": {
                    System.out.println("\n--- [15] Cấu hình Hệ thống Học kỳ ---");
                    break;
                }
                case "16": {
                    System.out.println("\n--- [16] Quản lý Nhập điểm ---");
                    break;
                }
                case "0":
                    running = false;
                    System.out.println("Đã đóng chương trình. Cảm ơn bạn đã sử dụng hệ thống!");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ, vui lòng chọn lại!");
            }
        }
        scanner.close();
    }
}
