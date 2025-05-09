package com.myapp.repository;

import com.myapp.model.Cart;
import com.myapp.model.User;
import com.myapp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findByUser(User user);
    Cart findByUserAndProduct(User user, Product product);
} 