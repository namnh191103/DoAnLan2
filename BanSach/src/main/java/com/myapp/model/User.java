package com.myapp.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table(name = "khach_hang")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String matKhau;
    
    @Column(nullable = false)
    private String hoTen;
    
    @Column(nullable = false)
    private String soDienThoai;
    
    @Column
    private String hinhAnh;
    
    @Column(nullable = false)
    private LocalDateTime ngayTao;
    
    @Column(nullable = false)
    private Boolean daKichHoat;
    
    @Column(nullable = false)
    private String loaiXacThuc;
    
    @Column
    private String maXacThuc;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "khach_hang_vai_tro",
        joinColumns = @JoinColumn(name = "khach_hang_id"),
        inverseJoinColumns = @JoinColumn(name = "vai_tro_id")
    )
    private Set<VaiTro> vaiTros;
} 