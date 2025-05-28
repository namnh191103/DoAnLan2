// Khai báo package cho repository quản lý vai trò người dùng
package com.myapp.repository;

import com.myapp.model.VaiTro; // Import entity VaiTro
import org.springframework.data.jpa.repository.JpaRepository; // Import JpaRepository để thao tác với database
import org.springframework.stereotype.Repository; // Đánh dấu đây là một repository
 
@Repository // Đánh dấu đây là một repository (nơi quản lý dữ liệu)
public interface VaiTroRepository extends JpaRepository<VaiTro, Integer> { // Kế thừa JpaRepository để có sẵn các hàm CRUD
    VaiTro findByTenVaiTro(String tenVaiTro); // Hàm tìm vai trò theo tên (ví dụ: ROLE_ADMIN, ROLE_USER)
} 