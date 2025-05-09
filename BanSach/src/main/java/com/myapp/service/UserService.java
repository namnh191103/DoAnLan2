package com.myapp.service;

import com.myapp.dto.UserDTO;
import com.myapp.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserDTO register(UserDTO userDTO);
    UserDTO login(String email, String matKhau);
    UserDTO findById(Integer id);
    UserDTO findByEmail(String email);
    User findEntityByEmail(String email);
    List<UserDTO> findAll();
    UserDTO update(Integer id, UserDTO userDTO);
    void delete(Integer id);
    boolean existsByEmail(String email);
    Page<UserDTO> findAll(Pageable pageable);
} 