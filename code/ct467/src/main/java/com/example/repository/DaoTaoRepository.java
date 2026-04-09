package com.example.repository;

import com.example.db.DBConnection;
import com.example.model.HocKy;
import com.example.model.Khoa;
import com.example.model.MonHoc;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class DaoTaoRepository {

    public boolean insertKhoa(Khoa khoa) {
        String sql = "INSERT INTO KHOA(MaKhoa, TenKhoa) VALUES(?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, khoa.getMaKhoa());
            pstmt.setString(2, khoa.getTenKhoa());
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean insertMonHoc(MonHoc monHoc) {
        String sql = "INSERT INTO MON_HOC(MaMon, TenMon, SoTinChi, MaLoaiMon) VALUES(?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, monHoc.getMaMon());
            pstmt.setString(2, monHoc.getTenMon());
            pstmt.setInt(3, monHoc.getSoTinChi());
            pstmt.setString(4, monHoc.getMaLoaiMon());
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean insertHocKy(HocKy hk) {
        String sql = "INSERT INTO HOC_KY(MaHocKy, TenHocKy, MaNamHoc, TrangThai) VALUES(?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, hk.getMaHocKy());
            pstmt.setString(2, hk.getTenHocKy());
            pstmt.setString(3, hk.getMaNamHoc());
            pstmt.setBoolean(4, hk.getTrangThai());
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
