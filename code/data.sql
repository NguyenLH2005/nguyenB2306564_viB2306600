INSERT INTO LOAI_TAI_KHOAN (MaLoaiTK, TenLoaiTK) VALUES 
('SV', 'Sinh Viên'), 
('GV', 'Giảng Viên'), 
('ADMIN', 'Quản Trị Viên');

INSERT INTO ACCOUNT (Username, Password, MaLoaiTK) VALUES 
('admin', 'admin123', 'ADMIN');
-- ==============================================================================
-- 1. BANG THOI GIAN & PHAN LOAI (BANG CHA CAP 1)
-- ==============================================================================

INSERT INTO NAM_HOC (MaNamHoc, TenNamHoc) VALUES 
('24-25', 'Nam hoc 2024 - 2025'),
('25-26', 'Nam hoc 2025 - 2026');

INSERT INTO HOC_KY (MaHocKy, TenHocKy, MaNamHoc, TrangThai) VALUES 
('HK1_2425', 'Hoc ky 1 (2024-2025)', '24-25', 0), -- Da khoa
('HK2_2425', 'Hoc ky 2 (2024-2025)', '24-25', 0), -- Da khoa
('HK1_2526', 'Hoc ky 1 (2025-2026)', '25-26', 1), -- Dang mo
('HK2_2526', 'Hoc ky 2 (2025-2026)', '25-26', 1); -- Dang mo

INSERT INTO LOAI_MON_HOC (MaLoaiMon, TenLoaiMon) VALUES 
('DC', 'Mon hoc Dai cuong'),
('CN', 'Mon hoc Chuyen nganh');

INSERT INTO DON_GIA_TIN_CHI (MaNamHoc, MaLoaiMon, SoTienMotTinChi) VALUES 
('24-25', 'DC', 350000),
('24-25', 'CN', 450000),
('25-26', 'DC', 380000),
('25-26', 'CN', 480000);

-- ==============================================================================
-- 2. BANG CO CAU TO CHUC (KHOA -> NGANH -> LOP)
-- ==============================================================================

-- Sinh ma Khoa: 11 (CNTT), 12 (Kinh Te), 13 (Nong Nghiep), 14 (Su Pham), 15 (KHTN)
INSERT INTO KHOA (MaKhoa, TenKhoa) VALUES 
('11', 'Khoa Cong nghe Thong tin & Truyen thong'),
('12', 'Khoa Kinh te'),
('13', 'Khoa Nong nghiep'),
('14', 'Khoa Su pham'),
('15', 'Khoa Khoa hoc Tu nhien');

INSERT INTO NGANH (MaNganh, TenNganh, MaKhoa) VALUES 
('CNTT', 'Cong nghe Thong tin', '11'),
('KTPM', 'Ky thuat Phan mem', '11'),
('HTTT', 'He thong Thong tin', '11'),
('QTKD', 'Quan tri Kinh doanh', '12'),
('KETOAN', 'Ke toan', '12'),
('NH', 'Nong hoc', '13'),
('BVTV', 'Bao ve Thuc vat', '13'),
('SPTOAN', 'Su pham Toan hoc', '14'),
('HOAHOC', 'Hoa hoc', '15'),
('SINHHOC', 'Sinh hoc', '15');

INSERT INTO LOP (MaLop, TenLop, MaNganh) VALUES 
('DI23V7A', 'Ky thuat phan mem K49', 'KTPM'),
('DI23V7B', 'He thong thong tin K49', 'HTTT'),
('KT23A1', 'Quan tri kinh doanh K49', 'QTKD'),
('NN23B2', 'Nong hoc K49', 'NH'),
('SP23C3', 'Su pham Toan K49', 'SPTOAN');

-- ==============================================================================
-- 3. BANG NHAN SU (GIANG VIEN & SINH VIEN)
-- ==============================================================================

-- Ma GV = 2 so dau cua Khoa + 4 so thu tu
INSERT INTO GIANG_VIEN (MaGV, HoTen, NgaySinh, MaKhoa) VALUES 
('110001', 'Nguyen Cong Mang', '1980-05-12', '11'),
('110002', 'Tran Thi Du Lieu', '1985-08-22', '11'),
('120001', 'Le Tai Chinh', '1978-11-05', '12'),
('140001', 'Pham Dai So', '1990-02-14', '14'),
('150001', 'Hoang Nguyen Tu', '1982-09-30', '15');

-- Sinh vien sinh nam 2005 (K49)
INSERT INTO SINH_VIEN (MSSV, HoTen, NgaySinh, MaLop) VALUES 
('B2300001', 'Nguyen Van Anh', '2005-01-15', 'DI23V7A'),
('B2300002', 'Tran Bao Binh', '2005-03-22', 'DI23V7A'),
('B2300003', 'Le Cat Cuong', '2005-05-10', 'DI23V7B'),
('B2300004', 'Pham Duy Danh', '2005-07-08', 'DI23V7B'),
('B2300005', 'Hoang Gia An', '2005-09-12', 'KT23A1'),
('B2300006', 'Vu Hai Bang', '2005-11-20', 'KT23A1'),
('B2300007', 'Dang Khoi Nguyen', '2005-02-28', 'NN23B2'),
('B2300008', 'Bui Lam Tuan', '2005-04-16', 'NN23B2'),
('B2300009', 'Do Minh Tri', '2005-06-05', 'SP23C3'),
('B2300010', 'Ngo Ngoc Ha', '2005-08-19', 'SP23C3');

-- ==============================================================================
-- 4. BANG TAI KHOAN (ACCOUNT) 
-- Luu y: Phai chay lenh nay sau khi da Insert GV va SV de Trigger khong bao loi
-- ==============================================================================

-- 10 Account Sinh vien (Mat khau mac dinh 123456)
INSERT INTO ACCOUNT (Username, Password, MaLoaiTK) VALUES 
('B2300001', '123456', 'SV'),
('B2300002', '123456', 'SV'),
('B2300003', '123456', 'SV'),
('B2300004', '123456', 'SV'),
('B2300005', '123456', 'SV'),
('B2300006', '123456', 'SV'),
('B2300007', '123456', 'SV'),
('B2300008', '123456', 'SV'),
('B2300009', '123456', 'SV'),
('B2300010', '123456', 'SV'),
-- 5 Account Giang vien
('110001', '123456', 'GV'),
('110002', '123456', 'GV'),
('120001', '123456', 'GV'),
('140001', '123456', 'GV'),
('150001', '123456', 'GV');

-- ==============================================================================
-- 5. BANG HOC VU (MON HOC & LOP HOC PHAN)
-- ==============================================================================

INSERT INTO MON_HOC (MaMon, TenMon, SoTinChi, MaLoaiMon) VALUES 
('CT101', 'Lap trinh co ban', 3, 'DC'),
('CT173', 'Kien truc may tinh', 3, 'CN'),
('CT467', 'Phan tich thiet ke he thong', 3, 'CN'),
('ML014', 'Triet hoc Mac - Lenin', 3, 'DC'),
('KN001', 'Ky nang mem', 2, 'DC');

INSERT INTO LOP_HOC_PHAN (MaLHP, MaMon, MaHocKy, MaGV, SoLuongMax) VALUES 
('CT101_HK1_25', 'CT101', 'HK1_2526', '110001', 40),
('CT173_HK1_25', 'CT173', 'HK1_2526', '110002', 40),
('CT467_HK1_25', 'CT467', 'HK1_2526', '110001', 30),
('ML014_HK1_25', 'ML014', 'HK1_2526', '140001', 80),
('KN001_HK1_25', 'KN001', 'HK1_2526', '120001', 50);

-- ==============================================================================
-- 6. BANG DIEM (KHI CHAY LENH NAY, TRIGGER SE TU DONG SINH RA HOC PHI)
-- ==============================================================================

INSERT INTO DIEM (MSSV, MaLHP, Diem) VALUES 
('B2300001', 'CT101_HK1_25', 8.5),
('B2300001', 'CT173_HK1_25', 7.0),
('B2300001', 'ML014_HK1_25', NULL), -- Dang hoc, chua co diem

('B2300002', 'CT101_HK1_25', 9.0),
('B2300002', 'KN001_HK1_25', 8.0),

('B2300003', 'CT467_HK1_25', NULL),
('B2300003', 'CT173_HK1_25', NULL),

('B2300005', 'ML014_HK1_25', 6.5),
('B2300005', 'KN001_HK1_25', 7.5),

('B2300010', 'ML014_HK1_25', NULL);

-- !!! LUU Y QUAN TRONG: 
-- Ban KHONG can chay lenh INSERT INTO HOC_PHI. 
-- Vi Trigger trg_Calculate_HocPhi_AfterDangKy cua ban da chay ngam va tu dong tao ra 
-- cac dong Hoc Phi cho cac sinh vien B2300001, B2300002, B2300003, B2300005, B2300010.

-- ==============================================================================
-- 7. BANG PHIEU THU (DONG TIEN)
-- ==============================================================================
-- Gia su sinh vien B2300001 tien hanh dong tien cho ky HK1_2526
-- Ma hoc phi sinh tu dong tu Trigger se la: HP_B2300001_HK1_2526

INSERT INTO PHIEU_THU (MaHocPhi, NgayThu, SoTienThu, PhuongThuc) VALUES 
('HP_B2300001_HK1_2526', '2025-10-15 08:30:00', 1000000, 'Chuyen khoan Agribank'),
('HP_B2300002_HK1_2526', '2025-10-16 09:15:00', 1900000, 'Tien mat');