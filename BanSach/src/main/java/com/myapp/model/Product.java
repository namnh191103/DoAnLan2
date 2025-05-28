package com.myapp.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Lớp Product đại diện cho một tác phẩm/sản phẩm trong hệ thống
 * Sử dụng Lombok @Data để tự động tạo các phương thức getter, setter, equals, hashCode và toString
 * 
 * @Entity - Đánh dấu đây là một entity JPA
 * @Table - Chỉ định tên bảng trong cơ sở dữ liệu là "tac_pham"
 */
@Data
@Entity
@Table(name = "tac_pham")
public class Product {
    /**
     * ID của tác phẩm, được tự động tăng
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // Khóa chính, tự động tăng
    
    /**
     * Tựa đề của tác phẩm
     * Không được phép null
     */
    @Column(name = "tua_de", nullable = false)
    private String tuaDe; // Tựa đề tác phẩm, nên kiểm tra không để trống
    
    /**
     * Đường dẫn URL thân thiện cho tác phẩm
     * Không được phép null và phải là duy nhất
     */
    @Column(name = "duong_dan", nullable = false, unique = true)
    private String duongDan; // Đường dẫn URL thân thiện, phải duy nhất, nên kiểm tra hợp lệ
    
    /**
     * Tóm tắt nội dung tác phẩm
     * Không được phép null
     * Sử dụng kiểu dữ liệu nvarchar(max) để hỗ trợ Unicode
     */
    @Column(name = "tom_tat", nullable = false, columnDefinition = "nvarchar(max)")
    private String tomTat; // Tóm tắt nội dung, nên kiểm tra không để trống
    
    /**
     * Nội dung chi tiết của tác phẩm
     * Không được phép null
     * Sử dụng kiểu dữ liệu nvarchar(max) để hỗ trợ Unicode
     */
    @Column(name = "noi_dung", nullable = false, columnDefinition = "nvarchar(max)")
    private String noiDung; // Nội dung chi tiết, nên kiểm tra không để trống
    
    /**
     * Ngày xuất bản của tác phẩm
     * Có thể null nếu chưa xuất bản
     */
    @Column(name = "ngay_xuat_ban")
    private LocalDate ngayXuatBan; // Có thể null nếu chưa xuất bản
    
    /**
     * Thời điểm cập nhật thông tin tác phẩm gần nhất
     */
    @Column(name = "ngay_cap_nhat")
    private LocalDateTime ngayCapNhat; // Thời điểm cập nhật gần nhất
    
    /**
     * Trạng thái xuất bản của tác phẩm
     * Mặc định là false (chưa xuất bản)
     * Không được phép null
     */
    @Column(name = "da_xuat_ban", nullable = false)
    private Boolean daXuatBan = false; // Trạng thái xuất bản, nên kiểm tra logic khi cập nhật
    
    /**
     * Số lượng tồn kho của tác phẩm
     */
    @Column(name = "so_luong_ton")
    private Integer soLuongTon; // Số lượng tồn kho, nên kiểm tra không âm
    
    /**
     * Giá nhập của tác phẩm
     * Định dạng: 10 chữ số, 2 số thập phân
     */
    @Column(name = "gia_nhap", precision = 10, scale = 2)
    private BigDecimal giaNhap; // Giá nhập, nên kiểm tra không âm
    
    /**
     * Giá bán của tác phẩm
     * Định dạng: 10 chữ số, 2 số thập phân
     */
    @Column(name = "gia_ban", precision = 10, scale = 2)
    private BigDecimal giaBan; // Giá bán, nên kiểm tra không âm
    
    /**
     * Phần trăm giảm giá áp dụng cho tác phẩm
     * Định dạng: 5 chữ số, 2 số thập phân
     */
    @Column(name = "phan_tram_giam_gia", precision = 5, scale = 2)
    private BigDecimal phanTramGiamGia; // Phần trăm giảm giá, nên kiểm tra từ 0 đến 100
    
    /**
     * Đường dẫn đến ảnh bìa của tác phẩm
     * Không được phép null
     */
    @Column(name = "anh_bia", nullable = false)
    private String anhBia; // Đường dẫn ảnh bìa, nên kiểm tra hợp lệ
    
    /**
     * Số lượng đánh giá của tác phẩm
     * Mặc định là 0
     */
    @Column(name = "review_count")
    private Integer reviewCount = 0; // Số lượng đánh giá, mặc định 0
    
    /**
     * Điểm đánh giá trung bình của tác phẩm
     * Định dạng: 3 chữ số, 2 số thập phân
     */
    @Column(name = "average_rating", precision = 3, scale = 2)
    private BigDecimal averageRating; // Điểm đánh giá trung bình, nên kiểm tra từ 0 đến 5
    
    /**
     * Chiều dài của tác phẩm (cm)
     * Định dạng: 5 chữ số, 2 số thập phân
     */
    @Column(name = "length_cm", precision = 5, scale = 2)
    private BigDecimal lengthCm; // Chiều dài (cm), nên kiểm tra không âm
    
    /**
     * Chiều rộng của tác phẩm (cm)
     * Định dạng: 5 chữ số, 2 số thập phân
     */
    @Column(name = "width_cm", precision = 5, scale = 2)
    private BigDecimal widthCm; // Chiều rộng (cm), nên kiểm tra không âm
    
    /**
     * Chiều cao của tác phẩm (cm)
     * Định dạng: 5 chữ số, 2 số thập phân
     */
    @Column(name = "height_cm", precision = 5, scale = 2)
    private BigDecimal heightCm; // Chiều cao (cm), nên kiểm tra không âm
    
    /**
     * Cân nặng của tác phẩm (kg)
     * Định dạng: 5 chữ số, 2 số thập phân
     */
    @Column(name = "weight_kg", precision = 5, scale = 2)
    private BigDecimal weightKg; // Cân nặng (kg), nên kiểm tra không âm
} 