package com.example.service;

import com.example.repository.HocPhanRepository;
import java.util.Scanner;

public class HocPhanService {
    private HocPhanRepository repository;

    public HocPhanService() {
        this.repository = new HocPhanRepository();
    }

    public void xemDanhSachLopGiangDay(Scanner scanner, String maGV) {
        System.out.print("Nhập mã học kỳ (VD: HK1_2425): ");
        String maHocKy = scanner.nextLine();
        
        System.out.println("\nDANH SÁCH LỚP HỌC PHẦN ĐANG GIẢNG DẠY");
        repository.xuatDanhSachLHP(maGV, maHocKy);
    }
}
