package com.myapp.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import com.myapp.model.TrangThaiDonHang;

/**
 * Lớp DonHang đại diện cho một đơn hàng trong hệ thống
 * Sử dụng các annotation của Lombok:
 * - @Data: tự động tạo getter, setter, equals, hashCode và toString
 * - @Builder: cho phép sử dụng Builder pattern
 * - @NoArgsConstructor: tạo constructor không tham số
 * - @AllArgsConstructor: tạo constructor với tất cả tham số
 * 
 * @Entity - Đánh dấu đây là một entity JPA
 * @Table - Chỉ định tên bảng trong cơ sở dữ liệu là "don_hang"
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "don_hang")
public class DonHang {
    /**
     * ID của đơn hàng, được tự động tăng
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Quan hệ nhiều-một với bảng User
     * Mỗi đơn hàng thuộc về một người dùng
     * Không được phép null
     */
    @ManyToOne
    @JoinColumn(name = "khach_hang_id", nullable = false)
    private User user;

    /**
     * Thời điểm đặt hàng
     * Không được phép null
     */
    @Column(name = "ngay_dat_hang", nullable = false)
    private LocalDateTime ngayDatHang;

    /**
     * Thời điểm giao hàng
     * Có thể null nếu đơn hàng chưa được giao
     */
    @Column(name = "ngay_giao_hang")
    private LocalDateTime ngayGiaoHang;

    /**
     * Thời điểm nhận hàng
     * Có thể null nếu đơn hàng chưa được nhận
     */
    @Column(name = "ngay_nhan_hang")
    private LocalDateTime ngayNhanHang;

    /**
     * Trạng thái của đơn hàng
     * Ví dụ: CHỜ_XÁC_NHẬN, ĐANG_GIAO, ĐÃ_GIAO, ĐÃ_HỦY
     * Không được phép null
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "trang_thai", nullable = false)
    private TrangThaiDonHang trangThai;

    /**
     * Tổng tiền hàng trước khi áp dụng các khoản giảm giá
     * Định dạng: 10 chữ số, 2 số thập phân
     * Không được phép null
     */
    @Column(name = "tong_tien", nullable = false, precision = 10, scale = 2)
    private BigDecimal tongTien;

    /**
     * Tổng tiền phải thanh toán sau khi áp dụng các khoản giảm giá
     * Định dạng: 10 chữ số, 2 số thập phân
     * Không được phép null
     */
    @Column(name = "tong_thanh_toan", nullable = false, precision = 10, scale = 2)
    private BigDecimal tongThanhToan;

    /**
     * Ghi chú thêm về đơn hàng
     * Có thể null
     */
    @Column(name = "ghi_chu")
    private String ghiChu;

    /**
     * Họ tên người nhận hàng
     * Không được phép null
     */
    @Column(name = "ho_ten", nullable = false)
    private String hoTen;

    /**
     * Số điện thoại người nhận hàng
     * Không được phép null
     */
    @Column(name = "so_dien_thoai", nullable = false)
    private String soDienThoai;

    /**
     * Địa chỉ chi tiết người nhận hàng
     * Không được phép null
     */
    @Column(name = "dia_chi", nullable = false)
    private String diaChi;

    /**
     * Tỉnh/Thành phố của người nhận hàng
     * Không được phép null
     */
    @Column(name = "tinh_thanh", nullable = false)
    private String tinhThanh;

    /**
     * Quận/Huyện của người nhận hàng
     * Không được phép null
     */
    @Column(name = "quan_huyen", nullable = false)
    private String quanHuyen;

    /**
     * Phường/Xã của người nhận hàng
     * Không được phép null
     */
    @Column(name = "phuong_xa", nullable = false)
    private String phuongXa;

    /**
     * Mã bưu điện của địa chỉ nhận hàng
     * Có thể null
     */
    @Column(name = "ma_buu_dien")
    private String maBuuDien;

    /**
     * Danh sách chi tiết các sản phẩm trong đơn hàng
     * Quan hệ một-nhiều với bảng ChiTietDonHang
     * CascadeType.ALL: tự động cập nhật/xóa các chi tiết khi đơn hàng thay đổi
     * orphanRemoval: tự động xóa các chi tiết không còn liên kết với đơn hàng
     */
    @OneToMany(mappedBy = "donHang", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChiTietDonHang> chiTietDonHangs;
} 