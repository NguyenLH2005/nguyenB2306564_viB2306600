TÀI LIỆU ĐẶC TẢ DỰ ÁN: HỆ THỐNG QUẢN LÝ SINH VIÊN (JAVA CONSOLE)
================================================================

1. THÔNG TIN CHUNG
------------------
- Mô hình ứng dụng: Ứng dụng Console (Command Line Interface).
- Ngôn ngữ lập trình: Java (Java SE).
- Công cụ quản lý dự án: Maven.
- Hệ quản trị cơ sở dữ liệu: MySQL.
- Thư viện kết nối CSDL: mysql-connector-j (JDBC).
- Kiến trúc phần mềm: MVC cơ bản kết hợp DAO (Data Access Object).

2. YÊU CẦU KỸ THUẬT CƠ SỞ DỮ LIỆU (ĐÃ ĐÁP ỨNG 100%)
--------------------------------------------------
Hệ thống quản lý luồng dữ liệu thông qua các kỹ thuật SQL nâng cao từ Backend:

- **Giao dịch (Transaction)**: Đã triển khai 3 Transaction (Đăng ký học phần, Hủy đăng ký, Đóng học phí) đảm bảo toàn vẹn dữ liệu tài chính.
- **Thủ tục lưu trữ (Stored Procedure)**: Đã triển khai 4 Procedure cho các báo cáo Doanh thu, Chất lượng đào tạo, Thống kê nợ và Danh sách giảng dạy.
- **Hàm tự định nghĩa (Stored Function)**: Đã triển khai 3 Function dùng để tính GPA, Sĩ số và Công nợ động.

3. CHI TIẾT TRẠNG THÁI TRIỂN KHAI 16 TASK (HOÀN THÀNH 100%)
---------------------------------------------------------

| STT | Tên chức năng (Nghiệp vụ) | Phụ trách | Kỹ thuật SQL chính | Trạng thái | Mô tả triển khai chi tiết |
| :-- | :--- | :--- | :--- | :--- | :--- |
| 1 | Đăng ký học phần | TV B | TRANSACTION | **Hoàn thành** | Kiểm tra sĩ số tối đa bằng Java, sau đó dùng Transaction để INSERT vào bảng DIEM. Trigger CSDL tự động tính toán số tiền HOC_PHI phát sinh ngay khi đăng ký. |
| 2 | Hủy đăng ký học phần | TV B | TRANSACTION | **Hoàn thành** | Dùng Transaction thực hiện DELETE trong bảng DIEM dựa trên MSSV và MaLHP. Trigger CSDL tự động hoàn trả/trừ tiền trong bảng HOC_PHI tương ứng. |
| 3 | Đóng tiền học phí | TV B | TRANSACTION | **Hoàn thành** | Thực hiện ghi nhận phiếu thu vào bảng PHIEU_THU và cập nhật cột DaDong trong bảng HOC_PHI. Đảm bảo số tiền đóng không vượt quá số tiền nợ thực tế. |
| 4 | Xem bảng điểm & GPA | TV B | FUNCTION | **Hoàn thành** | Truy vấn danh sách điểm từ bảng DIEM, kết quả GPA được tính toán tức thời thông qua hàm `func_TinhGPA(mssv)` nhúng trong câu lệnh SQL. |
| 5 | Kiểm tra sĩ số LHP | TV B | FUNCTION | **Hoàn thành** | Hiển thị thông tin lớp học phần, sử dụng hàm `func_DemSiSo(maLHP)` để đếm chính xác số lượng sinh viên đã đăng ký thành công vào lớp đó. |
| 6 | Tra cứu công nợ | TV B | FUNCTION | **Hoàn thành** | Tính toán số tiền sinh viên còn phải nộp cho một học kỳ cụ thể bằng cách gọi hàm `func_TinhTienNo(mssv, maHK)` (lấy Tổng - Đã đóng). |
| 7 | Thống kê SV nợ phí | TV B | PROCEDURE | **Hoàn thành** | Admin gọi Stored Procedure `sp_ThongKeSVNoHocPhi(maHK)` để xuất danh sách sinh viên chưa hoàn tất nghĩa vụ tài chính trong học kỳ được chọn. |
| 8 | Báo cáo Doanh thu | TV B | PROCEDURE | **Hoàn thành** | Xuất báo cáo tổng thu theo từng Khoa bằng Stored Procedure `sp_BaoCaoDoanhThuHocPhi()`, hỗ trợ quản lý tài chính ở cấp độ vĩ mô. |
| 9 | DS LHP Giảng dạy | TV A | PROCEDURE | **Hoàn thành** | Giảng viên sau khi đăng nhập sẽ chỉ thấy những lớp mình phụ trách thông qua Procedure `sp_DanhSachLHP_GiangVien(maGV)` lọc theo Mã giảng viên. |
| 10 | Báo cáo Chất lượng | TV A | PROCEDURE | **Hoàn thành** | Phân tích tỉ lệ Đậu/Rớt và điểm số cao nhất của một Lớp học phần bằng cách thực thi Stored Procedure `sp_BaoCaoChatLuong_LHP(maLHP)`. |
| 11 | Đăng nhập & Điều hướng| TV A | SELECT | **Hoàn thành** | Kiểm tra thông tin trong bảng ACCOUNT, xác định vai trò (AD/GV/SV) để chuyển hướng người dùng vào các phân hệ Menu Console phù hợp. |
| 12 | QL Cấu trúc Đào tạo | TV A | CRUD | **Hoàn thành** | Cung cấp Menu quản lý toàn diện (Xem/Thêm/Sửa/Xóa) cho các danh mục Khoa, Ngành và Lớp hành chính, đảm bảo tính nhất quán dữ liệu. |
| 13 | QL Hồ sơ Nhân sự | TV A | CRUD | **Hoàn thành** | Quản lý hồ sơ Sinh viên & Giảng viên. Hệ thống tự động phát sinh MSSV/MaGV theo quy tắc và tự tạo tài khoản đăng nhập ACCOUNT đồng bộ. |
| 14 | QL Khung đào tạo | TV A | CRUD | **Hoàn thành** | Quản lý danh mục Môn học (Thêm/Sửa/Xóa), cho phép thiết lập số tín chỉ và loại môn (Đại cương, Cơ sở ngành, Chuyên ngành...). |
| 15 | Cấu hình Học kỳ | TV A | CRUD | **Hoàn thành** | Quản lý việc đóng/mở các kỳ học và thiết lập bảng đơn giá tín chỉ (DON_GIA_TIN_CHI) linh hoạt cho từng năm học và loại môn học. |
| 16 | Quản lý Nhập điểm | TV A | UPDATE | **Hoàn thành** | Giảng viên có quyền truy cập vào danh sách sinh viên trong lớp mình dạy và cập nhật điểm số trực tiếp vào bảng DIEM thông qua MaLHP. |

4. KẾT LUẬN
-----------
Dự án đã đạt trạng thái **Production Ready** cho môi trường Console. Toàn bộ giao diện được chuẩn hóa **Tiếng Việt không dấu** để tương thích tối đa với mọi môi trường Console. Mã nguồn được tổ chức theo đúng kiến trúc MVC, sạch sẽ và dễ bảo trì.
