package com.myapp.service;

import com.myapp.dto.UserDTO;
import com.myapp.model.DonHang;
import com.myapp.model.ChiTietDonHang;
import com.myapp.model.Cart;
import com.myapp.model.User;
import com.myapp.repository.DonHangRepository;
import com.myapp.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.myapp.model.ShippingMethod;
import com.myapp.model.PaymentMethod;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.myapp.model.TrangThaiDonHang;

/**
 * Service xử lý logic liên quan đến đơn hàng
 * 
 * Sử dụng annotation @Service để đánh dấu đây là một Spring Service
 * @RequiredArgsConstructor: tự động tạo constructor với các tham số bắt buộc
 */
@Service
@RequiredArgsConstructor
public class DonHangUserService {
    /**
     * Repository xử lý truy vấn database liên quan đến đơn hàng
     * Được inject thông qua constructor
     */
    private final DonHangRepository donHangRepository;

    /**
     * Repository xử lý truy vấn database liên quan đến giỏ hàng
     * Được inject thông qua constructor
     */
    private final CartRepository cartRepository;

    /**
     * Service xử lý logic liên quan đến người dùng
     * Được inject thông qua constructor
     */
    private final UserService userService;

    /**
     * Lấy danh sách đơn hàng của một người dùng
     * 
     * @param userDTO Thông tin người dùng
     * @return Danh sách đơn hàng của người dùng
     */
    public List<DonHang> getOrdersByUser(UserDTO userDTO) {
        User user = userService.findByEmail(userDTO.getEmail()).getUser();
        return donHangRepository.findByUser(user);
    }

    /**
     * Lấy thông tin đơn hàng theo ID
     * 
     * @param id ID của đơn hàng
     * @return Thông tin đơn hàng hoặc null nếu không tìm thấy
     */
    public DonHang getOrderById(Integer id) {
        return donHangRepository.findById(id).orElse(null);
    }

    /**
     * Hủy đơn hàng
     * 
     * @param orderId ID của đơn hàng cần hủy
     * @param userDTO Thông tin người dùng thực hiện hủy
     * @throws Exception Nếu không có quyền hủy hoặc đơn hàng không thể hủy
     */
    public void cancelOrder(Integer orderId, UserDTO userDTO) throws Exception {
        DonHang donHang = getOrderById(orderId);
        if (donHang == null) {
            throw new Exception("Không tìm thấy đơn hàng");
        }
        if (!donHang.getUser().getEmail().equals(userDTO.getEmail())) {
            throw new Exception("Bạn không có quyền hủy đơn hàng này");
        }
        if (!donHang.getTrangThai().equals(TrangThaiDonHang.CHO_XAC_NHAN)) {
            throw new Exception("Không thể hủy đơn hàng ở trạng thái này");
        }
        donHang.setTrangThai(TrangThaiDonHang.DA_HUY);
        donHangRepository.save(donHang);
    }

    /**
     * Tạo đơn hàng mới từ giỏ hàng của người dùng
     * 
     * @param userDTO Thông tin người dùng
     * @param shippingAddress Địa chỉ giao hàng
     * @param phoneNumber Số điện thoại người nhận
     * @param note Ghi chú đơn hàng
     * @param shippingMethod Phương thức giao hàng
     * @param paymentMethod Phương thức thanh toán
     * @return Đơn hàng mới được tạo
     * @throws Exception Nếu có lỗi trong quá trình tạo đơn hàng
     */
    @Transactional
    public DonHang createOrder(UserDTO userDTO, String shippingAddress, String phoneNumber, String note, ShippingMethod shippingMethod, PaymentMethod paymentMethod) throws Exception {
        User user = userService.findByEmail(userDTO.getEmail()).getUser();
        List<Cart> cartItems = cartRepository.findByUserEmail(userDTO.getEmail());
        if (cartItems.isEmpty()) {
            throw new Exception("Giỏ hàng trống");
        }
        DonHang donHang = new DonHang();
        donHang.setUser(user);
        donHang.setNgayDatHang(LocalDateTime.now());
        donHang.setTrangThai(TrangThaiDonHang.CHO_XAC_NHAN);
        donHang.setGhiChu(note);
        donHang.setHoTen(user.getHoTen());
        donHang.setSoDienThoai(phoneNumber);
        donHang.setDiaChi(shippingAddress);
        donHang.setTinhThanh(null);
        donHang.setQuanHuyen(null);
        donHang.setPhuongXa(null);
        donHang.setMaBuuDien(null);
        List<ChiTietDonHang> chiTietDonHangs = new ArrayList<>();
        BigDecimal tongTien = BigDecimal.ZERO;
        for (Cart cartItem : cartItems) {
            ChiTietDonHang chiTiet = new ChiTietDonHang();
            chiTiet.setDonHang(donHang);
            chiTiet.setProduct(cartItem.getProduct());
            chiTiet.setSoLuong(cartItem.getSoLuong());
            chiTiet.setDonGia(cartItem.getProduct().getGiaBan().doubleValue());
            tongTien = tongTien.add(cartItem.getProduct().getGiaBan().multiply(BigDecimal.valueOf(cartItem.getSoLuong())));
            chiTietDonHangs.add(chiTiet);
        }
        donHang.setChiTietDonHangs(chiTietDonHangs);
        donHang.setTongTien(tongTien);
        BigDecimal shippingFee = (shippingMethod != null && shippingMethod.getFee() != null) ? shippingMethod.getFee() : BigDecimal.ZERO;
        if (shippingFee.compareTo(BigDecimal.ZERO) < 0) shippingFee = BigDecimal.ZERO;
        donHang.setTongThanhToan(tongTien.add(shippingFee));
        DonHang saved = donHangRepository.save(donHang);
        cartRepository.deleteAll(cartItems);
        return saved;
    }

    public DonHang createOrderWithStatus(UserDTO userDTO, String shippingAddress, String phoneNumber, String note, ShippingMethod shippingMethod, PaymentMethod paymentMethod, TrangThaiDonHang trangThai) throws Exception {
        User user = userService.findByEmail(userDTO.getEmail()).getUser();
        List<Cart> cartItems = cartRepository.findByUserEmail(userDTO.getEmail());
        if (cartItems.isEmpty()) {
            throw new Exception("Giỏ hàng trống");
        }
        DonHang donHang = new DonHang();
        donHang.setUser(user);
        donHang.setNgayDatHang(LocalDateTime.now());
        donHang.setTrangThai(trangThai);
        donHang.setGhiChu(note);
        donHang.setHoTen(user.getHoTen());
        donHang.setSoDienThoai(phoneNumber);
        donHang.setDiaChi(shippingAddress);
        donHang.setTinhThanh(null);
        donHang.setQuanHuyen(null);
        donHang.setPhuongXa(null);
        donHang.setMaBuuDien(null);
        List<ChiTietDonHang> chiTietDonHangs = new ArrayList<>();
        BigDecimal tongTien = BigDecimal.ZERO;
        for (Cart cartItem : cartItems) {
            ChiTietDonHang chiTiet = new ChiTietDonHang();
            chiTiet.setDonHang(donHang);
            chiTiet.setProduct(cartItem.getProduct());
            chiTiet.setSoLuong(cartItem.getSoLuong());
            chiTiet.setDonGia(cartItem.getProduct().getGiaBan().doubleValue());
            tongTien = tongTien.add(cartItem.getProduct().getGiaBan().multiply(BigDecimal.valueOf(cartItem.getSoLuong())));
            chiTietDonHangs.add(chiTiet);
        }
        donHang.setChiTietDonHangs(chiTietDonHangs);
        donHang.setTongTien(tongTien);
        BigDecimal shippingFee2 = (shippingMethod != null && shippingMethod.getFee() != null) ? shippingMethod.getFee() : BigDecimal.ZERO;
        if (shippingFee2.compareTo(BigDecimal.ZERO) < 0) shippingFee2 = BigDecimal.ZERO;
        donHang.setTongThanhToan(tongTien.add(shippingFee2));
        DonHang saved = donHangRepository.save(donHang);
        return saved;
    }

    public void save(DonHang donHang) {
        donHangRepository.save(donHang);
    }
} 