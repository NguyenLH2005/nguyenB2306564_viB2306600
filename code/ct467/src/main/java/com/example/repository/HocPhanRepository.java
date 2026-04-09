package com.example.repository;

import com.example.db.DBConnection;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

public class HocPhanRepository {

    // Task 9
    public void xuatDanhSachLHP(String maGV, String maHocKy) {
        String sql = "{CALL sp_DanhSachLHP_GiangVien(?, ?)}";
        System.out.printf("%-15s %-30s %-10s %-10s %-15s\n", "Mã LHP", "Tên Môn", "Số TC", "Tối Đa", "Đã Đăng Ký");
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
                    System.out.println("Không tìm thấy lớp học phần nào do bạn giảng dạy trong học kỳ này.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
