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
    void changePassword(String email, String oldPassword, String newPassword);
    void addRoleAdmin(Integer id);
    void removeRoleAdmin(Integer id);

    /**
     * Locks a user account.
     * @param id the ID of the user to lock
     */
    void lockUser(Integer id);

    /**
     * Unlocks a user account.
     * @param id the ID of the user to unlock
     */
    void unlockUser(Integer id);

    /**
     * Gửi OTP đặt lại mật khẩu về email người dùng
     * @param email email người dùng
     */
    void sendPasswordResetOtp(String email);

    /**
     * Đặt lại mật khẩu mới bằng OTP
     * @param email email người dùng
     * @param otp mã OTP
     * @param newPassword mật khẩu mới
     */
    void resetPasswordWithOtp(String email, String otp, String newPassword);
} 