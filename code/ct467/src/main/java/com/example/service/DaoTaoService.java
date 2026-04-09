package com.example.service;

import com.example.model.*;
import com.example.repository.DaoTaoRepository;

import java.util.List;
import java.util.Scanner;

public class DaoTaoService {
    private DaoTaoRepository repository;

    public DaoTaoService() {
        this.repository = new DaoTaoRepository();
    }

    // TASK 12: QUẢN LÝ CẤU TRÚC ĐÀO TẠO
    public void menuQuanLyCauTruc(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("\n--- QUAN LY CAU TRUC DAO TAO ---");
            System.out.println("1. Quan ly Khoa");
            System.out.println("2. Quan ly Nganh");
            System.out.println("3. Quan ly Lop");
            System.out.println("0. Quay lai");
            System.out.print("Chon chuc nang: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    menuQuanLyKhoa(scanner);
                    break;
                case "2":
                    menuQuanLyNganh(scanner);
                    break;
                case "3":
                    menuQuanLyLop(scanner);
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("Lua chon chua dung!");
            }
        }
    }

    private void menuQuanLyKhoa(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("\n--- QUAN LY KHOA ---");
            System.out.println("1. Xem danh sach Khoa");
            System.out.println("2. Them Khoa");
            System.out.println("3. Sua Ten Khoa");
            System.out.println("4. Xoa Khoa");
            System.out.println("0. Quay lai");
            System.out.print("Chon chuc nang: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    List<Khoa> khoas = repository.getAllKhoa();
                    System.out.printf("%-15s %-30s\n", "Ma Khoa", "Ten Khoa");
                    System.out.println("----------------------------------------");
                    for (Khoa k : khoas) {
                        System.out.printf("%-15s %-30s\n", k.getMaKhoa(), k.getTenKhoa());
                    }
                    break;
                case "2":
                    System.out.print("Nhap ma khoa moi: ");
                    String ma = scanner.nextLine();
                    System.out.print("Nhap ten khoa: ");
                    String ten = scanner.nextLine();
                    if (repository.insertKhoa(new Khoa(ma, ten)))
                        System.out.println("=> Them thanh cong!");
                    else
                        System.out.println("=> Them that bai!");
                    break;
                case "3":
                    System.out.print("Nhap ma khoa can sua: ");
                    String maSua = scanner.nextLine();
                    System.out.print("Nhap ten khoa moi: ");
                    String tenMoi = scanner.nextLine();
                    if (repository.updateKhoa(new Khoa(maSua, tenMoi)))
                        System.out.println("=> Sua thanh cong!");
                    else
                        System.out.println("=> Sua that bai (Khong tim thay ma)!");
                    break;
                case "4":
                    System.out.print("Nhap ma khoa can xoa: ");
                    String maXoa = scanner.nextLine();
                    if (repository.deleteKhoa(maXoa))
                        System.out.println("=> Xoa thanh cong!");
                    else
                        System.out.println("=> Xoa that bai!");
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("Lua chon chua dung!");
            }
        }
    }

    private void menuQuanLyNganh(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("\n--- QUAN LY NGANH ---");
            System.out.println("1. Xem danh sach Nganh");
            System.out.println("2. Them Nganh");
            System.out.println("3. Sua Nganh");
            System.out.println("4. Xoa Nganh");
            System.out.println("0. Quay lai");
            System.out.print("Chon chuc nang: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    List<Nganh> nganhs = repository.getAllNganh();
                    System.out.printf("%-15s %-30s %-15s\n", "Ma Nganh", "Ten Nganh", "Thuoc Khoa");
                    System.out.println("------------------------------------------------------------");
                    for (Nganh n : nganhs) {
                        System.out.printf("%-15s %-30s %-15s\n", n.getMaNganh(), n.getTenNganh(), n.getMaKhoa());
                    }
                    break;
                case "2":
                    System.out.print("Nhap ma nganh: ");
                    String ma = scanner.nextLine();
                    System.out.print("Nhap ten nganh: ");
                    String ten = scanner.nextLine();
                    System.out.print("Nhap ma khoa chu quan: ");
                    String maKhoa = scanner.nextLine();
                    if (repository.insertNganh(new Nganh(ma, ten, maKhoa)))
                        System.out.println("=> Them thanh cong!");
                    else
                        System.out.println("=> Them that bai (Kiem tra lai ma khoa)!");
                    break;
                case "3":
                    System.out.print("Nhap ma nganh can sua: ");
                    String maSua = scanner.nextLine();
                    System.out.print("Nhap ten nganh moi: ");
                    String tenMoi = scanner.nextLine();
                    System.out.print("Nhap ma khoa doi moi (co the giu nguyen): ");
                    String maKhoaMoi = scanner.nextLine();
                    if (repository.updateNganh(new Nganh(maSua, tenMoi, maKhoaMoi)))
                        System.out.println("=> Sua thanh cong!");
                    else
                        System.out.println("=> Sua that bai!");
                    break;
                case "4":
                    System.out.print("Nhap ma nganh can xoa: ");
                    String maXoa = scanner.nextLine();
                    if (repository.deleteNganh(maXoa))
                        System.out.println("=> Xoa thanh cong!");
                    else
                        System.out.println("=> Xoa that bai!");
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("Lua chon chua dung!");
            }
        }
    }

    private void menuQuanLyLop(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("\n--- QUAN LY LOP HOC ---");
            System.out.println("1. Xem danh sach Lop");
            System.out.println("2. Them Lop");
            System.out.println("3. Sua Lop");
            System.out.println("4. Xoa Lop");
            System.out.println("0. Quay lai");
            System.out.print("Chon chuc nang: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    List<Lop> lops = repository.getAllLop();
                    System.out.printf("%-15s %-30s %-15s\n", "Ma Lop", "Ten Lop", "Thuoc Nganh");
                    System.out.println("------------------------------------------------------------");
                    for (Lop l : lops) {
                        System.out.printf("%-15s %-30s %-15s\n", l.getMaLop(), l.getTenLop(), l.getMaNganh());
                    }
                    break;
                case "2":
                    System.out.print("Nhap ma lop: ");
                    String ma = scanner.nextLine();
                    System.out.print("Nhap ten lop: ");
                    String ten = scanner.nextLine();
                    System.out.print("Nhap ma nganh quan ly: ");
                    String maNganh = scanner.nextLine();
                    if (repository.insertLop(new Lop(ma, ten, maNganh)))
                        System.out.println("=> Them thanh cong!");
                    else
                        System.out.println("=> Them that bai (Kiem tra lai ma nganh)!");
                    break;
                case "3":
                    System.out.print("Nhap ma lop can sua: ");
                    String maSua = scanner.nextLine();
                    System.out.print("Nhap ten lop moi: ");
                    String tenMoi = scanner.nextLine();
                    System.out.print("Nhap ma nganh quan ly moi (co the giu nguyen): ");
                    String maNganhMoi = scanner.nextLine();
                    if (repository.updateLop(new Lop(maSua, tenMoi, maNganhMoi)))
                        System.out.println("=> Sua thanh cong!");
                    else
                        System.out.println("=> Sua that bai!");
                    break;
                case "4":
                    System.out.print("Nhap ma lop can xoa: ");
                    String maXoa = scanner.nextLine();
                    if (repository.deleteLop(maXoa))
                        System.out.println("=> Xoa thanh cong!");
                    else
                        System.out.println("=> Xoa that bai!");
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("Lua chon chua dung!");
            }
        }
    }

    // TASK 14: QUẢN LÝ KHUNG CHƯƠNG TRÌNH (MÔN HỌC)
    public void menuQuanLyMonHoc(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("\n--- QUAN LY MON HOC ---");
            System.out.println("1. Xem danh sach Mon hoc");
            System.out.println("2. Them Mon hoc");
            System.out.println("3. Sua thong tin Mon hoc");
            System.out.println("4. Xoa Mon hoc");
            System.out.println("0. Quay lai");
            System.out.print("Chon chuc nang: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    List<MonHoc> mhList = repository.getAllMonHoc();
                    System.out.printf("%-15s %-30s %-10s %-20s\n", "Ma Mon", "Ten Mon", "So TC",
                            "Loai Mon (DC/CN/...)");
                    System.out.println(
                            "--------------------------------------------------------------------------------");
                    for (MonHoc mh : mhList) {
                        System.out.printf("%-15s %-30s %-10d %-20s\n", mh.getMaMon(), mh.getTenMon(), mh.getSoTinChi(),
                                mh.getMaLoaiMon());
                    }
                    break;
                case "2":
                    System.out.print("Nhap ma mon: ");
                    String ma = scanner.nextLine();
                    System.out.print("Nhap ten mon: ");
                    String ten = scanner.nextLine();
                    System.out.print("Nhap so tin chi: ");
                    int stc = Integer.parseInt(scanner.nextLine());
                    System.out.print("Nhap ma loai mon (VD: DC - Dai cuong, CN - Chuyen nganh): ");
                    String loai = scanner.nextLine();
                    if (repository.insertMonHoc(new MonHoc(ma, ten, stc, loai)))
                        System.out.println("=> Them thanh cong!");
                    else
                        System.out.println("=> Them that bai!");
                    break;
                case "3":
                    System.out.print("Nhap ma mon can sua: ");
                    String maSua = scanner.nextLine();
                    System.out.print("Nhap ten mon moi: ");
                    String tenMoi = scanner.nextLine();
                    System.out.print("Nhap so tin chi moi: ");
                    int stcMoi = Integer.parseInt(scanner.nextLine());
                    System.out.print("Nhap ma loai mon moi: ");
                    String loaiMoi = scanner.nextLine();
                    if (repository.updateMonHoc(new MonHoc(maSua, tenMoi, stcMoi, loaiMoi)))
                        System.out.println("=> Sua thanh cong!");
                    else
                        System.out.println("=> Sua that bai!");
                    break;
                case "4":
                    System.out.print("Nhap ma mon can xoa: ");
                    String maXoa = scanner.nextLine();
                    if (repository.deleteMonHoc(maXoa))
                        System.out.println("=> Xoa thanh cong!");
                    else
                        System.out.println("=> Xoa that bai!");
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("Lua chon chua dung!");
            }
        }
    }

    // ================== TASK 15: CẤU HÌNH HỆ THỐNG HỌC KỲ & ĐƠN GIÁ
    // ==================
    public void menuQuanLyHocKy(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("\n--- CAU HINH HOC KY & DON GIA ---");
            System.out.println("1. Xem danh sach Hoc Ky");
            System.out.println("2. Them Hoc Ky moi");
            System.out.println("3. Dong / Mo Hoc Ky (Sua trang thai)");
            System.out.println("4. Xem bang don gia tin chi");
            System.out.println("5. Cap nhat Don gia tin chi (Cho tung loai mon)");
            System.out.println("0. Quay lai");
            System.out.print("Chon chuc nang: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    List<HocKy> hkList = repository.getAllHocKy();
                    System.out.printf("%-15s %-25s %-15s %-15s\n", "Ma Hoc Ky", "Ten Hoc Ky", "Nam Hoc", "Trang Thai");
                    System.out.println("-------------------------------------------------------------------------");
                    for (HocKy hk : hkList) {
                        System.out.printf("%-15s %-25s %-15s %-15s\n", hk.getMaHocKy(), hk.getTenHocKy(),
                                hk.getMaNamHoc(), hk.getTrangThai() ? "Dang mo (1)" : "Da khoa (0)");
                    }
                    break;
                case "2":
                    System.out.print("Nhap ma Hoc ky (VD: HK1_2425): ");
                    String ma = scanner.nextLine();
                    System.out.print("Nhap ten Hoc ky: ");
                    String ten = scanner.nextLine();
                    System.out.print("Nhap ma Nam hoc (VD: 24-25): ");
                    String namHoc = scanner.nextLine();
                    if (repository.insertHocKy(new HocKy(ma, ten, namHoc, true)))
                        System.out.println("=> Them thanh cong!");
                    else
                        System.out.println("=> Them that bai!");
                    break;
                case "3":
                    System.out.print("Nhap ma Hoc ky can doi trang thai: ");
                    String maDong = scanner.nextLine();
                    System.out.print("Chon trang thai (1: Mo, 0: Khoa): ");
                    boolean trangThai = scanner.nextLine().equals("1");
                    if (repository.updateTrangThaiHocKy(maDong, trangThai))
                        System.out.println("=> Cap nhat trang thai thanh cong!");
                    else
                        System.out.println("=> Cap nhat that bai!");
                    break;
                case "4":
                    List<DonGiaTinChi> dgList = repository.getAllDonGia();
                    System.out.printf("%-15s %-15s %-20s\n", "Nam Hoc", "Loai Mon", "Muc gia / 1 Tin chi");
                    System.out.println("------------------------------------------------------");
                    for (DonGiaTinChi dg : dgList) {
                        System.out.printf("%-15s %-15s %-20.0f\n", dg.getMaNamHoc(), dg.getMaLoaiMon(),
                                dg.getSoTienMotTinChi());
                    }
                    break;
                case "5":
                    System.out.print("Nhap ma Nam hoc can ap dung don gia (VD: 24-25): ");
                    String nam = scanner.nextLine();
                    System.out.print("Nhap ma Loai mon hoc can thay doi don gia (VD: DC, CN, co ban, ...): ");
                    String loaiMon = scanner.nextLine();
                    System.out.print("Nhap so tien tren 1 tin chi (VD: 450000): ");
                    float mucGia = Float.parseFloat(scanner.nextLine());
                    if (repository.insertOrUpdateDonGia(new DonGiaTinChi(nam, loaiMon, mucGia)))
                        System.out.println("=> Cap nhat don gia thanh cong!");
                    else
                        System.out.println("=> Cap nhat that bai!");
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("Lua chon chua dung!");
            }
        }
    }
}
