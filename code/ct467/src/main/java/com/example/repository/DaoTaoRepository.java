package com.example.repository;

import com.example.db.DBConnection;
import com.example.model.HocKy;
import com.example.model.Khoa;
import com.example.model.MonHoc;
import com.example.model.Nganh;
import com.example.model.Lop;
import com.example.model.DonGiaTinChi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DaoTaoRepository {

    // ================= KHOA =================
    public List<Khoa> getAllKhoa() {
        List<Khoa> list = new ArrayList<>();
        String sql = "SELECT * FROM KHOA";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                list.add(new Khoa(rs.getString("MaKhoa"), rs.getString("TenKhoa")));
            }
        } catch (Exception e) {
            System.out.println("Loi: " + e.getMessage());
        }
        return list;
    }

    public boolean insertKhoa(Khoa khoa) {
        String sql = "INSERT INTO KHOA(MaKhoa, TenKhoa) VALUES(?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, khoa.getMaKhoa());
            pstmt.setString(2, khoa.getTenKhoa());
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Loi: " + e.getMessage());
        }
        return false;
    }

    public boolean updateKhoa(Khoa khoa) {
        String sql = "UPDATE KHOA SET TenKhoa = ? WHERE MaKhoa = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, khoa.getTenKhoa());
            pstmt.setString(2, khoa.getMaKhoa());
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Loi: " + e.getMessage());
        }
        return false;
    }

    public boolean deleteKhoa(String maKhoa) {
        String sql = "DELETE FROM KHOA WHERE MaKhoa = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, maKhoa);
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Loi xoa khoa (Co the do rang buoc du lieu): " + e.getMessage());
        }
        return false;
    }

    // ================= NGANH =================
    public List<Nganh> getAllNganh() {
        List<Nganh> list = new ArrayList<>();
        String sql = "SELECT n.*, k.TenKhoa FROM NGANH n JOIN KHOA k ON n.MaKhoa = k.MaKhoa ORDER BY n.MaKhoa, n.TenNganh";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Nganh n = new Nganh(rs.getString("MaNganh"), rs.getString("TenNganh"), rs.getString("MaKhoa"));
                n.setTenKhoa(rs.getString("TenKhoa"));
                list.add(n);
            }
        } catch (Exception e) {
            System.out.println("Loi: " + e.getMessage());
        }
        return list;
    }

    public boolean insertNganh(Nganh nganh) {
        String sql = "INSERT INTO NGANH(MaNganh, TenNganh, MaKhoa) VALUES(?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nganh.getMaNganh());
            pstmt.setString(2, nganh.getTenNganh());
            pstmt.setString(3, nganh.getMaKhoa());
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Loi: " + e.getMessage());
        }
        return false;
    }

    public boolean updateNganh(Nganh nganh) {
        String sql = "UPDATE NGANH SET TenNganh = ?, MaKhoa = ? WHERE MaNganh = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nganh.getTenNganh());
            pstmt.setString(2, nganh.getMaKhoa());
            pstmt.setString(3, nganh.getMaNganh());
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Loi: " + e.getMessage());
        }
        return false;
    }

    public boolean deleteNganh(String maNganh) {
        String sql = "DELETE FROM NGANH WHERE MaNganh = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, maNganh);
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Loi xoa nganh: " + e.getMessage());
        }
        return false;
    }

    // ================= LOP =================
    public List<Lop> getAllLop() {
        List<Lop> list = new ArrayList<>();
        String sql = "SELECT l.*, n.TenNganh FROM LOP l JOIN NGANH n ON l.MaNganh = n.MaNganh ORDER BY l.MaNganh, l.TenLop";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Lop lop = new Lop(rs.getString("MaLop"), rs.getString("TenLop"), rs.getString("MaNganh"));
                lop.setTenNganh(rs.getString("TenNganh"));
                list.add(lop);
            }
        } catch (Exception e) {
            System.out.println("Loi: " + e.getMessage());
        }
        return list;
    }

    public boolean insertLop(Lop lop) {
        String sql = "INSERT INTO LOP(MaLop, TenLop, MaNganh) VALUES(?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, lop.getMaLop());
            pstmt.setString(2, lop.getTenLop());
            pstmt.setString(3, lop.getMaNganh());
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Loi: " + e.getMessage());
        }
        return false;
    }

    public boolean updateLop(Lop lop) {
        String sql = "UPDATE LOP SET TenLop = ?, MaNganh = ? WHERE MaLop = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, lop.getTenLop());
            pstmt.setString(2, lop.getMaNganh());
            pstmt.setString(3, lop.getMaLop());
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Loi: " + e.getMessage());
        }
        return false;
    }

    public boolean deleteLop(String maLop) {
        String sql = "DELETE FROM LOP WHERE MaLop = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, maLop);
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Loi xoa lop: " + e.getMessage());
        }
        return false;
    }

    // ================= MON HOC =================
    public List<MonHoc> getAllMonHoc() {
        List<MonHoc> list = new ArrayList<>();
        String sql = "SELECT * FROM MON_HOC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                MonHoc mh = new MonHoc();
                mh.setMaMon(rs.getString("MaMon"));
                mh.setTenMon(rs.getString("TenMon"));
                mh.setSoTinChi(rs.getInt("SoTinChi"));
                mh.setMaLoaiMon(rs.getString("MaLoaiMon"));
                list.add(mh);
            }
        } catch (Exception e) {
            System.out.println("Loi: " + e.getMessage());
        }
        return list;
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
            System.out.println("Loi: " + e.getMessage());
        }
        return false;
    }

    public boolean updateMonHoc(MonHoc monHoc) {
        String sql = "UPDATE MON_HOC SET TenMon = ?, SoTinChi = ?, MaLoaiMon = ? WHERE MaMon = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, monHoc.getTenMon());
            pstmt.setInt(2, monHoc.getSoTinChi());
            pstmt.setString(3, monHoc.getMaLoaiMon());
            pstmt.setString(4, monHoc.getMaMon());
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Loi: " + e.getMessage());
        }
        return false;
    }

    public boolean deleteMonHoc(String maMon) {
        String sql = "DELETE FROM MON_HOC WHERE MaMon = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, maMon);
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Loi xoa mon hoc: " + e.getMessage());
        }
        return false;
    }

    // ================= HOC KY =================
    public List<HocKy> getAllHocKy() {
        List<HocKy> list = new ArrayList<>();
        String sql = "SELECT * FROM HOC_KY";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                HocKy hk = new HocKy();
                hk.setMaHocKy(rs.getString("MaHocKy"));
                hk.setTenHocKy(rs.getString("TenHocKy"));
                hk.setMaNamHoc(rs.getString("MaNamHoc"));
                hk.setTrangThai(rs.getBoolean("TrangThai"));
                list.add(hk);
            }
        } catch (Exception e) {
            System.out.println("Loi: " + e.getMessage());
        }
        return list;
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
            System.out.println("Loi: " + e.getMessage());
        }
        return false;
    }

    public boolean updateTrangThaiHocKy(String maHocKy, boolean trangThai) {
        String sql = "UPDATE HOC_KY SET TrangThai = ? WHERE MaHocKy = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBoolean(1, trangThai);
            pstmt.setString(2, maHocKy);
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Loi: " + e.getMessage());
        }
        return false;
    }

    // ================= DON GIA TIN CHI =================
    public List<DonGiaTinChi> getAllDonGia() {
        List<DonGiaTinChi> list = new ArrayList<>();
        String sql = "SELECT * FROM DON_GIA_TIN_CHI";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                list.add(new DonGiaTinChi(rs.getString("MaNamHoc"), rs.getString("MaLoaiMon"), rs.getFloat("SoTienMotTinChi")));
            }
        } catch (Exception e) {
            System.out.println("Loi: " + e.getMessage());
        }
        return list;
    }

    public boolean insertOrUpdateDonGia(DonGiaTinChi donGia) {
        // Thu su dung INSERT ON DUPLICATE KEY UPDATE dac trung cua MySQL de tiet kiem dong code
        String sql = "INSERT INTO DON_GIA_TIN_CHI(MaNamHoc, MaLoaiMon, SoTienMotTinChi) VALUES(?, ?, ?) " +
                     "ON DUPLICATE KEY UPDATE SoTienMotTinChi = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, donGia.getMaNamHoc());
            pstmt.setString(2, donGia.getMaLoaiMon());
            pstmt.setFloat(3, donGia.getSoTienMotTinChi());
            pstmt.setFloat(4, donGia.getSoTienMotTinChi());
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Loi (chu y Ma Nam Hoc va Ma Loai Mon phai ton tai): " + e.getMessage());
        }
        return false;
    }
}
