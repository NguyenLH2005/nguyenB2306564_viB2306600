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

}
