package com.myapp.repository;

import com.myapp.model.Cart;
import com.myapp.model.User;
import com.myapp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository xử lý truy vấn database liên quan đến giỏ hàng
 * 
 * Sử dụng annotation @Repository để đánh dấu đây là một Spring Repository
 * Kế thừa JpaRepository để có sẵn các phương thức CRUD cơ bản
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    /**
     * Tìm danh sách sản phẩm trong giỏ hàng theo email của người dùng
     * 
     * @param email Email của người dùng
     * @return Danh sách sản phẩm trong giỏ hàng
     */
    List<Cart> findByUserEmail(String email);

    List<Cart> findByUser(User user);
    Cart findByUserAndProduct(User user, Product product);
} 