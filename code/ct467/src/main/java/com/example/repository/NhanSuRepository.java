package com.example.repository;

import com.example.db.DBConnection;
import com.example.model.GiangVien;
import com.example.model.SinhVien;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class NhanSuRepository {

    public String generateMSSV(String namNhapHoc) {
        String prefix = "B" + namNhapHoc;
        String sql = "SELECT MAX(MSSV) AS MaxID FROM SINH_VIEN WHERE MSSV LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, prefix + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next() && rs.getString("MaxID") != null) {
                    String maxId = rs.getString("MaxID");
                    int num = Integer.parseInt(maxId.substring(3));
                    return String.format("%s%05d", prefix, num + 1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prefix + "00001";
    }

    public String generateMaGV(String maKhoa) {
        // Assume maKhoa is a 2-digit string like "01"
        String prefix = maKhoa;
        String sql = "SELECT MAX(MaGV) AS MaxID FROM GIANG_VIEN WHERE MaKhoa = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, maKhoa);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next() && rs.getString("MaxID") != null) {
                    String maxId = rs.getString("MaxID");
                    // maGV has format: 2-digit khoa + 4-digit sequence
                    int num = Integer.parseInt(maxId.substring(2));
                    return String.format("%s%04d", prefix, num + 1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prefix + "0001";
    }

    public boolean createSinhVien(SinhVien sv) {
        String sqlSV = "INSERT INTO SINH_VIEN(MSSV, HoTen, NgaySinh, MaLop) VALUES(?, ?, ?, ?)";
        String sqlAcc = "INSERT INTO ACCOUNT(Username, Password, MaLoaiTK) VALUES(?, '123456', 'SV')";
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement pstSV = conn.prepareStatement(sqlSV);
                 PreparedStatement pstAcc = conn.prepareStatement(sqlAcc)) {
                
                pstSV.setString(1, sv.getMssv());
                pstSV.setString(2, sv.getHoTen());
                pstSV.setObject(3, sv.getNgaySinh());
                pstSV.setString(4, sv.getMaLop());
                pstSV.executeUpdate();

                pstAcc.setString(1, sv.getMssv());
                pstAcc.executeUpdate();

                conn.commit();
                return true;
            } catch (Exception ex) {
                conn.rollback();
                ex.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) { 
                try { conn.setAutoCommit(true); conn.close(); } catch (Exception ignored) {} 
            }
        }
        return false;
    }

    public boolean createGiangVien(GiangVien gv) {
        String sqlGV = "INSERT INTO GIANG_VIEN(MaGV, HoTen, NgaySinh, MaKhoa) VALUES(?, ?, ?, ?)";
        String sqlAcc = "INSERT INTO ACCOUNT(Username, Password, MaLoaiTK) VALUES(?, '123456', 'GV')";
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement pstGV = conn.prepareStatement(sqlGV);
                 PreparedStatement pstAcc = conn.prepareStatement(sqlAcc)) {
                
                pstGV.setString(1, gv.getMaGV());
                pstGV.setString(2, gv.getHoTen());
                pstGV.setObject(3, gv.getNgaySinh());
                pstGV.setString(4, gv.getMaKhoa());
                pstGV.executeUpdate();

                pstAcc.setString(1, gv.getMaGV());
                pstAcc.executeUpdate();

                conn.commit();
                return true;
            } catch (Exception ex) {
                conn.rollback();
                ex.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) { 
                try { conn.setAutoCommit(true); conn.close(); } catch (Exception ignored) {} 
            }
        }
        return false;
    }
}
