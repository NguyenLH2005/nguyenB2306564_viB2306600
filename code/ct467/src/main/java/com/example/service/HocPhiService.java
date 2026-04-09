package com.example.service;

// Chức năng:
// 3. Đóng tiền học phí
// 7. Thống kê SV nợ học phí
import com.example.repository.HocPhiRepository;
import java.util.Scanner;

public class HocPhiService {
    private HocPhiRepository repository;

    public HocPhiService() {
        this.repository = new HocPhiRepository();
    }

    // Task 3
    public void thucHienDongTien(Scanner scanner, String mssv) {
        System.out.print("Nhập mã học kỳ để thanh toán (VD: HK1_2425): ");
        String maHocKy = scanner.nextLine();

        // Tự sinh mã học phí theo công thức trong CSDL
        String maHocPhi = "HP_" + mssv + "_" + maHocKy;

        System.out.print("Nhập số tiền đóng: ");
        float soTien = 0;
        try {
            soTien = Float.parseFloat(scanner.nextLine());
        } catch (NumberFormatException ex) {
            System.out.println("Số tiền không hợp lệ.");
            return;
        }

        System.out.print("Nhập phương thức thanh toán (Tiền mặt / Chuyển khoản): ");
        String phuongThuc = scanner.nextLine();

        if (repository.dongHocPhi(maHocPhi, soTien, phuongThuc)) {
            System.out.println("=> Giao dịch đóng học phí thành công!");
        } else {
            System.out.println("=> Giao dịch thất bại.");
        }
    }

    // Task 7
    public void xemThongKeNoHocPhi(Scanner scanner) {
        System.out.print("Nhập mã Học kỳ cần thống kê (VD: HK1_2425): ");
        String maHocKy = scanner.nextLine();
        System.out.println("\nDANH SÁCH SINH VIÊN CÒN NỢ HỌC PHÍ - HỌC KỲ " + maHocKy);
        repository.thongKeSinhVienNo(maHocKy);
    }

    // Task 8
    public void xemBaoCaoDoanhThu(Scanner scanner) {
        System.out.print("Nhập mã Học kỳ cần báo cáo doanh thu (VD: HK1_2425): ");
        String maHocKy = scanner.nextLine();
        System.out.println("\nBÁO CÁO DOANH THU THEO KHOA - HỌC KỲ " + maHocKy);
        repository.baoCaoDoanhThuTheoKhoa(maHocKy);
    }
}
