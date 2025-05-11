package com.myapp.repository;

import com.myapp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository xử lý truy vấn database liên quan đến đơn hàng
 * 
 * Sử dụng annotation @Repository để đánh dấu đây là một Spring Repository
 * Kế thừa JpaRepository để có sẵn các phương thức CRUD cơ bản
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    /**
     * Tìm danh sách đơn hàng theo email của người dùng
     * 
     * @param email Email của người dùng
     * @return Danh sách đơn hàng của người dùng
     */
    List<Order> findByUserEmail(String email);
} 