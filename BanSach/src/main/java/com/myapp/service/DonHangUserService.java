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
 * Đoạn này nói rằng file này dùng để xử lý các chức năng liên quan đến đơn hàng của người dùng
 * @Service: Dán nhãn cho biết đây là một dịch vụ
 * @RequiredArgsConstructor: Tự động tạo hàm khởi tạo với các biến final
 */
@Service
@RequiredArgsConstructor
public class DonHangUserService {
    // Đây là nơi lấy dữ liệu đơn hàng từ nơi lưu trữ
    private final DonHangRepository donHangRepository;
    // Đây là nơi lấy dữ liệu giỏ hàng từ nơi lưu trữ
    private final CartRepository cartRepository;
    // Đây là nơi xử lý các chức năng liên quan đến người dùng
    private final UserService userService;

    /**
     * Lấy danh sách đơn hàng của một người dùng
     * 
     * @param userDTO Thông tin người dùng
     * @return Danh sách đơn hàng của người dùng
     */
    public List<DonHang> getOrdersByUser(UserDTO userDTO) {
        // Bước 1: Tìm người dùng thật dựa vào email
        User user = userService.findByEmail(userDTO.getEmail()).getUser();
        // Bước 2: Tìm tất cả đơn hàng của người dùng đó
        return donHangRepository.findByUser(user);
    }

    /**
     * Lấy thông tin đơn hàng theo ID
     * 
     * @param id ID của đơn hàng
     * @return Thông tin đơn hàng hoặc null nếu không tìm thấy
     */
    public DonHang getOrderById(Integer id) {
        // Nếu tìm thấy thì trả về đơn hàng, không thì trả về null
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
        // Bước 1: Tìm đơn hàng dựa vào mã số
        DonHang donHang = getOrderById(orderId);
        // Bước 2: Nếu không tìm thấy thì báo lỗi
        if (donHang == null) {
            throw new Exception("Không tìm thấy đơn hàng");
        }
        // Bước 3: Nếu người dùng không phải chủ đơn hàng thì báo lỗi
        if (!donHang.getUser().getEmail().equals(userDTO.getEmail())) {
            throw new Exception("Bạn không có quyền hủy đơn hàng này");
        }
        // Bước 4: Nếu đơn hàng không ở trạng thái chờ xác nhận thì không cho hủy
        if (!donHang.getTrangThai().equals(TrangThaiDonHang.CHO_XAC_NHAN)) {
            throw new Exception("Không thể hủy đơn hàng ở trạng thái này");
        }
        // Bước 5: Đổi trạng thái đơn hàng thành đã hủy
        donHang.setTrangThai(TrangThaiDonHang.DA_HUY);
        // Bước 6: Lưu lại đơn hàng đã thay đổi
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
    @Transactional // Nếu có lỗi trong quá trình tạo đơn hàng, mọi thay đổi sẽ bị hủy bỏ
    public DonHang createOrder(UserDTO userDTO, String shippingAddress, String phoneNumber, String note, ShippingMethod shippingMethod, PaymentMethod paymentMethod) throws Exception {
        // Bước 1: Tìm người dùng thật dựa vào email
        User user = userService.findByEmail(userDTO.getEmail()).getUser();
        // Bước 2: Lấy danh sách sản phẩm trong giỏ hàng của người dùng
        List<Cart> cartItems = cartRepository.findByUserEmail(userDTO.getEmail());
        // Bước 3: Nếu giỏ hàng trống thì báo lỗi
        if (cartItems.isEmpty()) {
            throw new Exception("Giỏ hàng trống");
        }
        // Bước 4: Tạo đối tượng đơn hàng mới
        DonHang donHang = new DonHang();
        donHang.setUser(user);
        donHang.setNgayDatHang(LocalDateTime.now()); // Lấy thời gian hiện tại
        donHang.setTrangThai(TrangThaiDonHang.CHO_XAC_NHAN); // Đơn hàng mới luôn ở trạng thái chờ xác nhận
        donHang.setGhiChu(note);
        donHang.setHoTen(user.getHoTen());
        donHang.setSoDienThoai(phoneNumber);
        donHang.setDiaChi(shippingAddress);
        donHang.setTinhThanh(null); // Chỗ này cần kiểm tra thêm, chưa rõ lắm
        donHang.setQuanHuyen(null); // Chỗ này cần kiểm tra thêm, chưa rõ lắm
        donHang.setPhuongXa(null); // Chỗ này cần kiểm tra thêm, chưa rõ lắm
        donHang.setMaBuuDien(null); // Chỗ này cần kiểm tra thêm, chưa rõ lắm
        // Bước 5: Tạo danh sách chi tiết đơn hàng
        List<ChiTietDonHang> chiTietDonHangs = new ArrayList<>();
        BigDecimal tongTien = BigDecimal.ZERO;
        // Bước 6: Với mỗi sản phẩm trong giỏ hàng, tạo một chi tiết đơn hàng
        for (Cart cartItem : cartItems) {
            ChiTietDonHang chiTiet = new ChiTietDonHang();
            chiTiet.setDonHang(donHang);
            chiTiet.setProduct(cartItem.getProduct());
            chiTiet.setSoLuong(cartItem.getSoLuong());
            chiTiet.setDonGia(cartItem.getProduct().getGiaBan().doubleValue());
            // Tính tổng tiền
            tongTien = tongTien.add(cartItem.getProduct().getGiaBan().multiply(BigDecimal.valueOf(cartItem.getSoLuong())));
            chiTietDonHangs.add(chiTiet);
        }
        // Bước 7: Gắn danh sách chi tiết vào đơn hàng
        donHang.setChiTietDonHangs(chiTietDonHangs);
        // Bước 8: Gắn tổng tiền vào đơn hàng
        donHang.setTongTien(tongTien);
        // Bước 9: Tính phí giao hàng (nếu có)
        BigDecimal shippingFee = (shippingMethod != null && shippingMethod.getFee() != null) ? shippingMethod.getFee() : BigDecimal.ZERO;
        if (shippingFee.compareTo(BigDecimal.ZERO) < 0) shippingFee = BigDecimal.ZERO;
        // Bước 10: Gắn tổng tiền phải thanh toán vào đơn hàng
        donHang.setTongThanhToan(tongTien.add(shippingFee));
        // Bước 11: Lưu đơn hàng vào nơi lưu trữ
        DonHang saved = donHangRepository.save(donHang);
        // Bước 12: Xóa hết sản phẩm trong giỏ hàng của người dùng
        cartRepository.deleteAll(cartItems);
        // Bước 13: Trả về đơn hàng vừa tạo
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