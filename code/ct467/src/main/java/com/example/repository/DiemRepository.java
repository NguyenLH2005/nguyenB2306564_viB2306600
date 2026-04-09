package com.example.repository;

import com.example.db.DBConnection;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DiemRepository {

    // Task 10
    public void baoCaoChatLuongLHP(String maLHP) {
        String sql = "{CALL sp_BaoCaoChatLuong_LHP(?)}";
        System.out.println("---------------------------------------------------------------");
        System.out.printf("%-15s %-15s %-15s %-15s\n", "Tong Si So", "So Luong Dau", "So Luong Rot", "Diem Cao Nhat");
        System.out.println("---------------------------------------------------------------");
        try (Connection conn = DBConnection.getConnection();
                CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.setString(1, maLHP);

            try (ResultSet rs = cstmt.executeQuery()) {
                if (rs.next()) {
                    System.out.printf("%-15d %-15d %-15d %-15.2f\n",
                            rs.getInt("TongSiSo"),
                            rs.getInt("SoLuongDau"),
                            rs.getInt("SoLuongRot"),
                            rs.getFloat("DiemCaoNhat"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Task 16
    public boolean nhapDiem(String mssv, String maLHP, float diem) {
        String sql = "UPDATE DIEM SET Diem = ? WHERE MSSV = ? AND MaLHP = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setFloat(1, diem);
            pstmt.setString(2, mssv);
            pstmt.setString(3, maLHP);
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void hienThiHocPhanDaDangKyHienTai(String mssv) {
        // Gộp chung mọi thứ vào 1 câu lệnh SQL duy nhất
        // Dùng truy vấn con (SELECT ... LIMIT 1) để lấy trực tiếp MaHocKy mới nhất gán
        // vào điều kiện WHERE
        String sql = "SELECT d.MaLHP, lhp.MaMon, mh.TenMon, d.Diem "
                + "FROM diem d "
                + "JOIN lop_hoc_phan lhp ON d.MaLHP = lhp.MaLHP "
                + "JOIN mon_hoc mh ON lhp.MaMon = mh.MaMon "
                + "WHERE lhp.MaHocKy = (SELECT MaHocKy FROM hoc_ky ORDER BY MaNamHoc DESC, MaHocKy DESC LIMIT 1) "
                + "AND d.MSSV = ?";

        // Chỉ cần 1 khối try-with-resources cho Connection và PreparedStatement
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Truyền tham số MSSV vào dấu "?"
            pstmt.setString(1, mssv);

            try (ResultSet rs = pstmt.executeQuery()) {

                System.out.println("--- DANH SACH HOC PHAN DA DANG KY (HOC KY NAY) ---");
                System.out.println("Sinh vien: " + mssv);
                System.out.println("---------------------------------------------------------------");
                System.out.printf("%-10s | %-10s | %-25s\n", "Ma LHP", "Ma Mon", "Ten Mon");
                System.out.println("---------------------------------------------------------------");

                boolean hasData = false;

                // Lặp qua kết quả trả về
                while (rs.next()) {
                    hasData = true;
                    String maLHP = rs.getString("MaLHP");
                    String maMon = rs.getString("MaMon");
                    String tenMon = rs.getString("TenMon");

                    System.out.printf("%-10s | %-10s | %-25s\n",
                            maLHP, maMon, tenMon);
                }

                if (!hasData) {
                    System.out.println("Sinh viên chưa đăng ký học phần nào trong học kỳ này.");
                }
                System.out.println("---------------------------------------------------------------");
            }

        } catch (Exception e) {
            System.out.println("Lỗi khi hiển thị danh sách đăng ký: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean kiemTraDaDangKy(String mssv, String maLHP) {
        // Lấy 1 dòng bất kỳ nếu tìm thấy MSSV và MaLHP trùng khớp trong bảng diem
        String sql = "SELECT 1 FROM diem WHERE MSSV = ? AND MaLHP = ? LIMIT 1";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, mssv);
            pstmt.setString(2, maLHP);

            try (ResultSet rs = pstmt.executeQuery()) {
                // Nếu rs.next() là true tức là đã có dữ liệu trong bảng (đã đăng ký)
                return rs.next();
            }

        } catch (Exception e) {
            System.out.println("Loi kiem tra dang ky: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public void xemDanhSachSinhVienVaDiem(String maLHP) {
        String sql = "SELECT s.MSSV, s.HoTen, d.Diem FROM DIEM d JOIN SINH_VIEN s ON d.MSSV = s.MSSV WHERE d.MaLHP = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, maLHP);
            try (ResultSet rs = pstmt.executeQuery()) {
                System.out.println("\nDANH SACH SINH VIEN - LOP HOC PHAN: " + maLHP);
                System.out.println("---------------------------------------------------------------");
                System.out.printf("%-15s %-30s %-10s\n", "MSSV", "Ho Ten", "Diem");
                System.out.println("---------------------------------------------------------------");
                boolean hasData = false;
                while (rs.next()) {
                    hasData = true;
                    Object diemObj = rs.getObject("Diem");
                    String diemStr = (diemObj == null) ? "Chua nhap" : String.format("%.2f", rs.getFloat("Diem"));
                    System.out.printf("%-15s %-30s %-10s\n", rs.getString("MSSV"), rs.getString("HoTen"), diemStr);
                }
                if (!hasData) {
                    System.out.println("Lop chua co sinh vien nao dang ky.");
                }
                System.out.println("---------------------------------------------------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
