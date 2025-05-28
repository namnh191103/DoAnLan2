package com.myapp.repository;

import com.myapp.model.Cart;
import com.myapp.model.User;
import com.myapp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Đoạn này nói rằng file này dùng để lấy dữ liệu về giỏ hàng từ nơi lưu trữ
 * "Annotation" @Repository giống như dán nhãn để chương trình biết đây là nơi quản lý dữ liệu
 * Kế thừa JpaRepository giúp file này có sẵn các chức năng như: thêm, xóa, sửa, tìm kiếm dữ liệu
 */
@Repository // Đánh dấu đây là một repository (nơi quản lý dữ liệu)
public interface CartRepository extends JpaRepository<Cart, Integer> { // Kế thừa JpaRepository để có sẵn các hàm CRUD
    /**
     * Tìm danh sách sản phẩm trong giỏ hàng theo email của người dùng
     * 
     * @param email Email của người dùng
     * @return Danh sách sản phẩm trong giỏ hàng
     */
    List<Cart> findByUserEmail(String email); // Tìm danh sách sản phẩm trong giỏ hàng theo email người dùng

    List<Cart> findByUser(User user); // Tìm tất cả sản phẩm trong giỏ hàng của một người
    Cart findByUserAndProduct(User user, Product product); // Tìm một sản phẩm cụ thể trong giỏ hàng của một người
} 