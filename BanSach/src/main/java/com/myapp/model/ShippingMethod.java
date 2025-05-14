package com.myapp.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "shipping_method")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShippingMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(length = 255)
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal fee;

    @Column(nullable = false)
    private boolean active;
} 