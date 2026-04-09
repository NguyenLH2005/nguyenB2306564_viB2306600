TÀI LIỆU ĐẶC TẢ DỰ ÁN: HỆ THỐNG QUẢN LÝ SINH VIÊN (JAVA CONSOLE)
1. THÔNG TIN CHUNG
Mô hình ứng dụng: Ứng dụng Console (Command Line Interface).

Ngôn ngữ lập trình: Java (Java SE).

Công cụ quản lý dự án: Maven.

Hệ quản trị cơ sở dữ liệu: MySQL.

Thư viện kết nối CSDL: mysql-connector-j (JDBC).

Kiến trúc phần mềm: MVC cơ bản kết hợp DAO (Data Access Object).

2. YÊU CẦU KỸ THUẬT CƠ SỞ DỮ LIỆU (ĐIỂM TỐI ĐA)
Hệ thống không sử dụng Trigger để xử lý logic kinh doanh, mà bắt buộc phải quản lý luồng dữ liệu thông qua các kỹ thuật SQL nâng cao từ Backend:

Giao dịch (Transaction): Tối thiểu 3 Transaction để xử lý các nghiệp vụ liên quan đến tiền bạc và toàn vẹn dữ liệu (Đăng ký môn, Hủy môn, Đóng tiền).

Thủ tục lưu trữ (Stored Procedure): Tối thiểu 4 Procedure dùng cho các truy vấn thống kê phức tạp (JOIN nhiều bảng, tính toán tổng hợp).

Hàm tự định nghĩa (Stored Function): Tối thiểu 3 Function để nhúng vào các câu lệnh SELECT giúp tính toán động (Tính GPA, Tính sĩ số, Tính công nợ).

3. LỘ TRÌNH THỰC HIỆN DỰ ÁN
Phase 1 (Database Preparation): Hoàn thiện mã SQL cho bảng, ràng buộc khóa ngoại, và viết sẵn các mã tạo PROCEDURE, FUNCTION. (Đã cấu trúc xong bảng, chuẩn bị viết Procedure/Function).

Phase 2 (Project Setup): Tạo dự án Maven, cấu hình pom.xml, cài đặt thư viện MySQL JDBC, viết file kết nối DatabaseConnection.java.

Phase 3 (Model & Basic DAO): Viết các Entity (Thực thể) và các thao tác CRUD cơ bản cho các bảng cấu trúc hệ thống (Khoa, Lớp, Môn Học).

Phase 4 (Core Business Logic): Xử lý Backend bằng Java JDBC cho các Transaction (Đăng ký môn, Tính học phí).

Phase 5 (Console UI & Integration): Xây dựng hệ thống Menu Console bằng Scanner, ráp nối các DAO vào giao diện, kiểm tra lỗi (try-catch) và hoàn thiện.

4. Phân công nhiệm vụ
STT	Tên chức năng (Nghiệp vụ)	Người phụ trách	Kỹ thuật SQL bắt buộc	Nhóm phân hệ	Ghi chú / Luồng xử lý
1	Đăng ký học phần	Thành viên B	TRANSACTION	Sinh viên & Học vụ	INSERT vào bảng DIEM + UPDATE cộng tiền HOC_PHI. Lỗi 1 bước -> Rollback.
2	Hủy đăng ký học phần	Thành viên B	TRANSACTION	Sinh viên & Học vụ	DELETE khỏi bảng DIEM + UPDATE trừ tiền HOC_PHI. Lỗi 1 bước -> Rollback.
3	Đóng tiền học phí	Thành viên B	TRANSACTION	Quản lý Tài chính	INSERT vào PHIEU_THU + UPDATE cộng tiền DaDong. Lỗi 1 bước -> Rollback.
4	Xem bảng điểm & Tính GPA	Thành viên B	STORED FUNCTION	Tra cứu (Sinh viên)	Viết hàm func_TinhGPA(MSSV) để tính điểm trung bình tích lũy.
5	Kiểm tra sĩ số Lớp học phần	Thành viên B	STORED FUNCTION	Tra cứu (Sinh viên)	Viết hàm func_DemSiSo(MaLHP) để hiện số lượng đã đăng ký / Tối đa.
6	Tra cứu công nợ cá nhân	Thành viên B	STORED FUNCTION	Tra cứu (Sinh viên)	Viết hàm func_TinhTienNo(MSSV, MaHocKy) tính (TongTien - DaDong).
7	Thống kê SV nợ học phí	Thành viên B	STORED PROCEDURE	Báo cáo Thống kê	Lọc SV "Chưa hoàn tất", JOIN với LOP, NGANH để truy thu.
8	Báo cáo Doanh thu Học phí	Thành viên B	STORED PROCEDURE	Báo cáo Thống kê	Tính tổng thực thu / phải thu, GROUP BY theo từng Khoa.
9	Danh sách LHP để Nhập điểm	Thành viên A	STORED PROCEDURE	Nghiệp vụ Giảng viên	Truyền MaGV, lọc ra danh sách lớp đang dạy trong học kỳ.
10	Báo cáo Chất lượng Đào tạo	Thành viên A	STORED PROCEDURE	Báo cáo Thống kê	Truyền MaLHP, đếm số lượng & tỷ lệ Đậu/Rớt, Điểm cao nhất.
11	Đăng nhập & Điều hướng	Thành viên A	DML Cơ bản (SELECT)	Hệ thống nền	Check bảng ACCOUNT, điều hướng Menu Console (Admin/GV/SV).
12	Quản lý Cấu trúc Đào tạo	Thành viên A	DML Cơ bản (CRUD)	Quản trị Danh mục	Thêm/Sửa/Xóa bảng KHOA, NGANH, LOP.
13	Quản lý Hồ sơ Nhân sự	Thành viên A	DML Cơ bản (CRUD)	Quản trị Danh mục	Cấp mã, thêm thông tin cho GIANG_VIEN, SINH_VIEN.
14	Quản lý Khung chương trình	Thành viên A	DML Cơ bản (CRUD)	Quản trị Học vụ	Thêm/Sửa dữ liệu bảng MON_HOC (Số TC, Loại môn).
15	Cấu hình Hệ thống Học kỳ	Thành viên A	DML Cơ bản (CRUD)	Quản trị Học vụ	Mở/Khóa HOC_KY, thiết lập DON_GIA_TIN_CHI.
16	Quản lý Nhập điểm	Thành viên A	DML Cơ bản (UPDATE)	Nghiệp vụ Giảng viên	Cập nhật Diem cho sinh viên vào bảng DIEM.
