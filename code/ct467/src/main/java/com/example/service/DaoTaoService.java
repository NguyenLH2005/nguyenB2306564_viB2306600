package com.example.service;

import com.example.model.HocKy;
import com.example.model.Khoa;
import com.example.model.MonHoc;
import com.example.repository.DaoTaoRepository;

import java.util.Scanner;

public class DaoTaoService {
    private DaoTaoRepository repository;

    public DaoTaoService() {
        this.repository = new DaoTaoRepository();
    }

    public void themKhoaMoi(Scanner scanner) {
        System.out.print("Nhập mã khoa: ");
        String ma = scanner.nextLine();
        System.out.print("Nhập tên khoa: ");
        String ten = scanner.nextLine();
        
        Khoa khoa = new Khoa();
        khoa.setMaKhoa(ma);
        khoa.setTenKhoa(ten);
        
        if (repository.insertKhoa(khoa)) {
            System.out.println("=> Thêm khoa thành công!");
        } else {
            System.out.println("=> Thêm khoa thất bại!");
        }
    }

    public void themMonHoc(Scanner scanner) {
        System.out.print("Nhập mã môn: ");
        String ma = scanner.nextLine();
        System.out.print("Nhập tên môn: ");
        String ten = scanner.nextLine();
        System.out.print("Nhập số tín chỉ: ");
        int stc = Integer.parseInt(scanner.nextLine());
        System.out.print("Nhập loại môn (DC: Đại cương / CN: Chuyên ngành): ");
        String loai = scanner.nextLine();

        MonHoc mh = new MonHoc();
        mh.setMaMon(ma);
        mh.setTenMon(ten);
        mh.setSoTinChi(stc);
        mh.setMaLoaiMon(loai);
        
        if (repository.insertMonHoc(mh)) {
            System.out.println("=> Thêm môn học thành công!");
        } else {
            System.out.println("=> Thêm môn học thất bại!");
        }
    }

    public void themHocKy(Scanner scanner) {
        System.out.print("Nhập mã học kỳ (VD: HK1_2425): ");
        String ma = scanner.nextLine();
        System.out.print("Nhập tên học kỳ: ");
        String ten = scanner.nextLine();
        System.out.print("Nhập mã năm học (VD: 24-25): ");
        String namHoc = scanner.nextLine();

        HocKy hk = new HocKy();
        hk.setMaHocKy(ma);
        hk.setTenHocKy(ten);
        hk.setMaNamHoc(namHoc);
        hk.setTrangThai(true);

        if (repository.insertHocKy(hk)) {
            System.out.println("=> Thêm học kỳ mới thành công!");
        } else {
            System.out.println("=> Thêm học kỳ thất bại!");
        }
    }
}
