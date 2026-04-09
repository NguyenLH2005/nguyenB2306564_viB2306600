-- ==============================================================================
-- 3. [TASK 1] THỦ TỤC & TRANSACTION: ĐĂNG KÝ HỌC PHẦN (THÀNH VIÊN B)
-- ==============================================================================
DROP PROCEDURE IF EXISTS sp_DangKyHocPhan$$

CREATE PROCEDURE sp_DangKyHocPhan(
    IN p_MSSV VARCHAR(10), 
    IN p_MaLHP VARCHAR(20)
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION 
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;

    START TRANSACTION;
    INSERT INTO DIEM (MSSV, MaLHP, Diem) VALUES (p_MSSV, p_MaLHP, NULL);
    COMMIT;
END$$

-- ==============================================================================
-- 4. [TASK 2] THỦ TỤC & TRANSACTION: HỦY ĐĂNG KÝ HỌC PHẦN (THÀNH VIÊN B)
-- ==============================================================================
DROP PROCEDURE IF EXISTS sp_HuyDangKyHocPhan$$

CREATE PROCEDURE sp_HuyDangKyHocPhan(
    IN p_MSSV VARCHAR(10), 
    IN p_MaLHP VARCHAR(20)
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION 
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;

    START TRANSACTION;
    DELETE FROM DIEM WHERE MSSV = p_MSSV AND MaLHP = p_MaLHP;
    COMMIT;
END$$

-- ==============================================================================
-- 5. [TASK 3] THỦ TỤC & TRANSACTION: ĐÓNG TIỀN HỌC PHÍ (THÀNH VIÊN B)
-- ==============================================================================
DROP PROCEDURE IF EXISTS sp_DongTienHocPhi$$

CREATE PROCEDURE sp_DongTienHocPhi(
    IN p_MaHocPhi VARCHAR(30), 
    IN p_SoTien FLOAT, 
    IN p_PhuongThuc VARCHAR(100)
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION 
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;

    START TRANSACTION;
    
    INSERT INTO PHIEU_THU (MaHocPhi, SoTienThu, PhuongThuc)
    VALUES (p_MaHocPhi, p_SoTien, p_PhuongThuc);
    
    UPDATE HOC_PHI 
    SET DaDong = DaDong + p_SoTien,
        TrangThai = CASE WHEN (DaDong + p_SoTien) >= TongTien THEN 'Đã hoàn tất' ELSE 'Chưa hoàn tất' END
    WHERE MaHocPhi = p_MaHocPhi;
    
    COMMIT;
END$$

-- ==============================================================================
-- 6. [TASK 4] STORED FUNCTION: TÍNH GPA (THÀNH VIÊN B)
-- ==============================================================================
DELIMITER $$

DROP FUNCTION IF EXISTS func_TinhGPA$$

CREATE FUNCTION func_TinhGPA(p_MSSV VARCHAR(10)) 
RETURNS FLOAT
DETERMINISTIC
BEGIN
    DECLARE v_GPA FLOAT DEFAULT 0;
    DECLARE v_TongTinChi INT DEFAULT 0;
    DECLARE v_TongDiem FLOAT DEFAULT 0;

    -- Sử dụng CASE WHEN để quy đổi điểm hệ 10 sang hệ 4 trước khi nhân với tín chỉ
    SELECT 
        SUM(
            (CASE 
                WHEN d.Diem < 4.0 THEN 0.0
                WHEN d.Diem >= 4.0 AND d.Diem < 5.0 THEN 1.0
                WHEN d.Diem >= 5.0 AND d.Diem < 6.0 THEN 1.5
                WHEN d.Diem >= 6.0 AND d.Diem < 6.5 THEN 2.0
                WHEN d.Diem >= 6.5 AND d.Diem < 7.0 THEN 2.5
                WHEN d.Diem >= 7.0 AND d.Diem < 8.0 THEN 3.0
                WHEN d.Diem >= 8.0 AND d.Diem < 9.0 THEN 3.5
                WHEN d.Diem >= 9.0 AND d.Diem <= 10.0 THEN 4.0
                ELSE 0.0 -- Trường hợp dữ liệu lỗi hoặc ngoài vùng
            END) * mh.SoTinChi
        ), 
        SUM(mh.SoTinChi)
        INTO v_TongDiem, v_TongTinChi
    FROM diem d
    JOIN lop_hoc_phan lhp ON d.MaLHP = lhp.MaLHP
    JOIN mon_hoc mh ON lhp.MaMon = mh.MaMon
    WHERE d.MSSV = p_MSSV AND d.Diem IS NOT NULL;

    -- Kiểm tra an toàn trước khi chia
    IF v_TongTinChi IS NOT NULL AND v_TongTinChi > 0 THEN
        SET v_GPA = ROUND(v_TongDiem / v_TongTinChi, 2);
    END IF;

    RETURN v_GPA;
END$$

DELIMITER ;

-- ==============================================================================
-- 7. [TASK 5] STORED FUNCTION: KIỂM TRA SĨ SỐ LỚP HỌC PHẦN (THÀNH VIÊN B)
-- ==============================================================================
DROP FUNCTION IF EXISTS func_DemSiSo$$

CREATE FUNCTION func_DemSiSo(p_MaLHP VARCHAR(20)) 
RETURNS VARCHAR(50)
DETERMINISTIC
BEGIN
    DECLARE v_DaDangKy INT DEFAULT 0;
    DECLARE v_ToiDa INT DEFAULT 0;
    
    SELECT COUNT(MSSV) INTO v_DaDangKy FROM DIEM WHERE MaLHP = p_MaLHP;
    SELECT SoLuongMax INTO v_ToiDa FROM LOP_HOC_PHAN WHERE MaLHP = p_MaLHP;
    
    RETURN CONCAT(v_DaDangKy, '/', v_ToiDa);
END$$

-- ==============================================================================
-- 8. [TASK 6] STORED FUNCTION: TRA CỨU CÔNG NỢ CÁ NHÂN (THÀNH VIÊN B)
-- ==============================================================================
DROP FUNCTION IF EXISTS func_TinhTienNo$$

CREATE FUNCTION func_TinhTienNo(p_MSSV VARCHAR(10), p_MaHocKy VARCHAR(20))
RETURNS FLOAT
DETERMINISTIC
BEGIN
    DECLARE v_TienNo FLOAT DEFAULT 0;
    DECLARE v_TongTien FLOAT DEFAULT 0;
    DECLARE v_DaDong FLOAT DEFAULT 0;
    
    SELECT TongTien, DaDong INTO v_TongTien, v_DaDong
    FROM HOC_PHI
    WHERE MSSV = p_MSSV AND MaHocKy = p_MaHocKy;
    
    IF v_TongTien IS NULL THEN
        SET v_TienNo = 0;
    ELSE
        SET v_TienNo = v_TongTien - v_DaDong;
        IF v_TienNo < 0 THEN
            SET v_TienNo = 0;
        END IF;
    END IF;
    
    RETURN v_TienNo;
END$$

-- ==============================================================================
-- 9. [TASK 7] THỦ TỤC: THỐNG KÊ SV NỢ HỌC PHÍ (THÀNH VIÊN B)
-- ==============================================================================
DROP PROCEDURE IF EXISTS sp_ThongKeSVNoHocPhi$$

CREATE PROCEDURE sp_ThongKeSVNoHocPhi(IN p_MaHocKy VARCHAR(20))
BEGIN
    SELECT 
        sv.MSSV, 
        sv.HoTen, 
        l.TenLop, 
        n.TenNganh, 
        hp.TongTien, 
        hp.DaDong, 
        (hp.TongTien - hp.DaDong) AS TienNo
    FROM HOC_PHI hp
    JOIN SINH_VIEN sv ON hp.MSSV = sv.MSSV
    JOIN LOP l ON sv.MaLop = l.MaLop
    JOIN NGANH n ON l.MaNganh = n.MaNganh
    WHERE hp.MaHocKy = p_MaHocKy 
      AND hp.TrangThai != 'Đã hoàn tất' 
      AND (hp.TongTien - hp.DaDong) > 0;
END$$

-- ==============================================================================
-- 10. [TASK 8] THỦ TỤC: BÁO CÁO DOANH THU HỌC PHÍ THEO KHOA (THÀNH VIÊN B)
-- ==============================================================================
DROP PROCEDURE IF EXISTS sp_BaoCaoDoanhThuHocPhi$$

CREATE PROCEDURE sp_BaoCaoDoanhThuHocPhi(IN p_MaHocKy VARCHAR(20))
BEGIN
    SELECT 
        k.MaKhoa,
        k.TenKhoa,
        COALESCE(SUM(hp.TongTien), 0) AS TongPhaiThu,
        COALESCE(SUM(hp.DaDong), 0) AS TongThucThu,
        COALESCE(SUM(hp.TongTien) - SUM(hp.DaDong), 0) AS TongConNo
    FROM KHOA k
    LEFT JOIN NGANH n ON k.MaKhoa = n.MaKhoa
    LEFT JOIN LOP l ON n.MaNganh = l.MaNganh
    LEFT JOIN SINH_VIEN sv ON l.MaLop = sv.MaLop
    LEFT JOIN HOC_PHI hp ON sv.MSSV = hp.MSSV AND hp.MaHocKy = p_MaHocKy
    GROUP BY k.MaKhoa, k.TenKhoa;
END$$

DELIMITER ;
