package com.example.service;

import com.example.repository.SinhVienRepository;
import java.util.Scanner;

public class SinhVienService {
    private SinhVienRepository repository;

    public SinhVienService() {
        this.repository = new SinhVienRepository();
    }

    // Task 4
    public void xemBangDiemVaGPA(String mssv) {
        System.out.println("\nBẢNG ĐIỂM CHI TIẾT");
        repository.xemBangDiem(mssv);

        float gpa = repository.tinhGPA(mssv);
        if (gpa != -1) {
            System.out.printf("\n=> ĐIỂM TRUNG BÌNH TÍCH LŨY (GPA): %.2f\n", gpa);
        }
    }

    // Task 6
    public void traCuuCongNoCaNhan(Scanner scanner, String mssv) {
        System.out.print("Nhập mã Học kỳ cần tra cứu công nợ (VD: HK1_2425): ");
        String maHocKy = scanner.nextLine();

        float congNo = repository.traCuuCongNo(mssv, maHocKy);
        if (congNo != -1) {
            System.out.printf("=> TỔNG SỐ TIỀN CÒN NỢ TRONG HỌC KỲ LÀ: %.0f VNĐ\n", congNo);
        } else {
            System.out.println("=> Không tìm thấy thông tin công nợ hoặc có lỗi xảy ra.");
        }
    }
}
