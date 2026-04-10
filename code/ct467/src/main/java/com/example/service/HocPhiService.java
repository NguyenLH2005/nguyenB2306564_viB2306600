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
        // 1. Gọi hàm hiển thị nợ học phí
        boolean coNo = repository.hienThiHocPhiConNo(mssv);

        // Nếu không có nợ thì không cho đóng và thoát luôn.
        if (!coNo) {
            System.out.println("=> Hien tai ban da hoan tat moi khoan hoc phi.");
            return;
        }

        System.out.print("Nhap ma hoc ky de thanh toan (VD: HK1_2425): ");
        String maHocKy = scanner.nextLine();

        // Tự sinh mã học phí theo công thức trong CSDL
        String maHocPhi = "HP_" + mssv + "_" + maHocKy;

        // 2. Lấy số tiền thực tế còn nợ của mã học phí này
        float tienConNo = repository.getTienConNo(maHocPhi);

        if (tienConNo < 0) {
            System.out.println("=> Khong tim thay thong tin no hoc phi cho ky nay. Vui long kiem tra lai ma!");
            return;
        } else if (tienConNo == 0) {
            System.out.println("=> Hoc ky nay ban da dong du hoc phi roi.");
            return;
        }

        System.out.printf("So tien con no hien tai la: %.0f VNĐ\n", tienConNo);
        System.out.print("Nhap so tien dong: ");
        float soTien = 0;
        try {
            soTien = Float.parseFloat(scanner.nextLine());
            if (soTien <= 0) {
                System.out.println("So tien phai lon hon 0.");
                return;
            }
        } catch (NumberFormatException ex) {
            System.out.println("So tien khong hop le.");
            return;
        }

        System.out.print("Nhap phuong thuc thanh toan (Tien mat / Chuyen khoan): ");
        String phuongThuc = scanner.nextLine();

        // 3. Xử lý logic tiền thừa
        float tienHoanLai = 0;
        float tienDongThucTe = soTien; // Số tiền sẽ ghi nhận vào CSDL

        // Nếu số tiền nhập vào LỚN HƠN tiền còn nợ
        if (soTien > tienConNo) {
            tienHoanLai = soTien - tienConNo;
            tienDongThucTe = tienConNo; // Chỉ ghi nhận đóng đủ phần nợ còn lại để CSDL không bị lố
        }

        // 4. Thực hiện đóng học phí với số tiền thực tế cần đóng
        if (repository.dongHocPhi(maHocPhi, tienDongThucTe, phuongThuc)) {
            System.out.println("=> Giao dich dong hoc phi thanh cong!");

            // In ra thông báo hoàn tiền nếu có tiền dư
            if (tienHoanLai > 0) {
                System.out.printf("=> Ban da dong du. He thong hoan lai so tien: %.0f VNĐ\n", tienHoanLai);
            }
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
