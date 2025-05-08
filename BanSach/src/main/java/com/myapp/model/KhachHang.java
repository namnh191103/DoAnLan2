package com.myapp.model;

import lombok.*;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "khach_hang")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KhachHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false, length = 128)
    private String email;

    @Column(name = "mat_khau", nullable = false, length = 64)
    private String matKhau;

    @Column(name = "ho_ten", nullable = false, length = 45)
    private String hoTen;

    @Column(name = "so_dien_thoai", nullable = false, length = 15)
    private String soDienThoai;

    @Column(name = "ngay_tao", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ngayTao;

    @Column(name = "da_kich_hoat", nullable = false)
    private Boolean daKichHoat;

    @Column(name = "loai_xac_thuc", nullable = false, length = 20)
    private String loaiXacThuc;

    @Column(name = "ma_xac_thuc", length = 64)
    private String maXacThuc;
} 