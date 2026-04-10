-- ==============================================================================
-- CƠ SỞ DỮ LIỆU: QUẢN LÝ SINH VIÊN (PHIÊN BẢN CHÍNH THỨC DÀNH CHO ĐỒ ÁN)
-- ==============================================================================

CREATE DATABASE IF NOT EXISTS QuanLySinhVien 
DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE QuanLySinhVien;

-- 1. XÓA BẢNG CŨ (Theo thứ tự từ con đến cha để tránh lỗi khóa ngoại)
-- ==============================================================================
-- 2. NHÓM BẢNG THỜI GIAN, PHÂN LOẠI & TÀI KHOẢN
-- ==============================================================================

CREATE TABLE NAM_HOC (
    MaNamHoc VARCHAR(20) PRIMARY KEY, -- VD: '24-25'
    TenNamHoc VARCHAR(50) NOT NULL
);

CREATE TABLE HOC_KY (
    MaHocKy VARCHAR(20) PRIMARY KEY, -- VD: 'HK1_2425'
    TenHocKy VARCHAR(50) NOT NULL,
    MaNamHoc VARCHAR(20) NOT NULL,
    TrangThai BOOLEAN DEFAULT TRUE, -- TRUE: Đang mở (1), FALSE: Đã khóa (0)
    FOREIGN KEY (MaNamHoc) REFERENCES NAM_HOC(MaNamHoc) 
);

CREATE TABLE LOAI_MON_HOC (
    MaLoaiMon VARCHAR(20) PRIMARY KEY, -- VD: 'DC' (Đại cương), 'CN' (Chuyên ngành)
    TenLoaiMon VARCHAR(100) NOT NULL
);

CREATE TABLE DON_GIA_TIN_CHI (
    MaNamHoc VARCHAR(20) NOT NULL,
    MaLoaiMon VARCHAR(20) NOT NULL,
    SoTienMotTinChi FLOAT NOT NULL,
    PRIMARY KEY (MaNamHoc, MaLoaiMon),
    FOREIGN KEY (MaNamHoc) REFERENCES NAM_HOC(MaNamHoc),
    FOREIGN KEY (MaLoaiMon) REFERENCES LOAI_MON_HOC(MaLoaiMon) 
);

CREATE TABLE LOAI_TAI_KHOAN (
    MaLoaiTK VARCHAR(10) PRIMARY KEY, -- 'SV', 'GV', 'ADMIN'
    TenLoaiTK VARCHAR(50) NOT NULL
);

CREATE TABLE ACCOUNT (
    Username VARCHAR(50) PRIMARY KEY, 
    -- Note: Username chính là MSSV (nếu là SV) hoặc MaGV (nếu là GV)
    Password VARCHAR(50) NOT NULL DEFAULT '123456',
    MaLoaiTK VARCHAR(10) NOT NULL,
    FOREIGN KEY (MaLoaiTK) REFERENCES LOAI_TAI_KHOAN(MaLoaiTK)
);

-- ==============================================================================
-- 3. NHÓM BẢNG QUẢN LÝ TỔ CHỨC & CON NGƯỜI
-- ==============================================================================

CREATE TABLE KHOA (
    MaKhoa VARCHAR(10) PRIMARY KEY,
    TenKhoa VARCHAR(100) NOT NULL
);

CREATE TABLE NGANH (
    MaNganh VARCHAR(10) PRIMARY KEY,
    TenNganh VARCHAR(100) NOT NULL,
    MaKhoa VARCHAR(10) NOT NULL,
    FOREIGN KEY (MaKhoa) REFERENCES KHOA(MaKhoa) 
);

CREATE TABLE LOP (
    MaLop VARCHAR(20) PRIMARY KEY,
    TenLop VARCHAR(100) NOT NULL,
    MaNganh VARCHAR(10) NOT NULL,
    FOREIGN KEY (MaNganh) REFERENCES NGANH(MaNganh) 
);

CREATE TABLE GIANG_VIEN (
    MaGV VARCHAR(15) PRIMARY KEY,
    HoTen VARCHAR(100) NOT NULL,
    NgaySinh DATE,
    MaKhoa VARCHAR(10) NOT NULL,
    FOREIGN KEY (MaKhoa) REFERENCES KHOA(MaKhoa)
);

CREATE TABLE SINH_VIEN ( 
    MSSV VARCHAR(10) PRIMARY KEY,
    HoTen VARCHAR(100) NOT NULL,
    NgaySinh DATE,
    MaLop VARCHAR(20) NOT NULL,
    FOREIGN KEY (MaLop) REFERENCES LOP(MaLop) 
);

-- ==============================================================================
-- 4. NHÓM BẢNG HỌC VỤ & TÀI CHÍNH
-- ==============================================================================

CREATE TABLE MON_HOC (
    MaMon VARCHAR(15) PRIMARY KEY,
    TenMon VARCHAR(100) NOT NULL,
    SoTinChi INT NOT NULL,
    MaLoaiMon VARCHAR(20) NOT NULL,
    FOREIGN KEY (MaLoaiMon) REFERENCES LOAI_MON_HOC(MaLoaiMon) 
);

-- Bảng Lớp học phần: Thể hiện việc Giảng viên dạy Môn gì ở Học kỳ nào
CREATE TABLE LOP_HOC_PHAN (
    MaLHP VARCHAR(20) PRIMARY KEY, -- VD: 'CT467_HK1_01'
    MaMon VARCHAR(15) NOT NULL,
    MaHocKy VARCHAR(20) NOT NULL,
    MaGV VARCHAR(15) NOT NULL,
    SoLuongMax INT DEFAULT 40, -- Sĩ số tối đa của lớp học phần
    FOREIGN KEY (MaMon) REFERENCES MON_HOC(MaMon),
    FOREIGN KEY (MaHocKy) REFERENCES HOC_KY(MaHocKy),
    FOREIGN KEY (MaGV) REFERENCES GIANG_VIEN(MaGV)
);

-- Bảng Điểm: Đã tối ưu, sinh viên giờ đăng ký vào Lớp học phần, không đăng ký chay vào môn học nữa
CREATE TABLE DIEM (
    MSSV VARCHAR(10) NOT NULL,
    MaLHP VARCHAR(20) NOT NULL,
    Diem FLOAT DEFAULT NULL,
    PRIMARY KEY (MSSV, MaLHP),
    FOREIGN KEY (MSSV) REFERENCES SINH_VIEN(MSSV),
    FOREIGN KEY (MaLHP) REFERENCES LOP_HOC_PHAN(MaLHP)
);

CREATE TABLE HOC_PHI (
    MaHocPhi VARCHAR(30) PRIMARY KEY, -- VD: 'HP_B2312345_HK1_2425'
    MSSV VARCHAR(10) NOT NULL,
    MaHocKy VARCHAR(20) NOT NULL,
    TongTien FLOAT NOT NULL DEFAULT 0,
    DaDong FLOAT DEFAULT 0,
    TrangThai VARCHAR(50) DEFAULT 'Chua dong',
    FOREIGN KEY (MSSV) REFERENCES SINH_VIEN(MSSV),
    FOREIGN KEY (MaHocKy) REFERENCES HOC_KY(MaHocKy) 
);

CREATE TABLE PHIEU_THU (
    MaPhieu INT AUTO_INCREMENT PRIMARY KEY,
    MaHocPhi VARCHAR(30) NOT NULL,
    NgayThu DATETIME DEFAULT CURRENT_TIMESTAMP,
    SoTienThu FLOAT NOT NULL,
    PhuongThuc VARCHAR(100) NOT NULL,
    FOREIGN KEY (MaHocPhi) REFERENCES HOC_PHI(MaHocPhi) 
);

-- ==============================================================================
-- 5. TRIGGER RÀNG BUỘC TÀI KHOẢN
-- ==============================================================================
DELIMITER $$

CREATE TRIGGER trg_Check_Account_Insert
BEFORE INSERT ON ACCOUNT
FOR EACH ROW
BEGIN
    DECLARE v_count INT;
    
    -- Nếu là tài khoản Sinh Viên
    IF NEW.MaLoaiTK = 'SV' THEN
        SELECT COUNT(*) INTO v_count FROM SINH_VIEN WHERE MSSV = NEW.Username;
        IF v_count = 0 THEN
            SIGNAL SQLSTATE '45000' 
            SET MESSAGE_TEXT = 'Loi: Username nay khong ton tai trong bang SINH_VIEN!';
        END IF;
        
    -- Nếu là tài khoản Giảng Viên
    ELSEIF NEW.MaLoaiTK = 'GV' THEN
        SELECT COUNT(*) INTO v_count FROM GIANG_VIEN WHERE MaGV = NEW.Username;
        IF v_count = 0 THEN
            SIGNAL SQLSTATE '45000' 
            SET MESSAGE_TEXT = 'Loi: Username nay khong ton tai trong bang GIANG_VIEN!';
        END IF;
    END IF;
END$$

DELIMITER ;

-- ==============================================================================
-- 6. TRIGGER TÍNH TIỀN KHI ĐĂNG KÝ MÔN (INSERT VÀO BẢNG ĐIỂM)
-- ==============================================================================
DELIMITER $$

CREATE TRIGGER trg_Calculate_HocPhi_AfterDangKy
AFTER INSERT ON DIEM
FOR EACH ROW
BEGIN
    DECLARE v_MaHocKy VARCHAR(20);
    DECLARE v_MaNamHoc VARCHAR(20);
    DECLARE v_MaLoaiMon VARCHAR(20);
    DECLARE v_SoTinChi INT;
    DECLARE v_DonGia FLOAT;
    DECLARE v_TienThem FLOAT;
    DECLARE v_MaHocPhi VARCHAR(30);
    
    -- 1. Lấy thông tin môn học và học kỳ từ lớp học phần sinh viên vừa đăng ký
    SELECT lhp.MaHocKy, hk.MaNamHoc, mh.MaLoaiMon, mh.SoTinChi
    INTO v_MaHocKy, v_MaNamHoc, v_MaLoaiMon, v_SoTinChi
    FROM LOP_HOC_PHAN lhp
    JOIN HOC_KY hk ON lhp.MaHocKy = hk.MaHocKy
    JOIN MON_HOC mh ON lhp.MaMon = mh.MaMon
    WHERE lhp.MaLHP = NEW.MaLHP;
    
    -- 2. Dò tìm Đơn giá tín chỉ ứng với năm học và loại môn đó
    SELECT SoTienMotTinChi INTO v_DonGia
    FROM DON_GIA_TIN_CHI
    WHERE MaNamHoc = v_MaNamHoc AND MaLoaiMon = v_MaLoaiMon;
    
    -- 3. Tính tiền của môn học này
    SET v_TienThem = v_SoTinChi * v_DonGia;
    
    -- 4. Tạo mã Học phí tự động
    SET v_MaHocPhi = CONCAT('HP_', NEW.MSSV, '_', v_MaHocKy);
    
    -- 5. Cập nhật hoặc Thêm mới vào bảng HOC_PHI
    IF EXISTS (SELECT 1 FROM HOC_PHI WHERE MaHocPhi = v_MaHocPhi) THEN
        -- Đã có phiếu thu của học kỳ này -> Cộng dồn tiền
        UPDATE HOC_PHI 
        SET TongTien = TongTien + v_TienThem,
            TrangThai = CASE WHEN DaDong >= (TongTien + v_TienThem) THEN 'Da hoan tat' ELSE 'Chua hoan tat' END
        WHERE MaHocPhi = v_MaHocPhi;
    ELSE
        -- Chưa có phiếu thu (môn đăng ký đầu tiên của học kỳ) -> Tạo mới
        INSERT INTO HOC_PHI (MaHocPhi, MSSV, MaHocKy, TongTien, DaDong, TrangThai)
        VALUES (v_MaHocPhi, NEW.MSSV, v_MaHocKy, v_TienThem, 0, 'Chua hoan tat');
    END IF;
END$$

DELIMITER ;

-- ==============================================================================
-- 7. TRIGGER TRỪ TIỀN KHI HỦY MÔN (DELETE KHỎI BẢNG ĐIỂM)
-- ==============================================================================
DELIMITER $$

CREATE TRIGGER trg_Calculate_HocPhi_AfterHuyDangKy
AFTER DELETE ON DIEM
FOR EACH ROW
BEGIN
    DECLARE v_MaHocKy VARCHAR(20);
    DECLARE v_MaNamHoc VARCHAR(20);
    DECLARE v_MaLoaiMon VARCHAR(20);
    DECLARE v_SoTinChi INT;
    DECLARE v_DonGia FLOAT;
    DECLARE v_TienTru FLOAT;
    DECLARE v_MaHocPhi VARCHAR(30);
    
    -- 1. Lấy thông tin môn học và học kỳ từ lớp học phần sinh viên VỪA HỦY
    SELECT lhp.MaHocKy, hk.MaNamHoc, mh.MaLoaiMon, mh.SoTinChi
    INTO v_MaHocKy, v_MaNamHoc, v_MaLoaiMon, v_SoTinChi
    FROM LOP_HOC_PHAN lhp
    JOIN HOC_KY hk ON lhp.MaHocKy = hk.MaHocKy
    JOIN MON_HOC mh ON lhp.MaMon = mh.MaMon
    WHERE lhp.MaLHP = OLD.MaLHP;
    
    -- 2. Dò tìm Đơn giá tín chỉ
    SELECT SoTienMotTinChi INTO v_DonGia
    FROM DON_GIA_TIN_CHI
    WHERE MaNamHoc = v_MaNamHoc AND MaLoaiMon = v_MaLoaiMon;
    
    -- 3. Tính số tiền cần trừ đi
    SET v_TienTru = v_SoTinChi * v_DonGia;
    
    -- 4. Tạo lại mã Học phí tương ứng
    SET v_MaHocPhi = CONCAT('HP_', OLD.MSSV, '_', v_MaHocKy);
    
    -- 5. Cập nhật TRỪ TIỀN trong bảng HOC_PHI
    UPDATE HOC_PHI 
    SET 
        TongTien = TongTien - v_TienTru,
        TrangThai = CASE 
                        -- Nếu trừ xong mà tổng tiền về 0 và chưa đóng đồng nào
                        WHEN (TongTien - v_TienTru) <= 0 AND DaDong <= 0 THEN 'Chua dong'
                        -- Nếu số tiền đã đóng lớn hơn hoặc bằng tổng tiền mới (do hủy môn)
                        WHEN DaDong >= (TongTien - v_TienTru) THEN 'Da hoan tat' 
                        -- Các trường hợp còn lại
                        ELSE 'Chua hoan tat' 
                    END
    WHERE MaHocPhi = v_MaHocPhi;

END$$

DELIMITER ;


