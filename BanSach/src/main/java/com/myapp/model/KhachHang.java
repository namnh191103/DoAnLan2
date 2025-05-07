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

    @Column(nullable = false, length = 64)
    private String matKhau;

    @Column(nullable = false, length = 45)
    private String hoTen;

    @Column(nullable = false, length = 15)
    private String soDienThoai;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ngayTao;

    @Column(nullable = false)
    private Boolean daKichHoat;

    @Column(nullable = false, length = 20)
    private String loaiXacThuc;

    @Column(length = 64)
    private String maXacThuc;
} 