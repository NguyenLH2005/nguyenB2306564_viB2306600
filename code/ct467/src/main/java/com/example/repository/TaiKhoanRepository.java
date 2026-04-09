package com.example.repository;

import com.example.db.DBConnection;
import com.example.model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TaiKhoanRepository {
    
    public Account authenticate(String username, String password) {
        String sql = "SELECT * FROM ACCOUNT WHERE Username = ? AND Password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Account acc = new Account();
                    acc.setUsername(rs.getString("Username"));
                    acc.setPassword(rs.getString("Password"));
                    acc.setMaLoaiTK(rs.getString("MaLoaiTK"));
                    return acc;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
