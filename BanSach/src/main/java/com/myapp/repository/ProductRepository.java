// Khai báo package cho repository quản lý sản phẩm
package com.myapp.repository;

import com.myapp.model.Product; // Import entity Product
import org.springframework.data.domain.Page; // Đối tượng phân trang
import org.springframework.data.domain.Pageable; // Đối tượng tạo yêu cầu phân trang
import org.springframework.data.jpa.repository.JpaRepository; // Import JpaRepository để thao tác với database
import org.springframework.stereotype.Repository; // Đánh dấu đây là một repository
import java.util.Optional; // Đối tượng Optional để xử lý giá trị có thể null

@Repository // Đánh dấu đây là một repository (nơi quản lý dữ liệu)
public interface ProductRepository extends JpaRepository<Product, Integer> { // Kế thừa JpaRepository để có sẵn các hàm CRUD
    Optional<Product> findByDuongDan(String duongDan); // Tìm sản phẩm theo đường dẫn thân thiện (slug)
    Optional<Product> findByTuaDe(String tuaDe); // Tìm sản phẩm theo tiêu đề
    Page<Product> findByTuaDeContainingOrTomTatContaining(String tuaDe, String tomTat, Pageable pageable); // Tìm sản phẩm theo tiêu đề hoặc tóm tắt, có phân trang
} 