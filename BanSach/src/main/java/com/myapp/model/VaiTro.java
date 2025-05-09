package com.myapp.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "vai_tro")
public class VaiTro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ten_vai_tro", nullable = false, unique = true)
    private String tenVaiTro;

    @Column(name = "mo_ta")
    private String moTa;
} 