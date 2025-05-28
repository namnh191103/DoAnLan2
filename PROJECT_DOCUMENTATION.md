# Tài liệu Mô tả Hệ thống Bán Sách (BanSach Project)

## Lời mở đầu

Tài liệu này mô tả chi tiết các chức năng của hệ thống Bán Sách, được thiết kế để giúp người không có chuyên môn lập trình có thể hiểu được cách hệ thống hoạt động, các thành phần liên quan và cách chúng tương tác với cơ sở dữ liệu.

---

## Phần 1: Chức năng Quản trị (Admin)

### Chức năng 1.1: Quản lý Khách hàng (Người dùng)

- **Mô tả:** Cho phép quản trị viên (admin) quản lý toàn diện tài khoản người dùng trong hệ thống. Admin có thể xem danh sách tất cả người dùng, xem chi tiết thông tin từng người, chỉnh sửa thông tin (trừ email và mật khẩu trực tiếp), cấp hoặc thu hồi quyền admin cho người dùng khác, và khóa hoặc mở khóa tài khoản người dùng.
- **File liên quan:**
  - **Controller:** `com.myapp.controller.admin.KhachHangAdminController.java`
  - **Service Interface:** `com.myapp.service.UserService.java`
  - **Service Implementation:** `com.myapp.service.impl.UserServiceImpl.java`
  - **Repository:**
    - `com.myapp.repository.UserRepository.java`
    - `com.myapp.repository.VaiTroRepository.java`
  - **Entity (Model - Cấu trúc dữ liệu):**
    - `com.myapp.model.User.java` (ánh xạ tới bảng `khach_hang`)
    - `com.myapp.model.VaiTro.java` (ánh xạ tới bảng `vai_tro`)
  - **DTO (Đối tượng truyền dữ liệu):** `com.myapp.dto.UserDTO.java`
  - **Giao diện người dùng (Views):** `admin/users.html` (danh sách), `admin/user-view.html` (xem chi tiết), `admin/user-edit.html` (chỉnh sửa).
- **Đường dẫn truy cập (Endpoints) và Hoạt động:**
  - `GET /admin/users`: Hiển thị danh sách người dùng (có phân trang).
    - **Tương tác Database:** Lấy tất cả dữ liệu từ bảng `khach_hang` và thông tin vai trò từ `khach_hang_vai_tro`.
  - `GET /admin/users/{id}`: Xem chi tiết thông tin của một người dùng dựa theo ID.
    - **Tương tác Database:** Lấy dữ liệu của một người dùng từ bảng `khach_hang` theo `id`.
  - `GET /admin/users/{id}/edit`: Hiển thị trang chỉnh sửa thông tin người dùng.
    - **Tương tác Database:** Tương tự như xem chi tiết.
  - `POST /admin/users/{id}`: Cập nhật thông tin người dùng sau khi admin chỉnh sửa.
    - **Tương tác Database:**
      - Đọc thông tin người dùng hiện tại từ `khach_hang`.
      - Nếu có thay đổi quyền admin: Đọc thông tin vai trò từ `vai_tro`, sau đó cập nhật (thêm/xóa) trong bảng liên kết `khach_hang_vai_tro`.
      - Cập nhật các thông tin (`hoTen`, `soDienThoai`, `hinhAnh`) trong bảng `khach_hang`.
  - `POST /admin/users/{id}/lock`: Khóa tài khoản người dùng.
    - **Tương tác Database:** Cập nhật cột `daKhoa` thành `true` trong bảng `khach_hang` cho người dùng tương ứng.
  - `POST /admin/users/{id}/unlock`: Mở khóa tài khoản người dùng.
    - **Tương tác Database:** Cập nhật cột `daKhoa` thành `false` trong bảng `khach_hang` cho người dùng tương ứng.
- **Bảng Cơ sở dữ liệu (SQL) liên quan:**
  - **Bảng `khach_hang` (từ Entity `User`):**
    - `id` (Số nguyên, Khóa chính, Tự tăng): Mã định danh duy nhất của người dùng.
    - `email` (Chuỗi, Duy nhất, Bắt buộc): Địa chỉ email (dùng để đăng nhập). (Đọc, Thêm, Sửa)
    - `matKhau` (Chuỗi, Bắt buộc): Mật khẩu đã được mã hóa. (Đọc để so sánh, Thêm, Sửa)
    - `hoTen` (Chuỗi, Bắt buộc): Họ và tên đầy đủ. (Đọc, Thêm, Sửa)
    - `soDienThoai` (Chuỗi, Bắt buộc): Số điện thoại. (Đọc, Thêm, Sửa)
    - `hinhAnh` (Chuỗi): Đường dẫn tới file ảnh đại diện. (Đọc, Thêm, Sửa)
    - `ngayTao` (Ngày giờ, Bắt buộc): Ngày giờ tài khoản được tạo. (Đọc, Thêm)
    - `daKichHoat` (Đúng/Sai, Bắt buộc): Trạng thái tài khoản đã được kích hoạt hay chưa. (Đọc, Thêm)
    - `loaiXacThuc` (Chuỗi, Bắt buộc): Phương thức đăng ký/xác thực (ví dụ: "EMAIL"). (Đọc, Thêm)
    - `maXacThuc` (Chuỗi): Mã dùng để kích hoạt tài khoản hoặc đặt lại mật khẩu. (Đọc, Thêm, Sửa)
    - `daKhoa` (Đúng/Sai, Mặc định: Sai): Trạng thái tài khoản có bị khóa hay không. (Đọc, Thêm, Sửa)
    - `otpExpiredAt` (Ngày giờ): Thời gian mã OTP hết hạn (dùng cho quên mật khẩu). (Đọc, Thêm, Sửa)
  - **Bảng `vai_tro` (từ Entity `VaiTro`):**
    - `id` (Số nguyên, Khóa chính, Tự tăng): Mã định danh duy nhất của vai trò.
    - `ten_vai_tro` (Chuỗi, Duy nhất, Bắt buộc): Tên vai trò (ví dụ: "ROLE_USER", "ROLE_ADMIN"). (Đọc, Thêm)
    - `mo_ta` (Chuỗi): Mô tả chi tiết về vai trò. (Đọc, Thêm)
  - **Bảng `khach_hang_vai_tro` (Bảng trung gian liên kết Người dùng và Vai trò):**
    - `khach_hang_id` (Số nguyên, Khóa chính, Khóa ngoại tới `khach_hang.id`): ID của người dùng. (Đọc, Thêm, Xóa)
    - `vai_tro_id` (Số nguyên, Khóa chính, Khóa ngoại tới `vai_tro.id`): ID của vai trò. (Đọc, Thêm, Xóa)
- **Truy vấn SQL/JPA nổi bật:**
  - Lấy người dùng theo email: `SELECT * FROM khach_hang WHERE email = :email_value`
  - Kiểm tra email đã tồn tại: `SELECT COUNT(*) FROM khach_hang WHERE email = :email_value`
  - Lấy vai trò theo tên: `SELECT * FROM vai_tro WHERE ten_vai_tro = :ten_vai_tro_value`
  - Các thao tác lưu (Thêm/Sửa), tìm theo ID, tìm tất cả, xóa theo ID được thực hiện qua các hàm chuẩn của JpaRepository, tương ứng với các lệnh SQL `INSERT/UPDATE`, `SELECT ... WHERE id = ?`, `SELECT * FROM ...`, `DELETE ... WHERE id = ?`.

---

### Chức năng 1.2: Quản lý Đơn hàng

- **Mô tả:** Admin có thể xem danh sách các đơn hàng đã được đặt trong hệ thống, xem chi tiết từng đơn, chỉnh sửa thông tin đơn hàng (ví dụ: cập nhật trạng thái, thông tin người nhận nếu cần thiết), và xác nhận khi đơn hàng đã được giao thành công.
- **File liên quan:**
  - **Controller:** `com.myapp.controller.admin.OrderAdminController.java`
  - **Service Interface:** `com.myapp.service.DonHangService.java`
  - **Service Implementation:** `com.myapp.service.impl.DonHangServiceImpl.java`
  - **Repository:** `com.myapp.repository.DonHangRepository.java`, `com.myapp.repository.UserRepository.java`
  - **Entity (Model):**
    - `com.myapp.model.DonHang.java` (ánh xạ tới bảng `don_hang`)
    - `com.myapp.model.ChiTietDonHang.java` (ánh xạ tới bảng `chi_tiet_don_hang`)
    - `com.myapp.model.User.java`
    - `com.myapp.model.Product.java`
    - `com.myapp.model.TrangThaiDonHang.java` (Enum - Định nghĩa các trạng thái đơn hàng)
  - **DTO:** `com.myapp.dto.DonHangDTO.java`, `com.myapp.dto.ChiTietDonHangDTO.java`
  - **Giao diện người dùng (Views):** `admin/orders.html` (danh sách), `admin/order-edit.html` (chỉnh sửa).
- **Đường dẫn truy cập (Endpoints) và Hoạt động:**
  - `GET /admin/orders`: Hiển thị danh sách tất cả đơn hàng.
    - **Tương tác Database:** Lấy tất cả dữ liệu từ bảng `don_hang` và các chi tiết liên quan từ `chi_tiet_don_hang`.
  - `GET /admin/orders/edit/{id}`: Hiển thị trang chỉnh sửa thông tin của một đơn hàng.
    - **Tương tác Database:** Lấy dữ liệu của một đơn hàng từ `don_hang` theo `id`.
  - `POST /admin/orders/edit/{id}`: Cập nhật thông tin đơn hàng.
    - **Tương tác Database:** Cập nhật các thông tin của đơn hàng trong bảng `don_hang` (ví dụ: `trangThai`, `ngayGiaoHang`, thông tin người nhận).
  - `GET /admin/orders/confirm/{id}`: Xác nhận đơn hàng đã được giao.
    - **Tương tác Database:** Cập nhật cột `trang_thai` thành `DA_GIAO` trong bảng `don_hang` cho đơn hàng tương ứng.
- **Bảng Cơ sở dữ liệu (SQL) liên quan:**
  - **Bảng `don_hang` (từ Entity `DonHang`):**
    - `id` (Số nguyên, Khóa chính, Tự tăng)
    - `khach_hang_id` (Số nguyên, Khóa ngoại tới `khach_hang.id`, Bắt buộc): ID của người đặt hàng.
    - `ngay_dat_hang` (Ngày giờ, Bắt buộc): Thời điểm đơn hàng được đặt.
    - `ngay_giao_hang` (Ngày giờ): Thời điểm dự kiến/thực tế giao hàng.
    - `ngay_nhan_hang` (Ngày giờ): Thời điểm khách nhận hàng.
    - `trang_thai` (Chuỗi, Bắt buộc): Trạng thái hiện tại của đơn hàng (ví dụ: "CHO_XAC_NHAN", "DA_GIAO").
    - `tong_tien` (Số thập phân, Bắt buộc): Tổng giá trị các sản phẩm trong đơn hàng (chưa tính phí ship, giảm giá cuối cùng).
    - `tong_thanh_toan` (Số thập phân, Bắt buộc): Tổng số tiền khách hàng phải trả cuối cùng.
    - `ghi_chu` (Chuỗi): Ghi chú thêm của khách hàng hoặc admin.
    - `ho_ten` (Chuỗi, Bắt buộc): Tên người nhận hàng.
    - `so_dien_thoai` (Chuỗi, Bắt buộc): Số điện thoại người nhận hàng.
    - `dia_chi` (Chuỗi, Bắt buộc): Địa chỉ giao hàng chi tiết.
    - `tinh_thanh` (Chuỗi, Bắt buộc): Tỉnh/Thành phố.
    - `quan_huyen` (Chuỗi, Bắt buộc): Quận/Huyện.
    - `phuong_xa` (Chuỗi, Bắt buộc): Phường/Xã.
    - `ma_buu_dien` (Chuỗi): Mã bưu điện (nếu có).
  - **Bảng `chi_tiet_don_hang` (từ Entity `ChiTietDonHang`):**
    - `id` (Số nguyên, Khóa chính, Tự tăng)
    - `don_hang_id` (Số nguyên, Khóa ngoại tới `don_hang.id`): Liên kết tới đơn hàng chứa mục này.
    - `product_id` (Số nguyên, Khóa ngoại tới `tac_pham.id`): Liên kết tới sản phẩm được đặt.
    - `so_luong` (Số nguyên): Số lượng sản phẩm.
    - `don_gia` (Số thập phân hoặc Số thực): Đơn giá của sản phẩm tại thời điểm đặt hàng.
- **Truy vấn SQL/JPA nổi bật (trong `DonHangRepository`):**
  - Tính tổng doanh thu theo trạng thái: `SELECT COALESCE(SUM(d.tongThanhToan),0) FROM DonHang d WHERE d.trangThai = :trang_thai_value`
  - Đếm số đơn hàng theo trạng thái: `SELECT COUNT(d) FROM DonHang d WHERE d.trangThai = :trang_thai_value`
  - Đếm tổng số đơn hàng: `SELECT COUNT(d) FROM DonHang d`
  - Lấy doanh thu theo ngày: `SELECT NEW map(CAST(d.ngayDatHang AS date) as ngay, COALESCE(SUM(d.tongThanhToan),0) as doanhThu) FROM DonHang d WHERE d.trangThai = :trang_thai_value AND d.ngayDatHang >= :ngay_bat_dau_value GROUP BY CAST(d.ngayDatHang AS date) ORDER BY ngay DESC`

---

### Chức năng 1.3: Quản lý Sản phẩm

- **Mô tả:** Admin có thể xem danh sách sản phẩm, thêm sản phẩm mới (sách) vào hệ thống, sửa thông tin chi tiết của sản phẩm hiện có, và xóa sản phẩm. Chức năng này cũng bao gồm việc tải lên và quản lý ảnh bìa cho mỗi sản phẩm.
- **File liên quan:**
  - **Controller:** `com.myapp.controller.admin.ProductAdminController.java`
  - **Service Interface:** `com.myapp.service.ProductService.java`
  - **Service Implementation:** `com.myapp.service.impl.ProductServiceImpl.java`
  - **Repository:** `com.myapp.repository.ProductRepository.java`
  - **Entity (Model):** `com.myapp.model.Product.java` (ánh xạ tới bảng `tac_pham`)
  - **DTO:** `com.myapp.dto.ProductDTO.java`
  - **Giao diện người dùng (Views):** `admin/products.html` (danh sách), `admin/product-form.html` (thêm/sửa).
- **Xử lý file ảnh:**
  - Ảnh bìa sản phẩm được tải lên sẽ được lưu vào thư mục `uploads/product-images/` (nằm bên ngoài thư mục `src` của dự án).
  - Tên file ảnh được tạo ngẫu nhiên và duy nhất.
  - Đường dẫn lưu trong database có dạng `/product-images/ten_file_anh.jpg` và được phục vụ như một tài nguyên tĩnh.
- **Đường dẫn truy cập (Endpoints) và Hoạt động:**
  - `GET /admin/products`: Hiển thị danh sách tất cả sản phẩm.
    - **Tương tác Database:** Lấy tất cả dữ liệu từ bảng `tac_pham`.
  - `GET /admin/products/add`: Hiển thị form để thêm sản phẩm mới.
  - `POST /admin/products/add`: Xử lý việc thêm sản phẩm mới.
    - **Tương tác Database:** Thêm một bản ghi mới vào bảng `tac_pham` với các thông tin được cung cấp.
  - `GET /admin/products/edit/{id}`: Hiển thị form để chỉnh sửa thông tin sản phẩm.
    - **Tương tác Database:** Lấy dữ liệu của một sản phẩm từ `tac_pham` theo `id`.
  - `POST /admin/products/edit/{id}`: Cập nhật thông tin sản phẩm.
    - **Tương tác Database:** Cập nhật các thông tin của sản phẩm trong bảng `tac_pham`.
  - `GET /admin/products/delete/{id}`: Xóa một sản phẩm khỏi hệ thống.
    - **Tương tác Database:** Xóa bản ghi tương ứng khỏi bảng `tac_pham` dựa theo `id`.
- **Bảng Cơ sở dữ liệu (SQL) liên quan:**
  - **Bảng `tac_pham` (từ Entity `Product`):**
    - `id` (Số nguyên, Khóa chính, Tự tăng)
    - `tua_de` (Chuỗi, Bắt buộc): Tựa đề/tên sản phẩm (sách).
    - `duong_dan` (Chuỗi, Bắt buộc, Duy nhất): Đường dẫn URL thân thiện (ví dụ: "dac-nhan-tam").
    - `tom_tat` (Chuỗi dài, Bắt buộc): Tóm tắt nội dung sản phẩm.
    - `noi_dung` (Chuỗi dài, Bắt buộc): Mô tả chi tiết sản phẩm.
    - `ngay_xuat_ban` (Ngày): Ngày xuất bản sách.
    - `ngay_cap_nhat` (Ngày giờ): Thời điểm thông tin sản phẩm được cập nhật lần cuối.
    - `da_xuat_ban` (Đúng/Sai, Bắt buộc, Mặc định: Sai): Trạng thái sản phẩm đã được công khai bán hay chưa.
    - `so_luong_ton` (Số nguyên): Số lượng sản phẩm còn lại trong kho.
    - `gia_nhap` (Số thập phân): Giá nhập sản phẩm.
    - `gia_ban` (Số thập phân): Giá bán sản phẩm cho khách hàng.
    - `phan_tram_giam_gia` (Số thập phân): Phần trăm giảm giá (nếu có).
    - `anh_bia` (Chuỗi, Bắt buộc): Đường dẫn tới file ảnh bìa sản phẩm.
    - `review_count` (Số nguyên, Mặc định: 0): Số lượt đánh giá sản phẩm.
    - `average_rating` (Số thập phân): Điểm đánh giá trung bình.
    - `length_cm`, `width_cm`, `height_cm` (Số thập phân): Kích thước sản phẩm.
    - `weight_kg` (Số thập phân): Cân nặng sản phẩm.
- **Truy vấn SQL/JPA nổi bật (trong `ProductRepository`):**
  - Tìm sản phẩm theo đường dẫn: `SELECT * FROM tac_pham WHERE duong_dan = :duong_dan_value`
  - Tìm sản phẩm theo tựa đề: `SELECT * FROM tac_pham WHERE tua_de = :tua_de_value`
  - Tìm kiếm sản phẩm (phân trang): `SELECT * FROM tac_pham WHERE tua_de LIKE '%keyword%' OR tom_tat LIKE '%keyword%' LIMIT ?, ?`

---

### Chức năng 1.4: Bảng điều khiển (Dashboard)

- **Mô tả:** Hiển thị trang tổng quan cho admin với các số liệu thống kê chính về hoạt động kinh doanh, bao gồm tổng doanh thu, tổng số đơn hàng, số đơn hàng đã hoàn thành, và biểu đồ doanh thu trong 7 ngày gần nhất.
- **File liên quan:**
  - **Controller:** `com.myapp.controller.admin.DashboardController.java`
  - **Service Interface:** `com.myapp.service.DonHangService.java` (sử dụng phương thức `getDashboardStats()`)
  - **Service Implementation:** `com.myapp.service.impl.DonHangServiceImpl.java`
  - **Repository:** `com.myapp.repository.DonHangRepository.java`
  - **Entity (Model):** `com.myapp.model.DonHang.java`, `com.myapp.model.TrangThaiDonHang.java`
  - **Giao diện người dùng (View):** `admin/dashboard.html`
- **Đường dẫn truy cập (Endpoints) và Hoạt động:**
  - `GET /admin/dashboard`: Hiển thị trang dashboard.
    - **Tương tác Database (thông qua `donHangService.getDashboardStats()`):**
      - Tính tổng doanh thu từ các đơn hàng có trạng thái "DA_GIAO".
      - Đếm tổng số đơn hàng.
      - Đếm số đơn hàng có trạng thái "DA_GIAO".
      - Lấy dữ liệu doanh thu theo ngày trong 7 ngày gần nhất cho các đơn hàng "DA_GIAO".
    - Dữ liệu doanh thu 7 ngày được chuyển đổi sang dạng JSON để vẽ biểu đồ trên giao diện.
- **Bảng Cơ sở dữ liệu (SQL) liên quan:**
  - Chủ yếu là bảng `don_hang`.
- **Truy vấn SQL/JPA nổi bật (trong `DonHangRepository` - đã được đề cập ở Chức năng 1.2 Quản lý Đơn hàng):**
  - `sumTongThanhToanByStatus`
  - `countByStatus`
  - `countAllOrders`
  - `getRevenueByDay`

---

## Phần 2: Chức năng Giao diện Lập trình Ứng dụng (API)

### Chức năng 2.1: Xác thực Người dùng (API)

- **Mô tả:** Cung cấp các điểm cuối API (endpoints) cho việc đăng ký tài khoản mới, đăng nhập, lấy thông tin người dùng, cập nhật và xóa người dùng. Các API này thường được sử dụng bởi các ứng dụng client (ví dụ: ứng dụng di động, frontend JavaScript) để tương tác với hệ thống.
- **File liên quan:**
  - **Controller:** `com.myapp.controller.api.AuthenticationController.java`
  - **Service Interface:** `com.myapp.service.UserService.java`
  - **Service Implementation:** `com.myapp.service.impl.UserServiceImpl.java`
  - **Repository:** `com.myapp.repository.UserRepository.java`, `com.myapp.repository.VaiTroRepository.java`
  - **Entity (Model):** `com.myapp.model.User.java`, `com.myapp.model.VaiTro.java`
  - **DTO:** `com.myapp.dto.UserDTO.java`
- **Đường dẫn API (Endpoints) và Hoạt động:**
  - `POST /api/auth/register`: Đăng ký tài khoản người dùng mới.
    - **Đầu vào:** Dữ liệu `UserDTO` trong body của request (bao gồm email, mật khẩu, họ tên, v.v.).
    - **Hoạt động:** Gọi `userService.register(userDTO)`.
    - **Tương tác Database:** (Như đã mô tả trong Chức năng 1.1 - phần `register` của `UserServiceImpl`)
      - Kiểm tra email tồn tại (`khach_hang`).
      - Thêm bản ghi mới vào `khach_hang`.
      - Thêm liên kết vai trò "ROLE_USER" trong `khach_hang_vai_tro`.
    - **Đầu ra:** `UserDTO` của người dùng vừa được tạo.
  - `POST /api/auth/login`: Đăng nhập người dùng.
    - **Đầu vào:** `UserDTO` chứa `email` và `matKhau` trong body request.
    - **Hoạt động:** Gọi `userService.login(email, matKhau)`.
    - **Tương tác Database:** (Như đã mô tả trong Chức năng 1.1 - phần `login` của `UserServiceImpl`)
      - Tìm người dùng theo email trong `khach_hang`.
      - So sánh mật khẩu.
    - **Đầu ra:** `UserDTO` của người dùng nếu đăng nhập thành công. (Lưu ý: API này có thể cần trả về JWT token thay vì chỉ UserDTO).
  - `GET /api/auth/{id}`: Lấy thông tin chi tiết của một người dùng theo ID.
    - **Hoạt động:** Gọi `userService.findById(id)`.
    - **Tương tác Database:** Lấy dữ liệu từ bảng `khach_hang` theo `id`.
    - **Đầu ra:** `UserDTO`.
  - `PUT /api/auth/{id}`: Cập nhật thông tin người dùng.
    - **Đầu vào:** `UserDTO` với thông tin cần cập nhật trong body request.
    - **Hoạt động:** Gọi `userService.update(id, userDTO)`.
    - **Tương tác Database:** Cập nhật bản ghi trong `khach_hang`.
    - **Đầu ra:** `UserDTO` đã được cập nhật.
  - `DELETE /api/auth/{id}`: Xóa người dùng.
    - **Hoạt động:** Gọi `userService.delete(id)`.
    - **Tương tác Database:** Xóa bản ghi người dùng khỏi `khach_hang`.
    - **Đầu ra:** Không có nội dung (HTTP 200 OK).
- **Bảng Cơ sở dữ liệu (SQL) liên quan:**
  - `khach_hang`, `vai_tro`, `khach_hang_vai_tro` (chi tiết ở Chức năng 1.1).
- **Lưu ý về Security:** Các API này cần được bảo vệ. Cơ chế bảo vệ sẽ được làm rõ khi phân tích phần Security.

---

### Chức năng 2.2: Quản lý Giỏ hàng (API)

- **Mô tả:** Cung cấp các API để người dùng (hoặc ứng dụng client) quản lý giỏ hàng của họ, bao gồm xem các sản phẩm trong giỏ, thêm sản phẩm, cập nhật số lượng, xóa sản phẩm, xóa toàn bộ giỏ hàng và xem tổng tiền. _Quan trọng: Cần đảm bảo các thao tác chỉ ảnh hưởng đến giỏ hàng của người dùng đã xác thực thực hiện request._
- **File liên quan:**
  - **Controller:** `com.myapp.controller.api.CartController.java`
  - **Service Interface:** `com.myapp.service.CartService.java`
  - **Service Implementation:** `com.myapp.service.impl.CartServiceImpl.java`
  - **Repository:** `com.myapp.repository.CartRepository.java`
  - **Entity (Model):** `com.myapp.model.Cart.java` (bảng `gio_hang_item`), `com.myapp.model.User.java` (bảng `khach_hang`), `com.myapp.model.Product.java` (bảng `tac_pham`)
  - **DTO:** `com.myapp.dto.CartItemDTO.java`
- **Đường dẫn API (Endpoints) và Hoạt động:**
  - `GET /api/cart`: Lấy các mục trong giỏ hàng của người dùng hiện tại.
    - **Tương tác Database:** `SELECT * FROM gio_hang_item WHERE khach_hang_id = ?` (cần logic để lấy `khach_hang_id` của user đang đăng nhập).
    - **Đầu ra:** `List<CartItemDTO>`.
  - `POST /api/cart/items`: Thêm sản phẩm vào giỏ.
    - **Đầu vào:** `CartItemDTO` (chứa `userEmail` (cần xem xét lại, nên lấy user từ context), `tacPhamId`, `soLuong`).
    - **Tương tác Database:**
      - Tìm `User` theo `userEmail` (hoặc user từ context).
      - Tìm `Product` theo `tacPhamId`.
      - Kiểm tra `SELECT * FROM gio_hang_item WHERE khach_hang_id = ? AND tac_pham_id = ?`.
      - Nếu có: `UPDATE gio_hang_item SET so_luong = so_luong + ? WHERE ...`.
      - Nếu không: `INSERT INTO gio_hang_item (khach_hang_id, tac_pham_id, so_luong) VALUES (?, ?, ?) `.
    - **Đầu ra:** `CartItemDTO`.
  - `PUT /api/cart/items/{id}`: Cập nhật số lượng.
    - **Đầu vào:** `id` (của `gio_hang_item`), `CartItemDTO` (chứa `soLuong` mới).
    - **Tương tác Database:** `UPDATE gio_hang_item SET so_luong = ? WHERE id = ?` (cần đảm bảo item này thuộc user đang đăng nhập).
    - **Đầu ra:** `CartItemDTO`.
  - `DELETE /api/cart/items/{id}`: Xóa mục khỏi giỏ.
    - **Đầu vào:** `id` (của `gio_hang_item`).
    - **Tương tác Database:** `DELETE FROM gio_hang_item WHERE id = ?` (cần đảm bảo item này thuộc user đang đăng nhập).
    - **Đầu ra:** HTTP 200 OK.
  - `DELETE /api/cart`: Xóa toàn bộ giỏ hàng của user hiện tại.
    - **Tương tác Database:** `DELETE FROM gio_hang_item WHERE khach_hang_id = ?` (cần logic để lấy `khach_hang_id` của user đang đăng nhập).
    - **Đầu ra:** HTTP 200 OK.
  - `GET /api/cart/total`: Lấy tổng tiền giỏ hàng của user hiện tại.
    - **Tương tác Database:** Lấy tất cả `gio_hang_item` của user, sau đó tính tổng.
    - **Đầu ra:** `BigDecimal`.
- **Bảng Cơ sở dữ liệu (SQL) liên quan:**
  - **Bảng `gio_hang_item` (từ Entity `Cart`):**
    - `id` (Số nguyên, PK, AI)
    - `khach_hang_id` (Số nguyên, FK tới `khach_hang.id`, Bắt buộc)
    - `tac_pham_id` (Số nguyên, FK tới `tac_pham.id`, Bắt buộc)
    - `so_luong` (Số nguyên, Bắt buộc)
- **Truy vấn SQL/JPA nổi bật (trong `CartRepository`):**
  - `findByUser(User user)`: `SELECT * FROM gio_hang_item WHERE khach_hang_id = ?`
  - `findByUserAndProduct(User user, Product product)`: `SELECT * FROM gio_hang_item WHERE khach_hang_id = ? AND tac_pham_id = ?`
- **Lưu ý quan trọng về Service & Security:**
  - Logic trong `CartServiceImpl` cần được rà soát và sửa đổi để đảm bảo các thao tác `getCartItems`, `clearCart`, `getCartTotal` và các thao tác thêm/sửa/xóa item đều được thực hiện trong context của người dùng đã xác thực, tránh ảnh hưởng đến dữ liệu của người dùng khác. Thông tin người dùng nên được lấy từ `SecurityContextHolder`.

---

### Chức năng 2.3: Quản lý Đơn hàng (API)

- **Mô tả:** Cung cấp các API để quản lý đơn hàng, bao gồm lấy danh sách đơn hàng (có phân trang, theo người dùng, theo trạng thái), xem chi tiết đơn hàng, tạo mới, cập nhật và xóa đơn hàng. _Quan trọng: Cần có cơ chế phân quyền rõ ràng. Admin có thể truy cập tất cả, người dùng thường chỉ nên truy cập đơn hàng của chính họ._
- **File liên quan:**
  - **Controller:** `com.myapp.controller.api.OrderController.java`
  - **Service Interface:** `com.myapp.service.DonHangService.java`
  - **Service Implementation:** `com.myapp.service.impl.DonHangServiceImpl.java` (Đã phân tích ở Chức năng 1.2)
  - **Repository:** `com.myapp.repository.DonHangRepository.java` (Đã phân tích ở Chức năng 1.2)
  - **Entity (Model):** `com.myapp.model.DonHang.java`, `com.myapp.model.ChiTietDonHang.java`, `com.myapp.model.User.java`, `com.myapp.model.Product.java`, `com.myapp.model.TrangThaiDonHang.java` (Đã phân tích ở Chức năng 1.2)
  - **DTO:** `com.myapp.dto.DonHangDTO.java`, `com.myapp.dto.ChiTietDonHangDTO.java` (Đã phân tích ở Chức năng 1.2)
- **Đường dẫn API (Endpoints) và Hoạt động:**
  - `GET /api/orders`: Lấy tất cả đơn hàng (phân trang).
    - **Quyền:** Admin.
    - **Tương tác Database:** `SELECT * FROM don_hang LIMIT ?, ?`.
    - **Đầu ra:** `Page<DonHangDTO>`.
  - `GET /api/orders/{id}`: Lấy chi tiết đơn hàng theo ID.
    - **Quyền:** Admin hoặc người dùng sở hữu đơn hàng.
    - **Tương tác Database:** `SELECT * FROM don_hang WHERE id = ?`.
    - **Đầu ra:** `DonHangDTO`.
  - `POST /api/orders`: Tạo đơn hàng mới.
    - **Quyền:** Người dùng đã xác thực.
    - **Đầu vào:** `DonHangDTO`.
    - **Tương tác Database:** `INSERT INTO don_hang (...) VALUES (...)`, `INSERT INTO chi_tiet_don_hang (...) VALUES (...)`.
    - **Đầu ra:** `DonHangDTO` đã tạo.
  - `PUT /api/orders/{id}`: Cập nhật đơn hàng.
    - **Lưu ý về Service & Security:**
      - Cập nhật/Xóa đơn hàng qua API bởi người dùng nên có giới hạn (ví dụ, chỉ được hủy khi đơn hàng ở trạng thái "CHO_XAC_NHAN").

---

### Chức năng 2.4: Quản lý Sản phẩm (API)

- **Mô tả:** Cung cấp các API để tương tác với dữ liệu sản phẩm. Bao gồm tạo, lấy thông tin (theo ID, slug, tựa đề, tất cả, tìm kiếm), cập nhật và xóa sản phẩm. Các API này có thể được dùng cho cả admin (quản lý sản phẩm) và người dùng (xem, tìm kiếm sản phẩm). Cần phân quyền phù hợp.
- **File liên quan:**
  - **Controller:** `com.myapp.controller.api.ProductController.java`
  - **Service Interface:** `com.myapp.service.ProductService.java` (Đã phân tích ở Chức năng 1.3)
  - **Service Implementation:** `com.myapp.service.impl.ProductServiceImpl.java` (Đã phân tích ở Chức năng 1.3)
  - **Repository:** `com.myapp.repository.ProductRepository.java` (Đã phân tích ở Chức năng 1.3)
  - **Entity (Model):** `com.myapp.model.Product.java` (bảng `tac_pham`) (Đã phân tích ở Chức năng 1.3)
  - **DTO:** `com.myapp.dto.ProductDTO.java` (Đã phân tích ở Chức năng 1.3)
- **Đường dẫn API (Endpoints) và Hoạt động:**
  - `POST /api/products`: Tạo sản phẩm mới.
    - **Quyền:** Admin.
    - **Đầu vào:** `ProductDTO`.
    - **Tương tác Database:** `INSERT INTO tac_pham (...) VALUES (...)`.
    - **Đầu ra:** `ProductDTO` đã tạo.
  - `GET /api/products/{id}`: Lấy sản phẩm theo ID.
    - **Quyền:** Công khai (cho người dùng xem) hoặc Admin.
    - **Tương tác Database:** `SELECT * FROM tac_pham WHERE id = ?`.
    - **Đầu ra:** `ProductDTO`.
  - `GET /api/products/slug/{duongDan}`: Lấy sản phẩm theo đường dẫn (slug).
    - **Quyền:** Công khai.
    - **Tương tác Database:** `SELECT * FROM tac_pham WHERE duong_dan = ?`.
    - **Đầu ra:** `ProductDTO`.
  - `GET /api/products/title/{tuaDe}`: Lấy sản phẩm theo tựa đề.
    - **Quyền:** Công khai.
    - **Tương tác Database:** `SELECT * FROM tac_pham WHERE tua_de = ?`.
    - **Đầu ra:** `ProductDTO`.
  - `GET /api/products`: Lấy tất cả sản phẩm (phân trang).
    - **Quyền:** Công khai.
    - **Tương tác Database:** `SELECT * FROM tac_pham LIMIT ?, ?`.
    - **Đầu ra:** `Page<ProductDTO>`.
  - `GET /api/products/search`: Tìm kiếm sản phẩm (phân trang).
    - **Quyền:** Công khai.
    - **Đầu vào:** `keyword`, `page`, `size`.
    - **Tương tác Database:** `SELECT * FROM tac_pham WHERE tua_de LIKE '%keyword%' OR tom_tat LIKE '%keyword%' LIMIT ?, ?`.
    - **Đầu ra:** `Page<ProductDTO>`.
  - `PUT /api/products/{id}`: Cập nhật sản phẩm.
    - **Quyền:** Admin.
    - **Đầu vào:** `id` sản phẩm, `ProductDTO` chứa thông tin cập nhật.
    - **Tương tác Database:** `UPDATE tac_pham SET ... WHERE id = ?`.
    - **Đầu ra:** `ProductDTO` đã cập nhật.
  - `DELETE /api/products/{id}`: Xóa sản phẩm.
    - **Quyền:** Admin.
    - **Tương tác Database:** `DELETE FROM tac_pham WHERE id = ?`.
    - **Đầu ra:** HTTP 200 OK.
- **Bảng Cơ sở dữ liệu (SQL) liên quan:**
  - `tac_pham` (chi tiết ở Chức năng 1.3).
- **Lưu ý về Security:**
  - Các API tạo, sửa, xóa sản phẩm (`POST`, `PUT`, `DELETE`) phải được bảo vệ và chỉ cho phép Admin truy cập.
  - Các API lấy thông tin (`GET`) có thể để công khai cho người dùng xem sản phẩm.

---

## Phần 3: Chức năng Người dùng (Giao diện Web - Thymeleaf)

### Chức năng 3.1: Xác thực Người dùng (Đăng nhập, Đăng ký, Quên mật khẩu)

- **Mô tả:** Cung cấp các trang và xử lý logic cho người dùng thực hiện các hành động liên quan đến tài khoản như đăng nhập, đăng ký, và quên mật khẩu (bao gồm gửi OTP và đặt lại mật khẩu mới).
- **File liên quan:**
  - **Controller:** `com.myapp.controller.user.AuthController.java`
  - **Service Interface:** `com.myapp.service.UserService.java` (Đã phân tích ở Chức năng 1.1)
  - **Service Implementation:** `com.myapp.service.impl.UserServiceImpl.java` (Đã phân tích ở Chức năng 1.1)
  - **Repository:** `com.myapp.repository.UserRepository.java`, `com.myapp.repository.VaiTroRepository.java` (Đã phân tích ở Chức năng 1.1)
  - **Entity (Model):** `com.myapp.model.User.java` (bảng `khach_hang`)
  - **DTO:** `com.myapp.dto.UserDTO.java`
  - **Giao diện người dùng (View - Thymeleaf):**
    - `user/login.html`: Trang đăng nhập.
    - `user/register.html`: Trang đăng ký.
    - `user/forgot-password.html`: Trang yêu cầu gửi OTP quên mật khẩu.
    - `user/enter-otp.html`: Trang nhập OTP và mật khẩu mới.
- **Đường dẫn truy cập (Endpoints) và Hoạt động:**
  - `GET /login`: Hiển thị trang đăng nhập.
    - **Tương tác Database:** Không trực tiếp (form sẽ POST tới endpoint xử lý đăng nhập của Spring Security).
  - `GET /register`: Hiển thị trang đăng ký.
    - **Tương tác Database:** Không.
  - `POST /do-register`: Xử lý đăng ký tài khoản.
    - **Đầu vào:** `UserDTO` từ form.
    - **Hoạt động:** Gọi `userService.register(userDTO)`.
    - **Tương tác Database (qua Service):**
      - Kiểm tra email tồn tại (`khach_hang`).
      - Thêm bản ghi mới vào `khach_hang`.
      - Thêm liên kết vai trò "ROLE_USER" trong `khach_hang_vai_tro`.
    - **Kết quả:** Chuyển hướng tới `/login` nếu thành công, hoặc hiển thị lại trang đăng ký với lỗi.
  - `GET /forgot-password`: Hiển thị trang quên mật khẩu.
    - **Tương tác Database:** Không.
  - `POST /forgot-password`: Xử lý yêu cầu gửi OTP.
    - **Đầu vào:** `email`.
    - **Hoạt động:** Gọi `userService.sendPasswordResetOtp(email)`.
    - **Tương tác Database (qua Service):**
      - Tìm người dùng theo `email` trong `khach_hang`.
      - Tạo OTP, lưu OTP và thời gian hết hạn vào `khach_hang`.
      - Gửi email chứa OTP.
    - **Kết quả:** Chuyển hướng tới trang nhập OTP (`/enter-otp`) hoặc báo lỗi.
  - `GET /enter-otp`: Hiển thị trang nhập OTP và mật khẩu mới (thường kèm theo email).
    - **Tương tác Database:** Không.
  - `POST /reset-password`: Xử lý đặt lại mật khẩu bằng OTP.
    - **Đầu vào:** `email`, `otp`, `newPassword`.
    - **Hoạt động:** Gọi `userService.resetPasswordWithOtp(email, otp, newPassword)`.
    - **Tương tác Database (qua Service):**
      - Tìm người dùng theo `email`.
      - Xác thực OTP và thời gian hiệu lực.
      - Cập nhật `matKhau` mới (đã mã hóa) và xóa OTP trong `khach_hang`.
    - **Kết quả:** Chuyển hướng tới `/login` nếu thành công, hoặc hiển thị lại trang nhập OTP với lỗi.
- **Bảng Cơ sở dữ liệu (SQL) liên quan:**
  - `khach_hang`, `vai_tro`, `khach_hang_vai_tro` (chi tiết ở Chức năng 1.1).
- **Lưu ý về Security:**
  - Việc đăng nhập thực tế được xử lý bởi Spring Security (cấu hình trong `SecurityConfig.java`). Controller này chủ yếu cung cấp giao diện và xử lý các luồng phụ trợ như đăng ký, quên mật khẩu.
  - Mật khẩu luôn được mã hóa trước khi lưu.
  - Cơ chế OTP cần đảm bảo tính bảo mật (OTP ngẫu nhiên, có thời gian hết hạn ngắn).

---

### Chức năng 3.2: Quản lý Giỏ hàng (Giao diện Web)

- **Mô tả:** Cho phép người dùng đã đăng nhập xem giỏ hàng của họ, thêm sản phẩm vào giỏ, xóa sản phẩm khỏi giỏ, và cập nhật số lượng sản phẩm trong giỏ hàng thông qua giao diện web.
- **File liên quan:**
  - **Controller:** `com.myapp.controller.user.UserCartController.java`
  - **Service Interface:** `com.myapp.service.CartService.java`, `com.myapp.service.UserService.java`, `com.myapp.service.ProductService.java`
  - **Service Implementation:** `com.myapp.service.impl.CartServiceImpl.java` (cần rà soát logic như đã lưu ý ở Chức năng 2.2), `com.myapp.service.impl.UserServiceImpl.java`, `com.myapp.service.impl.ProductServiceImpl.java`
  - **Repository:** `com.myapp.repository.CartRepository.java`, `com.myapp.repository.UserRepository.java`, `com.myapp.repository.ProductRepository.java`
  - **Entity (Model):** `com.myapp.model.Cart.java` (bảng `gio_hang_item`), `com.myapp.model.User.java` (bảng `khach_hang`), `com.myapp.model.Product.java` (bảng `tac_pham`)
  - **DTO:** `com.myapp.dto.UserDTO.java`, `com.myapp.dto.ProductDTO.java`, `com.myapp.dto.CartItemDTO.java` (dùng trong service)
  - **Giao diện người dùng (View - Thymeleaf):** `user/cart.html`
- **Đường dẫn truy cập (Endpoints) và Hoạt động:**
  - `GET /user/cart`: Hiển thị trang giỏ hàng.
    - **Hoạt động:**
      - Lấy email người dùng từ `Authentication`.
      - Gọi `userService.findByEmail(email)` để lấy `UserDTO`.
      - Gọi `cartService.getCartByUser(userDTO)` để lấy danh sách `Cart`.
      - Gọi `cartService.calculateTotal(cartItems)` để tính tổng tiền.
    - **Tương tác Database (qua Services):**
      - `SELECT * FROM khach_hang WHERE email = ?`.
      - `SELECT * FROM gio_hang_item WHERE khach_hang_id = ?` (và join với `tac_pham`).
    - **View:** Hiển thị danh sách sản phẩm, số lượng, giá, và tổng tiền.
  - `POST /user/cart/add`: Thêm sản phẩm vào giỏ hàng.
    - **Đầu vào:** `productId`, `quantity`.
    - **Hoạt động:**
      - Lấy `UserDTO` từ `Authentication`.
      - Lấy `ProductDTO` từ `productService.findById(productId)`.
      - Gọi `cartService.addToCart(userDTO, productDTO, quantity)`.
    - **Tương tác Database (qua Service):** Tìm `User`, `Product`; Kiểm tra/Thêm/Cập nhật `gio_hang_item`.
    - **Kết quả:** Chuyển hướng về `/user/cart`.
  - `POST /user/cart/remove/{id}` (@ResponseBody): Xóa một mục khỏi giỏ hàng.
    - **Đầu vào:** `id` (ID của `Cart` item).
    - **Hoạt động:** Gọi `cartService.removeFromCart(id)`.
    - **Tương tác Database (qua Service):** `DELETE FROM gio_hang_item WHERE id = ?` (trong context user hiện tại).
    - **Kết quả:** Trả về "success" hoặc "error".
  - `POST /user/cart/update/{id}`: Cập nhật số lượng một mục trong giỏ hàng.
    - **Đầu vào:** `id` (ID của `Cart` item), `quantity`.
    - **Hoạt động:** Gọi `cartService.updateCartQuantity(id, quantity)`.
    - **Tương tác Database (qua Service):** `UPDATE gio_hang_item SET so_luong = ? WHERE id = ?` (trong context user hiện tại).
    - **Kết quả:** Chuyển hướng về `/user/cart`.
- **Bảng Cơ sở dữ liệu (SQL) liên quan:**
  - `gio_hang_item`, `khach_hang`, `tac_pham`.
- **Lưu ý về Service & Security:**
  - Tất cả các thao tác trong `CartService` phải dựa trên người dùng đã xác thực (`Authentication`).
  - `cartService.getCartByUser(userDTO)` nên trả về danh sách đối tượng đầy đủ thông tin sản phẩm.
  - `cartService.calculateTotal(cartItems)` cần tính toán chính xác.
  - `removeFromCart` và `updateCartQuantity` trong service cần đảm bảo chỉ thao tác trên item của người dùng hiện tại.

---

### Chức năng 3.3: Quy trình Thanh toán Đơn hàng (Giao diện Web)

- **Mô tả:** Xử lý toàn bộ quy trình người dùng đặt hàng và thanh toán. Bao gồm hiển thị trang thanh toán với thông tin giỏ hàng, địa chỉ, lựa chọn phương thức vận chuyển và thanh toán (COD, VNPay). Xử lý việc tạo đơn hàng, chuyển hướng đến cổng thanh toán (VNPay), và nhận kết quả trả về từ cổng thanh toán.
- **File liên quan:**
  - **Controller:** `com.myapp.controller.user.UserCheckoutController.java`
  - **Service Interfaces:**
    - `com.myapp.service.CartService.java`
    - `com.myapp.service.DonHangUserService.java` (hoặc một phần của `DonHangService`)
    - `com.myapp.service.UserService.java`
    - `com.myapp.service.ShippingMethodService.java`
    - `com.myapp.service.PaymentMethodService.java`
    - `com.myapp.service.VNPayService.java`
  - **Service Implementations:** Các lớp implement tương ứng (ví dụ: `DonHangUserServiceImpl`, `VNPayServiceImpl`).
  - **Repository:** `DonHangRepository`, `ChiTietDonHangRepository`, `UserRepository`, `CartRepository`, `ShippingMethodRepository`, `PaymentMethodRepository`.
  - **Entity (Model):** `DonHang`, `ChiTietDonHang`, `User`, `Cart`, `Product`, `ShippingMethod`, `PaymentMethod`, `TrangThaiDonHang` (Enum).
  - **DTO:** `UserDTO`.
  - **Giao diện người dùng (Views - Thymeleaf):**
    - `user/checkout.html`: Trang điền thông tin và chọn phương thức thanh toán.
    - `user/payment-success.html`: Trang thông báo thanh toán thành công.
    - `user/payment-fail.html`: Trang thông báo thanh toán thất bại.
- **Đường dẫn truy cập (Endpoints) và Hoạt động:**
  - `GET /user/checkout`: Hiển thị trang thanh toán.
    - **Hoạt động:** Lấy thông tin người dùng, giỏ hàng, các PTVC và PTTT; tính tổng tiền.
    - **Tương tác Database:** Lấy dữ liệu từ `khach_hang`, `gio_hang_item`, `shipping_method`, `payment_method`.
    - **View:** Hiển thị sản phẩm, form địa chỉ, lựa chọn vận chuyển, thanh toán.
  - `POST /user/checkout`: Xử lý đặt hàng.
    - **Đầu vào:** `shippingAddress`, `phoneNumber`, `paymentMethod` (COD/VNPAY), `shippingMethodId`, `note`.
    - **Hoạt động (COD):**
      - Gọi `donHangUserService.createOrder(...)` (trạng thái "CHO_XAC_NHAN").
      - Xóa giỏ hàng.
      - **Tương tác Database:** `INSERT` vào `don_hang`, `chi_tiet_don_hang`. `DELETE` từ `gio_hang_item`.
      - **Kết quả:** Chuyển hướng đến `/user/checkout/payment-success`.
    - **Hoạt động (VNPAY):**
      - Gọi `donHangUserService.createOrderWithStatus(...)` (trạng thái "CHO_THANH_TOAN").
      - Gọi `vnpayService.createPaymentUrl(...)`.
      - Xóa giỏ hàng.
      - **Tương tác Database:** Tương tự COD, trạng thái đơn hàng khác.
      - **Kết quả:** Chuyển hướng người dùng đến URL của VNPay.
  - `GET /user/checkout/payment/vnpay-return`: Xử lý kết quả VNPay trả về.
    - **Đầu vào:** Các tham số từ VNPay.
    - **Hoạt động:** Xác thực chữ ký; Lấy đơn hàng; Kiểm tra trạng thái; Cập nhật trạng thái đơn hàng ("DA_THANH_TOAN" hoặc "THANH_TOAN_THAT_BAI").
    - **Tương tác Database:** `SELECT` và `UPDATE` bảng `don_hang`.
    - **Kết quả:** Chuyển hướng đến trang success/fail.
  - `GET /user/checkout/payment-success`: Hiển thị trang thông báo thành công.
  - `GET /user/checkout/payment-fail`: Hiển thị trang thông báo thất bại.
- **Bảng Cơ sở dữ liệu (SQL) liên quan:**
  - `don_hang`, `chi_tiet_don_hang`, `khach_hang`, `gio_hang_item`, `shipping_method`, `payment_method`.
  - **Bảng `shipping_method`:** `id` (PK), `ten_phuong_thuc` (VARCHAR), `mo_ta` (TEXT), `phi_van_chuyen` (DECIMAL), `dang_hoat_dong` (BOOLEAN).
  - **Bảng `payment_method`:** `id` (PK), `ten_phuong_thuc` (VARCHAR), `mo_ta` (TEXT), `dang_hoat_dong` (BOOLEAN).
- **Lưu ý quan trọng:**
  - `DonHangUserService` xử lý logic tạo đơn, tính tổng tiền cuối cùng.
  - Xóa giỏ hàng sau khi tạo đơn thành công.
  - Xử lý cẩn thận các trường hợp lỗi.
  - Đảm bảo an toàn cho tham số với VNPay.

---

### Chức năng 3.4: Quản lý Tài khoản Người dùng (Giao diện Web)

- **Mô tả:** Cho phép người dùng đã đăng nhập xem và quản lý thông tin tài khoản cá nhân của họ, bao gồm xem thông tin, đổi mật khẩu, và cập nhật số điện thoại.
- **File liên quan:**
  - **Controller:** `com.myapp.controller.user.UserAccountController.java`
  - **Service Interface:** `com.myapp.service.UserService.java`
  - **Service Implementation:** `com.myapp.service.impl.UserServiceImpl.java`
  - **Repository:** `com.myapp.repository.UserRepository.java`
  - **Entity (Model):** `com.myapp.model.User.java` (bảng `khach_hang`)
  - **DTO:** `com.myapp.dto.UserDTO.java`
  - **Giao diện người dùng (View - Thymeleaf):** `user/account.html`
- **Đường dẫn truy cập (Endpoints) và Hoạt động:**
  - `GET /user/account`: Hiển thị trang thông tin tài khoản.
    - **Hoạt động:** Lấy `UserDTO` của người dùng đang đăng nhập.
    - **Tương tác Database (qua Service):** `SELECT * FROM khach_hang WHERE email = ?`.
    - **View:** Hiển thị thông tin và các form thay đổi.
  - `POST /user/account/change-password`: Xử lý đổi mật khẩu.
    - **Đầu vào:** `oldPassword`, `newPassword`.
    - **Hoạt động:** Gọi `userService.changePassword(email, oldPassword, newPassword)`.
    - **Tương tác Database (qua Service):** Kiểm tra mật khẩu cũ, cập nhật mật khẩu mới trong `khach_hang`.
    - **Kết quả:** Hiển thị lại trang `user/account` với thông báo.
  - `POST /user/account/update-phone`: Xử lý cập nhật số điện thoại.
    - **Đầu vào:** `soDienThoai`.
    - **Hoạt động:** Gọi `userService.update(...)` hoặc phương thức chuyên biệt.
    - **Tương tác Database (qua Service):** `UPDATE khach_hang SET so_dien_thoai = ? WHERE id = ?`.
    - **Kết quả:** Hiển thị lại trang `user/account` với thông báo.
- **Bảng Cơ sở dữ liệu (SQL) liên quan:**
  - `khach_hang` (chi tiết ở Chức năng 1.1).
- **Lưu ý về Security:**
  - Yêu cầu xác thực cho tất cả endpoints.
  - Xác thực mật khẩu cũ khi đổi mật khẩu.

---

### Chức năng 3.5: Quản lý Đơn hàng của Người dùng (Giao diện Web)

- **Mô tả:** Cho phép người dùng đã đăng nhập xem danh sách các đơn hàng họ đã đặt, xem chi tiết từng đơn hàng, và thực hiện một số thao tác như hủy đơn hàng (nếu điều kiện cho phép).
- **File liên quan:**
  - **Controller:** `com.myapp.controller.user.UserOrderController.java`
  - **Service Interfaces:** `com.myapp.service.DonHangUserService.java`, `com.myapp.service.UserService.java`
  - **Service Implementations:** `DonHangUserServiceImpl` (hoặc tương đương), `UserServiceImpl`.
  - **Repository:** `DonHangRepository`, `UserRepository`, `ChiTietDonHangRepository`.
  - **Entity (Model):** `DonHang`, `ChiTietDonHang`, `User`, `Cart`, `Product`, `TrangThaiDonHang` (Enum).
  - **DTO:** `UserDTO`, `DonHangDTO` (trả về từ service).
  - **Giao diện người dùng (Views - Thymeleaf):**
    - `user/orders/list.html`: Trang danh sách đơn hàng.
    - `user/orders/detail.html`: Trang chi tiết đơn hàng.
- **Đường dẫn truy cập (Endpoints) và Hoạt động:**
  - `GET /user/orders`: Hiển thị danh sách đơn hàng của người dùng.
    - **Hoạt động:** Lấy `UserDTO`, gọi `donHangUserService.getOrdersByUser(userDTO)`.
    - **Tương tác Database (qua Service):** `SELECT * FROM don_hang WHERE khach_hang_id = ? ORDER BY ngay_dat_hang DESC`.
    - **View:** Hiển thị danh sách tóm tắt đơn hàng.
  - `GET /user/orders/{id}`: Hiển thị chi tiết một đơn hàng.
    - **Đầu vào:** `id` đơn hàng.
    - **Hoạt động:** Lấy `UserDTO`, `DonHangDTO`. **Kiểm tra quyền sở hữu đơn hàng.**
    - **Tương tác Database (qua Service):** `SELECT * FROM don_hang WHERE id = ?` (join chi tiết).
    - **View:** Hiển thị thông tin chi tiết đơn hàng.
  - `POST /user/orders/{id}/cancel`: Xử lý yêu cầu hủy đơn hàng.
    - **Đầu vào:** `id` đơn hàng.
    - **Hoạt động:** Lấy `UserDTO`, gọi `donHangUserService.cancelOrder(id, userDTO)` (Service kiểm tra điều kiện hủy).
    - **Tương tác Database (qua Service):** Kiểm tra và `UPDATE don_hang SET trang_thai = 'DA_HUY'`.
    - **Kết quả:** Chuyển hướng về trang chi tiết đơn hàng với thông báo.
- **Bảng Cơ sở dữ liệu (SQL) liên quan:**
  - `don_hang`, `chi_tiet_don_hang`, `khach_hang`.
- **Lưu ý về Service & Security:**
  - Luôn xác minh quyền sở hữu đơn hàng.
  - Logic hủy đơn hàng trong Service cần định nghĩa rõ điều kiện.
  - Xem xét quy trình hoàn tiền nếu hủy đơn đã thanh toán.

---

### Chức năng 3.6: Trang chủ và Hiển thị Sản phẩm (Giao diện Web)

- **Mô tả:** Hiển thị trang chủ cho người dùng, thường bao gồm danh sách các sản phẩm nổi bật hoặc mới nhất.
- **File liên quan:**
  - **Controller:** `com.myapp.controller.user.HomeController.java`
  - **Service Interface:** `com.myapp.service.ProductService.java`
  - **Service Implementation:** `com.myapp.service.impl.ProductServiceImpl.java`
  - **Repository:** `com.myapp.repository.ProductRepository.java`
  - **Entity (Model):** `com.myapp.model.Product.java` (bảng `tac_pham`)
  - **DTO:** `com.myapp.dto.ProductDTO.java` (trả về từ service)
  - **Giao diện người dùng (View - Thymeleaf):** `user/home.html`
- **Đường dẫn truy cập (Endpoints) và Hoạt động:**
  - `GET /`
  - `GET /user/home`: Hiển thị trang chủ.
    - **Hoạt động:** Lấy 12 sản phẩm mới nhất (sắp xếp theo `id` giảm dần).
    - **Tương tác Database (qua Service):** `SELECT * FROM tac_pham WHERE da_xuat_ban = true ORDER BY id DESC LIMIT 12` (Nên lọc theo `da_xuat_ban`).
    - **View:** Hiển thị danh sách sản phẩm.
- **Bảng Cơ sở dữ liệu (SQL) liên quan:**
  - `tac_pham` (chi tiết ở Chức năng 1.3).
- **Lưu ý:**
  - Trang chủ có thể được làm phong phú hơn (sản phẩm bán chạy, giảm giá, v.v.).
  - Đảm bảo chỉ hiển thị sản phẩm `da_xuat_ban = true`.

---

## Phần 4: Cấu hình An ninh (Spring Security)

- **Mô tả:** Định nghĩa quy tắc bảo mật: xác thực, phân quyền, form đăng nhập, đăng xuất, session, và tích hợp JWT cho API.
- **File liên quan chính:**
  - `com.myapp.security.SecurityConfig.java`
  - `com.myapp.security.CustomUserDetailsService.java`: Tải thông tin user từ DB.
    - **Mục đích:** Implement `UserDetailsService` của Spring Security.
    - **Logic:** Nhận email, tìm `User` trong DB (`userRepository.findByEmail`). Nếu không thấy hoặc tài khoản bị khóa, ném exception. Chuyển đổi `VaiTro` thành `GrantedAuthority`. Trả về `CustomUserDetails`.
  - `com.myapp.security.CustomUserDetails.java`: Chứa thông tin user (email, mật khẩu, quyền).
  - `com.myapp.security.JwtAuthenticationFilter.java`: Xử lý JWT trong request (cho API).
  - `com.myapp.security.JwtService.java` (hoặc `JwtUtil`): Tạo và xác thực JWT.
  - Models: `User.java`, `VaiTro.java`.
  - Repository: `UserRepository.java`.
- **Cấu hình chính trong `securityFilterChain` (`SecurityConfig.java`):**
  - **CSRF:** Vô hiệu hóa.
  - **Phân quyền Request (`authorizeHttpRequests`):**
    - **Công khai (Permit All):** `/webjars/**`, `/`, `/products` (cần xem lại), `/login`, `/register`, `/css/**`, `/js/**`, và `anyRequest()` (cần rà soát kỹ).
    - **Yêu cầu xác thực:** `/cart`, `/account` (và các đường dẫn con).
    - **Yêu cầu quyền Admin:** `/admin/**`.
  - **Form Đăng nhập (`formLogin`):**
    - Trang: `/login`, Xử lý: `/do-login`.
    - Tham số: `username` (email), `matKhau`.
    - Thành công: Admin -> `/admin/dashboard`, User -> `/`.
    - Thất bại: `/login?error=true`.
  - **Đăng xuất (`logout`):** URL: `/logout`, Thành công: `/`.
  - **Session:** `SessionCreationPolicy.IF_REQUIRED`.
  - **Nhà cung cấp Xác thực:** `DaoAuthenticationProvider` (dùng `CustomUserDetailsService` & `BCryptPasswordEncoder`).
  - **JWT Filter:** `JwtAuthenticationFilter` được thêm vào chuỗi filter.
- **Luồng xác thực (Form Login):** User nhập credentials -> POST tới `/do-login` -> `DaoAuthenticationProvider` (qua `CustomUserDetailsService`) kiểm tra DB -> Thành công/Thất bại -> Chuyển hướng.
