package com.example.repository;

import com.example.db.DBConnection;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

public class HocPhiRepository {
    // Task 3: Đóng học phí
    public boolean dongHocPhi(String maHocPhi, float soTien, String phuongThuc) {
        String sql = "{CALL sp_DongTienHocPhi(?, ?, ?)}";
        try (Connection conn = DBConnection.getConnection();
                CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.setString(1, maHocPhi);
            cstmt.setFloat(2, soTien);
            cstmt.setString(3, phuongThuc);
            cstmt.execute();
            return true;
        } catch (Exception e) {
            System.out.println("Loi dong hoc phi: " + e.getMessage());
            return false;
        }
    }

    // Task 7: Thống kê sinh viên nợ học phí
    public void thongKeSinhVienNo(String maHocKy) {
        String sql = "{CALL sp_ThongKeSVNoHocPhi(?)}";
        try (Connection conn = DBConnection.getConnection();
                CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.setString(1, maHocKy);
            try (ResultSet rs = cstmt.executeQuery()) {
                System.out.printf("%-10s %-25s %-15s %-20s %-15s %-15s %-15s\n", "MSSV", "Ho Ten", "Lop", "Nganh",
                        "Tong Can Thu", "Da Dong", "Con No");
                System.out.println(
                        "-----------------------------------------------------------------------------------------------------------------------");
                while (rs.next()) {
                    System.out.printf("%-10s %-25s %-15s %-20s %-15.0f %-15.0f %-15.0f\n",
                            rs.getString("MSSV"),
                            rs.getString("HoTen"),
                            rs.getString("TenLop"),
                            rs.getString("TenNganh"),
                            rs.getFloat("TongTien"),
                            rs.getFloat("DaDong"),
                            rs.getFloat("TienNo"));
                }
            }
        } catch (Exception e) {
            System.out.println("Loi thong ke: " + e.getMessage());
        }
    }

    // Task 8: Báo cáo doanh thu học phí
    public void baoCaoDoanhThuTheoKhoa(String maHocKy) {
        String sql = "{CALL sp_BaoCaoDoanhThuHocPhi(?)}";
        try (Connection conn = DBConnection.getConnection();
                CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.setString(1, maHocKy);
            try (ResultSet rs = cstmt.executeQuery()) {
                System.out.printf("%-10s %-30s %-20s %-20s %-20s\n", "Ma Khoa", "Ten Khoa", "Tong Phai Thu", "Thuc Thu",
                        "Con No");
                System.out.println(
                        "-----------------------------------------------------------------------------------------------------------");
                while (rs.next()) {
                    System.out.printf("%-10s %-30s %-20.0f %-20.0f %-20.0f\n",
                            rs.getString("MaKhoa"),
                            rs.getString("TenKhoa"),
                            rs.getFloat("TongPhaiThu"),
                            rs.getFloat("TongThucThu"),
                            rs.getFloat("TongConNo"));
                }
            }
        } catch (Exception e) {
            System.out.println("Loi bao cao doanh thu: " + e.getMessage());
        }
    }
}
