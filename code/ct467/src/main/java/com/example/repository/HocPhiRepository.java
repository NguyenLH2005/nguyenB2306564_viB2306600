package com.example.repository;

import com.example.db.DBConnection;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class HocPhiRepository {
    // Task 3: Đóng học phí
    public boolean dongHocPhi(String maHocPhi, float soTien, String phuongThuc) {
        String sql = "{CALL sp_DongTienHocPhi(?, ?, ?)}";
        try (Connection conn = DBConnection.getConnection();
                CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.setString(1, maHocPhi);
            cstmt.setFloat(2, soTien);
            cstmt.setString(3, phuongThuc);
            cstmt.execute();
            return true;
        } catch (Exception e) {
            System.out.println("Loi dong hoc phi: " + e.getMessage());
            return false;
        }
    }

    // Task 7: Thống kê sinh viên nợ học phí
    public void thongKeSinhVienNo(String maHocKy) {
        String sql = "{CALL sp_ThongKeSVNoHocPhi(?)}";
        try (Connection conn = DBConnection.getConnection();
                CallableStatement cstmt = conn.prepareCall(sql)) {

            cstmt.setString(1, maHocKy);

            try (ResultSet rs = cstmt.executeQuery()) {

                // Kiểm tra xem có dữ liệu không
                if (!rs.next()) {
                    System.out.println("Khong co sinh vien nao no hoc phi trong hoc ky " + maHocKy);
                    return;
                }

                // In tiêu đề (chỉ in khi có dữ liệu)
                System.out.printf("%-10s | %-25s | %-10s | %-20s | %-15s | %-15s | %-15s\n",
                        "MSSV", "Ho Ten", "Lop", "Nganh", "Tong Can Thu", "Da Dong", "Con No");
                System.out.println(
                        "-----------------------------------------------------------------------------------------------------------------------");

                // Dùng do-while để không bỏ sót sinh viên đầu tiên
                do {
                    System.out.printf("%-10s | %-25s | %-10s | %-20s | %-15.0f | %-15.0f | %-15.0f\n",
                            rs.getString("MSSV"),
                            rs.getString("HoTen"),
                            rs.getString("TenLop"),
                            rs.getString("TenNganh"),
                            rs.getFloat("TongTien"),
                            rs.getFloat("DaDong"),
                            rs.getFloat("TienNo"));
                } while (rs.next());

                System.out.println(
                        "-----------------------------------------------------------------------------------------------------------------------");
            }
        } catch (Exception e) {
            System.out.println("Loi thong ke: " + e.getMessage());
        }
    }

    // Task 8: Báo cáo doanh thu học phí
    public void baoCaoDoanhThuTheoKhoa(String maHocKy) {
        String sql = "{CALL sp_BaoCaoDoanhThuHocPhi(?)}";
        try (Connection conn = DBConnection.getConnection();
                CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.setString(1, maHocKy);
            try (ResultSet rs = cstmt.executeQuery()) {
                System.out.printf("%-10s %-40s %-40s %-20s %-20s\n", "Ma Khoa", "Ten Khoa", "Tong Phai Thu", "Thuc Thu",
                        "Con No");
                System.out.println(
                        "-----------------------------------------------------------------------------------------------------------");
                while (rs.next()) {
                    System.out.printf("%-10s %-40s %-40.0f %-20.0f %-20.0f\n",
                            rs.getString("MaKhoa"),
                            rs.getString("TenKhoa"),
                            rs.getFloat("TongPhaiThu"),
                            rs.getFloat("TongThucThu"),
                            rs.getFloat("TongConNo"));
                }
            }
        } catch (Exception e) {
            System.out.println("Loi bao cao doanh thu: " + e.getMessage());
        }
    }

    public boolean hienThiHocPhiConNo(String mssv) {
        // Truy vấn các khoản học phí của sinh viên mà trạng thái KHÔNG PHẢI là 'Đã hoàn
        // tất'
        // Bạn có thể sửa tên bảng 'hoc_phi' cho khớp với CSDL thực tế
        String sql = "SELECT MaHocKy, TongTien, DaDong, TrangThai FROM hoc_phi "
                + "WHERE MSSV = ? AND TrangThai != 'Da hoan tat'";

        boolean coNo = false; // Biến kiểm tra xem sinh viên có nợ không

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, mssv);

            try (ResultSet rs = pstmt.executeQuery()) {
                System.out.println("\n--- CAC KY CON NO HOC PHI ---");
                System.out.printf("%-15s | %-15s | %-15s | %-15s\n", "Ma Hoc Ky", "Tong Tien", "Da Dong", "Trang Thai");
                System.out.println("---------------------------------------------------------------------");

                while (rs.next()) {
                    coNo = true;
                    String maHK = rs.getString("MaHocKy");
                    float tongTien = rs.getFloat("TongTien");
                    float daDong = rs.getFloat("DaDong");
                    String trangThai = rs.getString("TrangThai");

                    // In ra dạng số nguyên (không có phần thập phân) cho dễ nhìn
                    System.out.printf("%-15s | %-15.0f | %-15.0f | %-15s\n", maHK, tongTien, daDong, trangThai);
                }

                if (!coNo) {
                    System.out.println("Ban khong co khoan no hoc phi nao.");
                }
                System.out.println("---------------------------------------------------------------------\n");
            }

        } catch (Exception e) {
            System.out.println("Loi hien thi hoc phi: " + e.getMessage());
        }

        return coNo; // Tra ve true neu co no, false neu khong
    }

    public float getTienConNo(String maHocPhi) {
        String sql = "SELECT TongTien, DaDong FROM hoc_phi WHERE MaHocPhi = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, maHocPhi);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    float tongTien = rs.getFloat("TongTien");
                    float daDong = rs.getFloat("DaDong");
                    return tongTien - daDong; // Trả về số tiền còn nợ
                }
            }
        } catch (Exception e) {
            System.out.println("Loi khi lay thong tin hoc phi: " + e.getMessage());
            e.printStackTrace();
        }

        // Trả về -1 nếu không tìm thấy mã học phí trong CSDL
        return -1f;
    }

}
