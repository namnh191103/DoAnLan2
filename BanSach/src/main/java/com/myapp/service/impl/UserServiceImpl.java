package com.myapp.service.impl;

import com.myapp.dto.UserDTO;
import com.myapp.model.User;
import com.myapp.model.VaiTro;
import com.myapp.repository.UserRepository;
import com.myapp.repository.VaiTroRepository;
import com.myapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import java.util.HashSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.myapp.service.MailService;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VaiTroRepository vaiTroRepository;
    private final MailService mailService;
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public UserDTO register(UserDTO userDTO) {
        String email = userDTO.getEmail().trim().toLowerCase();
        log.info("[REGISTER] Check existsByEmail: '{}', raw='{}'", email, userDTO.getEmail());
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }
        
        User user = new User();
        user.setEmail(email);
        user.setMatKhau(passwordEncoder.encode(userDTO.getMatKhau()));
        user.setHoTen(userDTO.getHoTen());
        user.setSoDienThoai(userDTO.getSoDienThoai());
        user.setHinhAnh(userDTO.getHinhAnh());
        user.setDaKichHoat(true);
        user.setLoaiXacThuc("EMAIL");
        user.setNgayTao(java.time.LocalDateTime.now());
        
        VaiTro roleUser = vaiTroRepository.findByTenVaiTro("ROLE_USER");
        HashSet<VaiTro> roles = new HashSet<>();
        roles.add(roleUser);
        user.setVaiTros(roles);
        
        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    @Override
    public UserDTO login(String email, String matKhau) {
        String normalizedEmail = email.trim().toLowerCase();
        log.info("[LOGIN] Try login with email: '{}', raw='{}'", normalizedEmail, email);
        User user = userRepository.findByEmail(normalizedEmail)
            .orElseThrow(() -> new RuntimeException("User not found"));
        log.info("[LOGIN] Found user: {}", user.getEmail());
        boolean passwordMatch = passwordEncoder.matches(matKhau, user.getMatKhau());
        log.info("[LOGIN] Password match: {}", passwordMatch);
        if (!passwordMatch) {
            throw new RuntimeException("Invalid password");
        }
        return convertToDTO(user);
    }

    @Override
    public UserDTO findById(Integer id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return convertToDTO(user);
    }

    @Override
    public UserDTO findByEmail(String email) {
        String normalizedEmail = email.trim().toLowerCase();
        User user = userRepository.findByEmail(normalizedEmail)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return convertToDTO(user);
    }

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    @Override
    public Page<UserDTO> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(this::convertToDTO);
    }

    @Override
    public UserDTO update(Integer id, UserDTO userDTO) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
            
        user.setHoTen(userDTO.getHoTen());
        user.setSoDienThoai(userDTO.getSoDienThoai());
        user.setHinhAnh(userDTO.getHinhAnh());
        
        if (userDTO.getMatKhau() != null && !userDTO.getMatKhau().isEmpty()) {
            user.setMatKhau(passwordEncoder.encode(userDTO.getMatKhau()));
        }
        
        User updatedUser = userRepository.save(user);
        return convertToDTO(updatedUser);
    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email.trim().toLowerCase());
    }

    @Override
    public User findEntityByEmail(String email) {
        String normalizedEmail = email.trim().toLowerCase();
        return userRepository.findByEmail(normalizedEmail)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public void changePassword(String email, String oldPassword, String newPassword) {
        User user = userRepository.findByEmail(email.trim().toLowerCase())
            .orElseThrow(() -> new RuntimeException("User not found"));
        if (!passwordEncoder.matches(oldPassword, user.getMatKhau())) {
            throw new RuntimeException("Mật khẩu cũ không đúng!");
        }
        user.setMatKhau(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public void addRoleAdmin(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        VaiTro roleAdmin = vaiTroRepository.findByTenVaiTro("ROLE_ADMIN");
        if (roleAdmin != null && (user.getVaiTros() == null || !user.getVaiTros().contains(roleAdmin))) {
            if (user.getVaiTros() == null) user.setVaiTros(new java.util.HashSet<>());
            user.getVaiTros().add(roleAdmin);
            userRepository.save(user);
        }
    }

    @Override
    public void removeRoleAdmin(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        VaiTro roleAdmin = vaiTroRepository.findByTenVaiTro("ROLE_ADMIN");
        if (roleAdmin != null && user.getVaiTros() != null && user.getVaiTros().contains(roleAdmin)) {
            user.getVaiTros().remove(roleAdmin);
            userRepository.save(user);
        }
    }

    @Override
    public void lockUser(Integer id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        user.setDaKhoa(true);
        userRepository.save(user);
        log.info("User with id {} locked.", id);
    }

    @Override
    public void unlockUser(Integer id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        user.setDaKhoa(false);
        userRepository.save(user);
        log.info("User with id {} unlocked.", id);
    }

    @Override
    public void sendPasswordResetOtp(String email) {
        String normalizedEmail = email.trim().toLowerCase();
        User user = userRepository.findByEmail(normalizedEmail)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng với email này!"));
        // Sinh OTP 6 số
        String otp = String.format("%06d", new Random().nextInt(1000000));
        user.setMaXacThuc(otp);
        user.setOtpExpiredAt(LocalDateTime.now().plusMinutes(10)); // OTP có hiệu lực 10 phút
        userRepository.save(user);
        // Gửi mail
        String subject = "Mã OTP đặt lại mật khẩu";
        String text = "Mã OTP của bạn là: " + otp + "\nMã có hiệu lực trong 10 phút.";
        mailService.sendSimpleEmail(user.getEmail(), subject, text);
    }

    @Override
    public void resetPasswordWithOtp(String email, String otp, String newPassword) {
        String normalizedEmail = email.trim().toLowerCase();
        User user = userRepository.findByEmail(normalizedEmail)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng với email này!"));
        if (user.getMaXacThuc() == null || user.getOtpExpiredAt() == null) {
            throw new RuntimeException("Bạn chưa yêu cầu OTP hoặc OTP đã hết hạn!");
        }
        if (!user.getMaXacThuc().equals(otp)) {
            throw new RuntimeException("Mã OTP không đúng!");
        }
        if (user.getOtpExpiredAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Mã OTP đã hết hạn!");
        }
        // Đổi mật khẩu
        user.setMatKhau(passwordEncoder.encode(newPassword));
        // Xóa OTP sau khi dùng
        user.setMaXacThuc(null);
        user.setOtpExpiredAt(null);
        userRepository.save(user);
    }

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setHoTen(user.getHoTen());
        dto.setSoDienThoai(user.getSoDienThoai());
        dto.setHinhAnh(user.getHinhAnh());
        dto.setNgayTao(user.getNgayTao());
        dto.setDaKichHoat(user.getDaKichHoat());
        dto.setLoaiXacThuc(user.getLoaiXacThuc());
        dto.setMaXacThuc(user.getMaXacThuc());
        dto.setDaKhoa(user.isDaKhoa());
        if (user.getVaiTros() != null) {
            dto.setVaiTros(user.getVaiTros().stream().map(vt -> vt.getTenVaiTro()).collect(java.util.stream.Collectors.toList()));
        }
        return dto;
    }
} 