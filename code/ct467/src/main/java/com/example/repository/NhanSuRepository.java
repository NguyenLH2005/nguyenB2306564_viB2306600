package com.example.repository;

import com.example.db.DBConnection;
import com.example.model.GiangVien;
import com.example.model.SinhVien;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NhanSuRepository {

    // ================== SINH VIEN ==================

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
            System.out.println("Loi tao ma: " + e.getMessage());
        }
        return prefix + "00001";
    }

    public List<SinhVien> getAllSinhVien() {
        List<SinhVien> list = new ArrayList<>();
        String sql = "SELECT * FROM SINH_VIEN";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                SinhVien sv = new SinhVien();
                sv.setMssv(rs.getString("MSSV"));
                sv.setHoTen(rs.getString("HoTen"));
                if (rs.getDate("NgaySinh") != null) sv.setNgaySinh(rs.getDate("NgaySinh").toLocalDate());
                sv.setMaLop(rs.getString("MaLop"));
                list.add(sv);
            }
        } catch (Exception e) {
            System.out.println("Loi: " + e.getMessage());
        }
        return list;
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
                System.out.println("Loi tao sinh vien (Kiem tra ton tai ma Lop): " + ex.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) { try { conn.setAutoCommit(true); conn.close(); } catch (Exception ignored) {} }
        }
        return false;
    }

    public boolean updateSinhVien(SinhVien sv) {
        String sql = "UPDATE SINH_VIEN SET HoTen = ?, MaLop = ? WHERE MSSV = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, sv.getHoTen());
            pstmt.setString(2, sv.getMaLop());
            pstmt.setString(3, sv.getMssv());
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Loi: " + e.getMessage());
        }
        return false;
    }

    public boolean deleteSinhVien(String mssv) {
        String sqlSV = "DELETE FROM SINH_VIEN WHERE MSSV = ?";
        String sqlAcc = "DELETE FROM ACCOUNT WHERE Username = ?";
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            try (PreparedStatement pstSV = conn.prepareStatement(sqlSV);
                 PreparedStatement pstAcc = conn.prepareStatement(sqlAcc)) {
                
                pstSV.setString(1, mssv);
                int rowSV = pstSV.executeUpdate(); // Co the bi chan boi Khoa ngoai o bang DIEM, HOC_PHI
                
                if (rowSV > 0) {
                    pstAcc.setString(1, mssv);
                    pstAcc.executeUpdate();
                    conn.commit();
                    return true;
                }
            } catch (Exception ex) {
                conn.rollback();
                System.out.println("Loi xoa: Sinh vien nay dang co du lieu Diem hoac Hoc phi, khong the xoa!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) { try { conn.setAutoCommit(true); conn.close(); } catch (Exception ignored) {} }
        }
        return false;
    }


    // ================== GIANG VIEN ==================

    public String generateMaGV(String maKhoa) {
        String prefix = maKhoa;
        String sql = "SELECT MAX(MaGV) AS MaxID FROM GIANG_VIEN WHERE MaKhoa = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, maKhoa);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next() && rs.getString("MaxID") != null) {
                    String maxId = rs.getString("MaxID");
                    int num = Integer.parseInt(maxId.substring(2));
                    return String.format("%s%04d", prefix, num + 1);
                }
            }
        } catch (Exception e) {
            System.out.println("Loi tao ma gv: " + e.getMessage());
        }
        return prefix + "0001";
    }

    public List<GiangVien> getAllGiangVien() {
        List<GiangVien> list = new ArrayList<>();
        String sql = "SELECT * FROM GIANG_VIEN";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                GiangVien gv = new GiangVien();
                gv.setMaGV(rs.getString("MaGV"));
                gv.setHoTen(rs.getString("HoTen"));
                if (rs.getDate("NgaySinh") != null) gv.setNgaySinh(rs.getDate("NgaySinh").toLocalDate());
                gv.setMaKhoa(rs.getString("MaKhoa"));
                list.add(gv);
            }
        } catch (Exception e) {
            System.out.println("Loi: " + e.getMessage());
        }
        return list;
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
                System.out.println("Loi tao GV: " + ex.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) { try { conn.setAutoCommit(true); conn.close(); } catch (Exception ignored) {} }
        }
        return false;
    }

    public boolean updateGiangVien(GiangVien gv) {
        String sql = "UPDATE GIANG_VIEN SET HoTen = ?, MaKhoa = ? WHERE MaGV = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, gv.getHoTen());
            pstmt.setString(2, gv.getMaKhoa());
            pstmt.setString(3, gv.getMaGV());
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Loi: " + e.getMessage());
        }
        return false;
    }

    public boolean deleteGiangVien(String maGV) {
        String sqlGV = "DELETE FROM GIANG_VIEN WHERE MaGV = ?";
        String sqlAcc = "DELETE FROM ACCOUNT WHERE Username = ?";
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            try (PreparedStatement pstGV = conn.prepareStatement(sqlGV);
                 PreparedStatement pstAcc = conn.prepareStatement(sqlAcc)) {
                
                pstGV.setString(1, maGV);
                int rowGV = pstGV.executeUpdate(); // Co the bi chan boi khoa ngoai LOP_HOC_PHAN
                
                if (rowGV > 0) {
                    pstAcc.setString(1, maGV);
                    pstAcc.executeUpdate();
                    conn.commit();
                    return true;
                }
            } catch (Exception ex) {
                conn.rollback();
                System.out.println("Loi xoa: Giang vien nay dang quan ly Lop Hoc Phan, khong the xoa!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) { try { conn.setAutoCommit(true); conn.close(); } catch (Exception ignored) {} }
        }
        return false;
    }
}
