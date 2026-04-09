package com.example.repository;

import com.example.db.DBConnection;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class HocPhanRepository {


    // Lấy học phần ở học kỳ gần đây nhất
    public void hienThiHPHienTai() {
        String sql_HocKy = "SELECT * FROM hoc_ky ORDER BY MaHocKy DESC LIMIT 1";
        String sql_LHP = "SELECT * FROM lop_hoc_phan JOIN mon_hoc ON lop_hoc_phan.MaMon = mon_hoc.MaMon WHERE MaHocKy = ?";

        try (Connection conn = DBConnection.getConnection()) {

            String MaHK = null;

            // Lấy học kỳ mới nhất
            try (Statement stmt = conn.createStatement();
                    ResultSet rsHK = stmt.executeQuery(sql_HocKy)) {

                if (rsHK.next()) {
                    MaHK = rsHK.getString("MaHocKy");
                }
            }

            // Nếu không có học kỳ
            if (MaHK == null) {
                System.out.println("Không tìm thấy học kỳ.");
                return;
            }

            // Lấy lớp học phần theo học kỳ
            try (PreparedStatement pstmt = conn.prepareStatement(sql_LHP)) {
                pstmt.setString(1, MaHK);

                try (ResultSet rs = pstmt.executeQuery()) {
                    boolean hasData = false;

                    while (rs.next()) {
                        hasData = true;
                        System.out.printf("%-15s %-30s %-10s %-10s %-15d\n",
                                rs.getString("MaLHP"),
                                rs.getString("TenMon"),
                                rs.getString("MaHocKy"),
                                rs.getString("MaGV"),
                                rs.getInt("SoLuongMax"));
                    }

                    if (!hasData) {
                        System.out.println("Không có lớp học phần trong học kỳ này.");
                    }
                }
            }

        } catch (Exception e) {
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
