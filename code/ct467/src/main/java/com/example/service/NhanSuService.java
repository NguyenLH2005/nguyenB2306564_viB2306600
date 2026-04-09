package com.example.service;

import com.example.model.GiangVien;
import com.example.model.SinhVien;
import com.example.repository.NhanSuRepository;

import java.util.List;
import java.util.Scanner;

public class NhanSuService {
    private NhanSuRepository repository;

    public NhanSuService() {
        this.repository = new NhanSuRepository();
    }

    public void menuQuanLyNhanSu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("\n--- QUAN LY QUY MO NHAN SU ---");
            System.out.println("1. Quan ly Ho so Sinh vien");
            System.out.println("2. Quan ly Ho so Giang vien");
            System.out.println("0. Quay lai");
            System.out.print("Chon chuc nang: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1": menuSinhVien(scanner); break;
                case "2": menuGiangVien(scanner); break;
                case "0": running = false; break;
                default: System.out.println("Lua chon chua dung!");
            }
        }
    }

    private void menuSinhVien(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("\n--- QUAN LY SINH VIEN ---");
            System.out.println("1. Xem danh sach Sinh vien");
            System.out.println("2. Them Sinh vien moi");
            System.out.println("3. Sua thong tin Sinh vien");
            System.out.println("4. Xoa Sinh vien");
            System.out.println("0. Quay lai");
            System.out.print("Chon chuc nang: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    List<SinhVien> svList = repository.getAllSinhVien();
                    System.out.printf("%-15s %-30s %-15s\n", "MSSV", "Ho Ten", "Thuoc Lop");
                    System.out.println("------------------------------------------------------------");
                    for (SinhVien sv : svList) {
                        System.out.printf("%-15s %-30s %-15s\n", sv.getMssv(), sv.getHoTen(), sv.getMaLop());
                    }
                    break;
                case "2":
                    System.out.print("Nhap nam bat dau hoc (VD: 23, 24): ");
                    String namHoc = scanner.nextLine();
                    String newMssv = repository.generateMSSV(namHoc);
                    System.out.println("=> MSSV duoc cap: " + newMssv);
                    
                    System.out.print("Nhap ho ten sinh vien: ");
                    String hoTen = scanner.nextLine();
                    System.out.print("Nhap ma lop (VD: LOP01): ");
                    String maLop = scanner.nextLine();
                    
                    SinhVien svNew = new SinhVien();
                    svNew.setMssv(newMssv);
                    svNew.setHoTen(hoTen);
                    svNew.setMaLop(maLop);
                    if (repository.createSinhVien(svNew)) {
                        System.out.println("=> Them sinh vien thanh cong! MK dang nhap mac dinh: 123456");
                    } else {
                        System.out.println("=> Them that bai!");
                    }
                    break;
                case "3":
                    System.out.print("Nhap MSSV can sua: ");
                    String mssvSua = scanner.nextLine();
                    System.out.print("Nhap ho ten moi: ");
                    String hoTenMoi = scanner.nextLine();
                    System.out.print("Nhap ma lop moi (co the giu nguyen): ");
                    String maLopMoi = scanner.nextLine();
                    
                    SinhVien svUpdate = new SinhVien();
                    svUpdate.setMssv(mssvSua);
                    svUpdate.setHoTen(hoTenMoi);
                    svUpdate.setMaLop(maLopMoi);
                    if (repository.updateSinhVien(svUpdate)) System.out.println("=> Sua thanh cong!");
                    else System.out.println("=> Sua that bai!");
                    break;
                case "4":
                    System.out.print("Nhap MSSV can xoa: ");
                    String mssvXoa = scanner.nextLine();
                    if (repository.deleteSinhVien(mssvXoa)) System.out.println("=> Xoa thanh cong (Kem tai khoan)!");
                    else System.out.println("=> Xoa that bai!");
                    break;
                case "0": running = false; break;
                default: System.out.println("Lua chon chua dung!");
            }
        }
    }

    private void menuGiangVien(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("\n--- QUAN LY GIANG VIEN ---");
            System.out.println("1. Xem danh sach Giang vien");
            System.out.println("2. Them Giang vien moi");
            System.out.println("3. Sua thong tin Giang vien");
            System.out.println("4. Xoa Giang vien");
            System.out.println("0. Quay lai");
            System.out.print("Chon chuc nang: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    List<GiangVien> gvList = repository.getAllGiangVien();
                    System.out.printf("%-15s %-30s %-15s\n", "Ma GV", "Ho Ten", "Khoa");
                    System.out.println("------------------------------------------------------------");
                    for (GiangVien gv : gvList) {
                        System.out.printf("%-15s %-30s %-15s\n", gv.getMaGV(), gv.getHoTen(), gv.getMaKhoa());
                    }
                    break;
                case "2":
                    System.out.print("Nhap ma Khoa cua giang vien (VD: 01): ");
                    String maKhoa = scanner.nextLine();
                    String newMaGv = repository.generateMaGV(maKhoa);
                    System.out.println("=> Ma GV duoc cap tu dong: " + newMaGv);
                    
                    System.out.print("Nhap ho ten giang vien: ");
                    String hoTen = scanner.nextLine();
                    
                    GiangVien gvNew = new GiangVien();
                    gvNew.setMaGV(newMaGv);
                    gvNew.setHoTen(hoTen);
                    gvNew.setMaKhoa(maKhoa);
                    if (repository.createGiangVien(gvNew)) {
                        System.out.println("=> Them giang vien thanh cong! MK dang nhap: 123456");
                    } else {
                        System.out.println("=> Them that bai!");
                    }
                    break;
                case "3":
                    System.out.print("Nhap Ma GV can sua: ");
                    String maGvSua = scanner.nextLine();
                    System.out.print("Nhap ho ten moi: ");
                    String hoTenGvMoi = scanner.nextLine();
                    System.out.print("Nhap ma Khoa moi (co the giu nguyen): ");
                    String maKhoaMoi = scanner.nextLine();
                    
                    GiangVien gvUpdate = new GiangVien();
                    gvUpdate.setMaGV(maGvSua);
                    gvUpdate.setHoTen(hoTenGvMoi);
                    gvUpdate.setMaKhoa(maKhoaMoi);
                    if (repository.updateGiangVien(gvUpdate)) System.out.println("=> Sua thanh cong!");
                    else System.out.println("=> Sua that bai!");
                    break;
                case "4":
                    System.out.print("Nhap Ma GV can xoa: ");
                    String maGvXoa = scanner.nextLine();
                    if (repository.deleteGiangVien(maGvXoa)) System.out.println("=> Xoa thanh cong (Kem tai khoan)!");
                    else System.out.println("=> Xoa that bai!");
                    break;
                case "0": running = false; break;
                default: System.out.println("Lua chon chua dung!");
            }
        }
    }
}
