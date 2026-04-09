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
        System.out.print("Nhap ma hoc ky de thanh toan (VD: HK1_2425): ");
        String maHocKy = scanner.nextLine();

        // Tự sinh mã học phí theo công thức trong CSDL
        String maHocPhi = "HP_" + mssv + "_" + maHocKy;

        System.out.print("Nhap so tien dong: ");
        float soTien = 0;
        try {
            soTien = Float.parseFloat(scanner.nextLine());
        } catch (NumberFormatException ex) {
            System.out.println("So tien khong hop le.");
            return;
        }

        System.out.print("Nhap phuong thuc thanh toan (Tien mat / Chuyen khoan): ");
        String phuongThuc = scanner.nextLine();

        if (repository.dongHocPhi(maHocPhi, soTien, phuongThuc)) {
            System.out.println("=> Giao dich dong hoc phi thanh cong!");
        } else {
            System.out.println("=> Giao dich that bai.");
        }
    }

    // Task 7
    public void xemThongKeNoHocPhi(Scanner scanner) {
        System.out.print("Nhap ma Hoc ky can thong ke (VD: HK1_2425): ");
        String maHocKy = scanner.nextLine();
        System.out.println("\nDANH SACH SINH VIEN CON NO HOC PHI - HOC KY " + maHocKy);
        repository.thongKeSinhVienNo(maHocKy);
    }

    // Task 8
    public void xemBaoCaoDoanhThu(Scanner scanner) {
        System.out.print("Nhap ma Hoc ky can bao cao doanh thu (VD: HK1_2425): ");
        String maHocKy = scanner.nextLine();
        System.out.println("\nBAO CAO DOANH THU THEO KHOA - HOC KY " + maHocKy);
        repository.baoCaoDoanhThuTheoKhoa(maHocKy);
    }
}
