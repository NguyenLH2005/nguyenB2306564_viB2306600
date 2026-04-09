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
        System.out.println("\nBANG DIEM CHI TIET");
        repository.xemBangDiem(mssv);

        float gpa = repository.tinhGPA(mssv);
        if (gpa != -1) {
            System.out.printf("\n=> DIEM TRUNG BINH TICH LUY (GPA): %.2f\n", gpa);
        }
    }

    // Task 6
    public void traCuuCongNoCaNhan(Scanner scanner, String mssv) {
        System.out.print("Nhap ma Hoc ky can tra cuu cong no (VD: HK1_2425): ");
        String maHocKy = scanner.nextLine();

        float congNo = repository.traCuuCongNo(mssv, maHocKy);
        if (congNo != -1) {
            System.out.printf("=> TONG SO TIEN CON NO TRONG HOC KY LA: %.0f VND\n", congNo);
        } else {
            System.out.println("=> Khong tim thay thong tin cong no hoac co loi xay ra.");
        }
    }
}
