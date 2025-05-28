// Khai báo package cho repository quản lý phương thức thanh toán
package com.myapp.repository;

import com.myapp.model.PaymentMethod; // Import entity PaymentMethod
import org.springframework.data.jpa.repository.JpaRepository; // Import JpaRepository để thao tác với database
import org.springframework.stereotype.Repository; // Đánh dấu đây là một repository
import java.util.List; // Thư viện danh sách

@Repository // Đánh dấu đây là một repository (nơi quản lý dữ liệu)
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Integer> { // Kế thừa JpaRepository để có sẵn các hàm CRUD
    List<PaymentMethod> findByActiveTrue(); // Tìm danh sách phương thức thanh toán đang hoạt động
} 