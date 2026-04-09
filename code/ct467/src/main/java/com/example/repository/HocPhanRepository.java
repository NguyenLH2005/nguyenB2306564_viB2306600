package com.example.repository;

import com.example.db.DBConnection;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class HocPhanRepository {

    public void layTatCaHocPhan() {
        String sql = "SELECT * FROM lop_hoc_phan lhp JOIN mon_hoc mh ON lhp.MaMon = mh.MaMon join giang_vien gv on lhp.MaGV = gv.MaGV";
        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.printf("%-15s | %-25s | %-10s | %-10s\n",
                        rs.getString("MaLHP"),
                        rs.getString("TenMon"),
                        rs.getString("MaHocKy"),
                        rs.getString("HoTen"));
            }
        } catch (Exception e) {
            System.out.println("Loi lay danh sach hoc phan: " + e.getMessage());
        }
    }

    // Lấy học phần ở học kỳ gần đây nhất
    public void hienThiHPHienTai() {
        // Lấy học kỳ mới nhất
        String sql_HocKy = "SELECT MaHocKy FROM hoc_ky ORDER BY MaHocKy DESC LIMIT 1";

        // Cập nhật câu lệnh SQL: Gọi func_DemSiSo(MaLHP) và đặt tên cột kết quả là
        // 'SiSo'
        String sql_LHP = "SELECT lhp.MaLHP, mh.TenMon, lhp.MaHocKy, lhp.MaGV, lhp.SoLuongMax, "
                + "func_DemSiSo(lhp.MaLHP) AS SiSo "
                + "FROM lop_hoc_phan lhp "
                + "JOIN mon_hoc mh ON lhp.MaMon = mh.MaMon "
                + "WHERE lhp.MaHocKy = ?";

        try (Connection conn = DBConnection.getConnection()) {

            String maHK = null;

            // 1. Lấy học kỳ mới nhất
            try (Statement stmt = conn.createStatement();
                    ResultSet rsHK = stmt.executeQuery(sql_HocKy)) {

                if (rsHK.next()) {
                    maHK = rsHK.getString("MaHocKy");
                }
            }

            // Nếu không có học kỳ
            if (maHK == null) {
                System.out.println("Không tìm thấy học kỳ.");
                return;
            }

            System.out.println("\n--- DANH SACH LOP HOC PHAN HOC KY HIEN TAI (" + maHK + ") ---");
            // Thêm cột hiển thị Sĩ Số
            System.out.printf("%-15s | %-25s | %-10s | %-10s | %-15s | %-10s\n",
                    "Ma LHP", "Ten Mon", "Ma Hoc Ky", "Ma GV", "Si So (DK/Max)", "Con Trong");
            System.out
                    .println(
                            "-----------------------------------------------------------------------------------------------------");

            // 2. Lấy danh sách lớp học phần theo học kỳ
            try (PreparedStatement pstmt = conn.prepareStatement(sql_LHP)) {
                pstmt.setString(1, maHK);

                try (ResultSet rs = pstmt.executeQuery()) {
                    boolean hasData = false;

                    while (rs.next()) {
                        hasData = true;
                        String siSoStr = rs.getString("SiSo");
                        int conTrong = 0;
                        if (siSoStr != null && siSoStr.contains("/")) {
                            String[] parts = siSoStr.split("/");
                            try {
                                int daDangKy = Integer.parseInt(parts[0].trim());
                                int toiDa = Integer.parseInt(parts[1].trim());
                                conTrong = toiDa - daDangKy;
                            } catch (Exception e) {
                            }
                        }

                        System.out.printf("%-15s | %-25s | %-10s | %-10s | %-15s | %-10d\n",
                                rs.getString("MaLHP"),
                                rs.getString("TenMon"),
                                rs.getString("MaHocKy"),
                                rs.getString("MaGV"),
                                siSoStr,
                                conTrong); // <--- In ra số lượng chỗ còn trống
                    }

                    if (!hasData) {
                        System.out.println("Không có lớp học phần trong học kỳ này.");
                    }
                    System.out.println(
                            "-----------------------------------------------------------------------------------------------------\n");
                }
            }

        } catch (Exception e) {
            System.out.println("Lỗi tải danh sách lớp học phần: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Task 1
    public boolean dangKyHocPhan(String mssv, String maLHP) {
        String sql = "{CALL sp_DangKyHocPhan(?, ?)}";
        try (Connection conn = DBConnection.getConnection();
                CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.setString(1, mssv);
            cstmt.setString(2, maLHP);
            cstmt.execute();
            return true;
        } catch (Exception e) {
            System.out.println("Loi dang ky: " + e.getMessage());
            return false;
        }
    }

    // Task 2
    public boolean huyDangKyHocPhan(String mssv, String maLHP) {
        String sql = "{CALL sp_HuyDangKyHocPhan(?, ?)}";
        try (Connection conn = DBConnection.getConnection();
                CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.setString(1, mssv);
            cstmt.setString(2, maLHP);
            cstmt.execute();
            return true;
        } catch (Exception e) {
            System.out.println("Loi huy dang ky: " + e.getMessage());
            return false;
        }
    }

    // Task 5
    public String kiemTraSiSo(String maLHP) {
        String sql = "{? = CALL func_DemSiSo(?)}";
        try (Connection conn = DBConnection.getConnection();
                CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.registerOutParameter(1, java.sql.Types.VARCHAR);
            cstmt.setString(2, maLHP);
            cstmt.execute();
            return cstmt.getString(1);
        } catch (Exception e) {
            System.out.println("Loi ham dem si so: " + e.getMessage());
            return "Loi";
        }
    }

    // Task 9
    public void xuatDanhSachLHP(String maGV, String maHocKy) {
        String sql = "{CALL sp_DanhSachLHP_GiangVien(?, ?)}";
        System.out.printf("%-15s %-30s %-10s %-10s %-15s\n", "Ma LHP", "Ten Mon", "So TC", "Toi Da", "Da Dang Ky");
        System.out.println("--------------------------------------------------------------------------------");
        try (Connection conn = DBConnection.getConnection();
                CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.setString(1, maGV);
            cstmt.setString(2, maHocKy);

            try (ResultSet rs = cstmt.executeQuery()) {
                boolean hasData = false;
                while (rs.next()) {
                    hasData = true;
                    System.out.printf("%-15s %-30s %-10d %-10d %-15d\n",
                            rs.getString("MaLHP"),
                            rs.getString("TenMon"),
                            rs.getInt("SoTinChi"),
                            rs.getInt("SoLuongMax"),
                            rs.getInt("SL_DaDangKy"));
                }
                if (!hasData) {
                    System.out.println("Khong tim thay lop hoc phan nao do ban giang day trong hoc ky nay.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
