DELIMITER $$
-- ==============================================================================
-- 1. [TASK 9] THỦ TỤC: DANH SÁCH LỚP HỌC PHẦN ĐỂ NHẬP ĐIỂM (NGHIỆP VỤ GIẢNG VIÊN)
-- Mô tả: Truyền mã Giảng Viên và mã Học Kỳ, xuất ra danh sách các lớp Giảng Viên đó đang dạy.
-- ==============================================================================

CREATE PROCEDURE sp_DanhSachLHP_GiangVien(
    IN p_MaGV VARCHAR(15), 
    IN p_MaHocKy VARCHAR(20)
)
BEGIN
    SELECT 
        lhp.MaLHP, 
        mh.TenMon, 
        mh.SoTinChi,
        lhp.SoLuongMax, 
        (SELECT COUNT(MSSV) FROM DIEM d WHERE d.MaLHP = lhp.MaLHP) AS SL_DaDangKy
    FROM LOP_HOC_PHAN lhp
    JOIN MON_HOC mh ON lhp.MaMon = mh.MaMon
    WHERE lhp.MaGV = p_MaGV AND lhp.MaHocKy = p_MaHocKy;
END$$

-- ==============================================================================
-- 2. [TASK 10] THỦ TỤC: BÁO CÁO CHẤT LƯỢNG ĐÀO TẠO
-- Mô tả: Truyền mã Lớp Học Phần, đếm tổng sinh viên (sĩ số), số rớt/đậu và điểm cao nhất.
-- ==============================================================================
CREATE PROCEDURE sp_BaoCaoChatLuong_LHP(
    IN p_MaLHP VARCHAR(20)
)
BEGIN
    SELECT 
        COUNT(MSSV) AS TongSiSo,
        SUM(CASE WHEN Diem >= 4.0 THEN 1 ELSE 0 END) AS SoLuongDau,
        SUM(CASE WHEN Diem < 4.0 AND Diem IS NOT NULL THEN 1 ELSE 0 END) AS SoLuongRot,
        SUM(CASE WHEN Diem IS NULL THEN 1 ELSE 0 END) AS ChuaNhapDiem,
        MAX(Diem) AS DiemCaoNhat
    FROM DIEM
    WHERE MaLHP = p_MaLHP;
END$$

DELIMITER;
