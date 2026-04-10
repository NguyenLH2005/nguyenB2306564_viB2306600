# NỘI DUNG CHI TIẾT BÁO CÁO ĐỒ ÁN
## Đề tài: Hệ thống Quản lý Sinh viên (Java Console & MySQL)

---

### 1. THÔNG TIN CHUNG
*   **Tên đề tài:** Hệ thống Quản lý Sinh viên.
*   **Sinh viên thực hiện:**
    1.  **Lê Hoàng Nguyên** - MSSV: B2306564
    2.  **Trịnh Minh Vĩ** - MSSV: B2306600
*   **Công nghệ sử dụng:** Java (JDBC), MySQL, Maven.
*   **Kiến trúc:** POJO Model, MVC Console kết hợp DAO (Data Access Object).

---

### 2. CHI TIẾT CÁC CHỨC NĂNG CỦA HỆ THỐNG
Hệ thống được thiết kế theo 3 phân hệ chính dựa trên vai trò người dùng (điều hướng từ `Main`, thông qua đăng nhập phân quyền `ACCOUNT`):

#### A. Phân hệ Admin:
1.  **Quản lý cấu trúc đào tạo:** Cung cấp Menu quản lý toàn diện (Xem/Thêm/Sửa/Xóa - CRUD) cho các danh mục Khoa, Ngành, Lớp hành chính.
2.  **Quản lý khung chương trình:** CRUD danh mục Môn học, định nghĩa số tín chỉ và loại môn (Đại cương, Chuyên ngành,...).
3.  **Cấu hình hệ thống:** Quản lý Học kỳ (Mở/Đóng học kỳ) và thiết lập Bảng Đơn giá Tín chỉ linh hoạt theo năm học và loại môn.
4.  **Quản lý hồ sơ nhân sự:** Duy trì thông tin Giảng viên, Sinh viên. Có cơ chế cấp tài khoản đăng nhập để bảo mật thông tin.
5.  **Quản lý Lớp học phần:** Chức năng mới cho phép Admin trực tiếp thiết lập kế hoạch đào tạo thông qua việc Mở (Thêm), Sửa, Xóa các Lớp học phần cho học kỳ hiện tại, gán Giảng viên và quy định Sĩ số tối đa.
6.  **Thống kê & Báo cáo:** Xuất báo cáo Doanh thu Học phí theo từng Khoa và Lọc danh sách Sinh viên còn nợ phí.

#### B. Phân hệ Giảng viên:
1.  **Danh sách lớp giảng dạy:** Giảng viên tra cứu các lớp học phần được phân công trong học kỳ.
2.  **Quản lý điểm số:** Hiển thị danh sách sinh viên của lớp trước khi nhập, cho phép giảng viên cập nhật điểm số trực tiếp.
3.  **Báo cáo chất lượng đào tạo:** Phân tích tỷ lệ Đậu/Rớt và điểm cao nhất của một Lớp học phần cụ thể sau khi kết thúc môn.

#### C. Phân hệ Sinh viên:
1.  **Đăng ký học phần:** Liệt kê các lớp đang mở, hiển thị tự động *Sĩ số hiện tại / Sĩ số tối đa* và *Số chỗ còn trống*. Kiểm tra điều kiện và cho phép đăng ký trực tuyến (Giao dịch).
2.  **Hủy đăng ký học phần:** Rút môn học hợp lệ, hệ thống tự động tính toán lại tài chính.
3.  **Đóng tiền học phí:** Tra cứu số tiền thực tế còn nợ và thực hiện thanh toán trực tuyến bảo mật.
4.  **Xem bảng điểm & Tính GPA:** Hiển thị tất cả môn đã học và tính Điểm trung bình tích lũy GPA tự động.
5.  **Xem và đăng ký học lại:** Tính toán số Tín chỉ rớt cần học lại. Tự động truy xuất danh sách các Lớp học phần đang mở của những môn sinh viên đã thi rớt (Điểm < 4.0) và chưa đăng ký để sinh viên tiện đăng ký lại.
6.  **Tra cứu công nợ cá nhân:** Báo cáo chi tiết tổng tiền phải đóng và tổng tiền đã đóng cho học kỳ đang chọn.

---

### 3. PHÂN CÔNG CÔNG VIỆC
Dự án được triển khai song song, phân chia rõ ràng các module logic:

| Sinh viên | Nhiệm vụ chính | Kỹ thuật nổi bật phụ trách |
| :--- | :--- | :--- |
| **Trịnh Minh Vĩ** (TV B) | Phụ trách toàn bộ nghiệp vụ Tài chính (Học phí, Đóng tiền) và Học vụ của Sinh viên (Đăng ký, Hủy môn, Đăng ký học lại, Tính GPA). | Triển khai Transactions (Giao dịch an toàn), Stored Functions (Tính GPA, Công nợ, Đếm sĩ số, Tín chỉ học lại), Triggers cho CSDL, Truy vấn lọc dữ liệu phức tạp (Subquery, NOT IN). |
| **Lê Hoàng Nguyên** (TV A) | Build hệ thống Core kiến trúc, Phân quyền Đăng nhập, Nghiệp vụ Danh mục Admin và Phân hệ Giảng viên (Chấm điểm, Thống kê). | Thiết kế CSDL, Triển khai CRUD toàn diện, Stored Procedures (Báo cáo Doanh thu, Chất lượng), Hệ thống Navigation Console động đa tầng. |

---

### 4. THIẾT KẾ CƠ SỞ DỮ LIỆU
Hệ thống gồm 12 bảng chính liên kết chặt chẽ:

*   **KHOA - NGANH - LOP:** Cấu trúc tổ chức hành chính.
*   **SINH_VIEN - GIANG_VIEN:** Thông tin nhân sự cốt lõi.
*   **MON_HOC - HOC_KY - LOP_HOC_PHAN:** Khung chương trình và kế hoạch giảng dạy.
*   **DIEM:** Lưu trữ kết quả học tập và liên kết đăng ký môn học.
*   **HOC_PHI - PHIEU_THU:** Quản lý tài chính sinh viên.
*   **ACCOUNT:** Quản lý định danh và phân quyền đăng nhập.

> [!TIP]
> **Mô hình vật lý:** Các bảng được chuẩn hóa dạng 3NF. Sử dụng khóa ngoại (`FOREIGN KEY`) để đảm bảo tính toàn vẹn (ví dụ: Không thể xóa Khoa nếu còn Ngành thuộc khoa đó).

---

### 5. STORED OBJECTS & LOGIC DATABASE CHI TIẾT
Dự án đã khai thác sâu sức mạnh của RDBMS MySQL để giảm tải nghiệp vụ cho backend Java, đảm bảo tính toàn vẹn và bảo mật dữ liệu ở mức tối đa:

#### A. Stored Procedures có áp dụng Transaction (Giao dịch an toàn):
Hệ thống sử dụng Transaction cho các nghiệp vụ cập nhật chéo giữa nhiều bảng để đảm bảo tiêu chí `ACID` (đặc biệt là tính `Atomicity` - nếu 1 bước lỗi, toàn bộ tiến trình sẽ `Rollback`).
1.  `sp_DangKyHocPhan`: 
    *   **Logic:** Thực hiện `INSERT` dữ liệu sinh viên vào bảng `DIEM` (đăng ký chỗ vào Lớp học phần). 
    *   **Bảo vệ:** Giao dịch này kích hoạt Trigger tính tiền. Nếu xảy ra lỗi hệ thống hoặc vi phạm khóa ngoại, nó sẽ Rollback để tránh việc "sinh viên có tên trong danh sách nhưng không bị tính tiền".
2.  `sp_HuyDangKyHocPhan`:
    *   **Logic:** Thực hiện `DELETE` bản ghi của sinh viên khỏi bảng `DIEM`.
    *   **Bảo vệ:** Đảm bảo việc hủy môn và hoàn lại tiền (thông qua Trigger) được thực hiện đồng thời.
3.  `sp_DongTienHocPhi`:
    *   **Logic:** Gồm 2 bước: Bước 1 là tạo mới một biên lai vào bảng `PHIEU_THU`. Bước 2 là cập nhật cộng dồn số tiền `DaDong` và đánh giá lại `TrangThai` trong bảng `HOC_PHI`.
    *   **Bảo vệ:** Đảm bảo tiền của sinh viên được nộp vào thì chắc chắn cập nhật đúng trạng thái nợ, không xảy ra hiện tượng mất tiền do ngắt kết nối giữa chừng.

#### B. Stored Functions (Hàm tự định nghĩa):
Các hàm được dùng để tính toán và trả về một giá trị duy nhất, được nhúng trực tiếp vào các câu lệnh `SELECT` để hiển thị trên giao diện.
1.  `func_TinhGPA(MSSV)`: 
    *   **Logic:** Duyệt qua bảng `DIEM`, tham chiếu sang `MON_HOC` để lấy số tín chỉ. Sử dụng cấu trúc `CASE WHEN` để quy đổi điểm hệ 10 sang hệ 4, sau đó áp dụng công thức tính Trung bình có trọng số (Tổng điểm hệ 4 * Tín chỉ / Tổng tín chỉ).
2.  `func_DemSiSo(MaLHP)`:
    *   **Logic:** Đếm số lượng sinh viên đã đăng ký (`COUNT(MSSV)`) và ghép chuỗi với `SoLuongMax` của lớp đó. Trả về kết quả trực quan dạng chuỗi: `"Đã đăng ký / Tối đa"` (VD: "35/40").
3.  `func_TinChiHocLai(MSSV)`:
    *   **Logic:** Tìm các môn có `MAX(Diem) < 4.0` (tức là sinh viên chưa từng pass môn này) và tiến hành `SUM` số tín chỉ lại để trả về tổng tín chỉ sinh viên đang nợ hệ thống.

#### C. Stored Procedures KHÔNG sử dụng Transaction:
Được sử dụng chủ yếu cho các nghiệp vụ `SELECT` (Truy vấn) và nhóm dữ liệu để phục vụ xuất báo cáo, do không làm thay đổi trạng thái dữ liệu (Create/Update/Delete) nên không cần dùng Transaction.
1.  `sp_ThongKeSVNoHocPhi(MaHocKy)`: 
    *   **Logic:** Lọc ra danh sách sinh viên có `TrangThai != 'Đã hoàn tất'` và tính ra số `TienNo` (Tổng tiền - Đã đóng) để báo cho Admin.
2.  `sp_BaoCaoDoanhThuHocPhi(MaHocKy)`:
    *   **Logic:** Kết hợp `LEFT JOIN` qua 5 bảng (Khoa -> Ngành -> Lớp -> SV -> Học Phí) kết hợp tính `SUM` để chốt xem mỗi Khoa đã thu được bao nhiêu tiền và còn bị nợ bao nhiêu trong học kỳ đó.
3.  `sp_DanhSachLHP_GiangVien(MaGV, MaHocKy)`:
    *   **Logic:** Lọc bảng `LOP_HOC_PHAN` theo mã giảng viên để in ra thời khóa biểu giảng dạy của họ. Có đính kèm thêm subquery đếm sĩ số thực tế.
4.  `sp_BaoCaoChatLuong_LHP(MaLHP)`:
    *   **Logic:** Thống kê phổ điểm của lớp: Tổng sĩ số, Tỉ lệ Đậu (>=4.0), Tỉ lệ Rớt (<4.0), và truy xuất sinh viên có `MAX(Diem)` cao nhất.

#### D. Triggers (Trình kích hoạt tự động):
Các "Bẫy" dữ liệu hoạt động ngầm phía dưới CSDL, tự động chạy khi có thao tác DML (Insert/Update/Delete) xảy ra, giúp giữ tính toàn vẹn dữ liệu cực tốt:
1.  `trg_Check_Account_Insert` (`BEFORE INSERT` trên `ACCOUNT`):
    *   **Logic:** Bẫy lỗi phân quyền. Trước khi một Account được tạo, Trigger kiểm tra xem mật mã chức vụ là SV hay GV. Nếu là SV, nó sẽ soi bảng `SINH_VIEN`, nếu không tìm thấy MSSV khớp với Username, nó sẽ ném ra lỗi `SQLSTATE '45000'` và chăn việc tạo tài khoản. (Chống tạo tài khoản rác).
2.  `trg_Calculate_HocPhi_AfterDangKy` (`AFTER INSERT` trên `DIEM`):
    *   **Logic:** Ngay khi một dòng điểm được Insert (do SV đăng ký môn), Trigger chạy ngầm: Lấy số tín chỉ x Đơn giá tín chỉ ứng với Loại môn học đó -> Tính ra số tiền. Sau đó tự động `INSERT` hoặc `UPDATE` số tiền đó vào Mã Học Phí của sinh viên.
3.  `trg_Calculate_HocPhi_AfterHuyDangKy` (`AFTER DELETE` trên `DIEM`):
    *   **Logic:** Ngược lại với quá trình trên. Khi SV hủy môn, Trigger tính số tiền của môn đó và tự động `UPDATE` trừ số tiền tương ứng khỏi mã Học Phí. Nó cũng sẽ tự đánh giá lại biến `TrangThai` (từ "Đã hoàn tất" về "Chưa hoàn tất" nếu việc trừ tiền làm tiền thu < tổng tiền).

---

### 6. HƯỚNG DẪN CHỤP MÀN HÌNH CHỨC NĂNG BÁO CÁO
*(Hỗ trợ trình bày Slider Slide Báo Cáo)*

1.  **Giao diện Đăng nhập phân quyền (`Main.java`):** Chụp màn hình khi nhập User/Pass và điều hướng tới các Menu khác nhau (Admin, GV, SV).
2.  **Menu Admin - Quản lý Cấu trúc & LHP:** Chụp bảng hiển thị danh sách Ngành/Lớp (đã JOIN tên Khoa) và màn hình Menu Quản lý Lớp học phần mới.
3.  **Nghiệp vụ Giảng viên:** Chụp giao diện Nhập Điểm (hiển thị danh sách SV và điểm cũ trước khi bắt thầy cô nhập MSSV).
4.  **Tính năng Tính GPA & Công nợ:** Chụp giao diện Sinh viên hiển thị bản điểm chi tiết kèm GPA tổng.
5.  **Tính năng Đăng ký & Học Lại:** Chụp danh sách Môn đang mở có đính kèm **Sĩ số Còn trống**, đặc biệt hãy chụp bảng phân tích **Danh sách môn cần Học lại** cực kỳ trực quan với điểm số cũ < 4.0.
