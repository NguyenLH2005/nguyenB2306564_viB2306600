package com.example.repository;

import com.example.db.DBConnection;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class SinhVienRepository {
    // Task 4: Tính GPA
    public float tinhGPA(String mssv) {
        String sql = "{? = CALL func_TinhGPA(?)}";
        try (Connection conn = DBConnection.getConnection();
                CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.registerOutParameter(1, java.sql.Types.FLOAT);
            cstmt.setString(2, mssv);
            cstmt.execute();
            return cstmt.getFloat(1);
        } catch (Exception e) {
            System.out.println("Lỗi tính GPA: " + e.getMessage());
            return -1;
        }
    }

    // Task 4: Xem bản điểm chi tiết (Bonus query)
    public void xemBangDiem(String mssv) {
        String sql = "SELECT d.MaLHP, mh.TenMon, mh.SoTinChi, d.Diem " +
                "FROM DIEM d " +
                "JOIN LOP_HOC_PHAN lhp ON d.MaLHP = lhp.MaLHP " +
                "JOIN MON_HOC mh ON lhp.MaMon = mh.MaMon " +
                "WHERE d.MSSV = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, mssv);
            try (ResultSet rs = pstmt.executeQuery()) {
                System.out.printf("%-20s %-30s %-10s %-10s\n", "Mã LHP", "Tên Môn", "Số TC", "Điểm");
                System.out.println("--------------------------------------------------------------------------------");
                while (rs.next()) {
                    float diem = rs.getFloat("Diem");
                    String diemStr = rs.wasNull() ? "Chưa có" : String.valueOf(diem);
                    System.out.printf("%-20s %-30s %-10d %-10s\n",
                            rs.getString("MaLHP"),
                            rs.getString("TenMon"),
                            rs.getInt("SoTinChi"),
                            diemStr);
                }
            }
        } catch (Exception e) {
            System.out.println("Lỗi xem bảng điểm: " + e.getMessage());
        }
    }

    // Task 6: Tra cứu công nợ cá nhân
    public float traCuuCongNo(String mssv, String maHocKy) {
        String sql = "{? = CALL func_TinhTienNo(?, ?)}";
        try (Connection conn = DBConnection.getConnection();
                CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.registerOutParameter(1, java.sql.Types.FLOAT);
            cstmt.setString(2, mssv);
            cstmt.setString(3, maHocKy);
            cstmt.execute();
            return cstmt.getFloat(1);
        } catch (Exception e) {
            System.out.println("Lỗi tra cứu công nợ: " + e.getMessage());
            return -1;
        }
    }
}
