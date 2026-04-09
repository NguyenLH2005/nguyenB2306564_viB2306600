package com.example.repository;

import com.example.db.DBConnection;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DiemRepository {

    // Task 10
    public void baoCaoChatLuongLHP(String maLHP) {
        String sql = "{CALL sp_BaoCaoChatLuong_LHP(?)}";
        System.out.println("---------------------------------------------------------------");
        System.out.printf("%-15s %-15s %-15s %-15s\n", "Tổng Sĩ Số", "Số Lượng Đậu", "Số Lượng Rớt", "Điểm Cao Nhất");
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
}
