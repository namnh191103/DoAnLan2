package com.myapp.service;

import com.myapp.dto.UserDTO;
import com.myapp.model.Order;
import com.myapp.model.OrderDetail;
import com.myapp.model.Cart;
import com.myapp.model.User;
import com.myapp.repository.OrderRepository;
import com.myapp.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service xử lý logic liên quan đến đơn hàng
 * 
 * Sử dụng annotation @Service để đánh dấu đây là một Spring Service
 * @RequiredArgsConstructor: tự động tạo constructor với các tham số bắt buộc
 */
@Service
@RequiredArgsConstructor
public class OrderService {
    /**
     * Repository xử lý truy vấn database liên quan đến đơn hàng
     * Được inject thông qua constructor
     */
    private final OrderRepository orderRepository;

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
    public List<Order> getOrdersByUser(UserDTO userDTO) {
        return orderRepository.findByUserEmail(userDTO.getEmail());
    }

    /**
     * Lấy thông tin đơn hàng theo ID
     * 
     * @param id ID của đơn hàng
     * @return Thông tin đơn hàng hoặc null nếu không tìm thấy
     */
    public Order getOrderById(Integer id) {
        return orderRepository.findById(id).orElse(null);
    }

    /**
     * Hủy đơn hàng
     * 
     * @param orderId ID của đơn hàng cần hủy
     * @param userDTO Thông tin người dùng thực hiện hủy
     * @throws Exception Nếu không có quyền hủy hoặc đơn hàng không thể hủy
     */
    public void cancelOrder(Integer orderId, UserDTO userDTO) throws Exception {
        Order order = getOrderById(orderId);
        if (order == null) {
            throw new Exception("Không tìm thấy đơn hàng");
        }
        if (!order.getUser().getEmail().equals(userDTO.getEmail())) {
            throw new Exception("Bạn không có quyền hủy đơn hàng này");
        }
        if (!order.getStatus().equals("PENDING")) {
            throw new Exception("Không thể hủy đơn hàng ở trạng thái này");
        }
        order.setStatus("CANCELLED");
        orderRepository.save(order);
    }

    /**
     * Tạo đơn hàng mới từ giỏ hàng của người dùng
     * 
     * @param userDTO Thông tin người dùng
     * @param shippingAddress Địa chỉ giao hàng
     * @param phoneNumber Số điện thoại người nhận
     * @param note Ghi chú đơn hàng
     * @return Đơn hàng mới được tạo
     * @throws Exception Nếu có lỗi trong quá trình tạo đơn hàng
     */
    @Transactional
    public Order createOrder(UserDTO userDTO, String shippingAddress, String phoneNumber, String note) throws Exception {
        // Lấy thông tin User từ UserDTO
        User user = userService.findByEmail(userDTO.getEmail()).getUser();

        // Lấy danh sách sản phẩm trong giỏ hàng
        List<Cart> cartItems = cartRepository.findByUserEmail(userDTO.getEmail());
        if (cartItems.isEmpty()) {
            throw new Exception("Giỏ hàng trống");
        }

        // Tạo đơn hàng mới
        Order order = new Order();
        order.setUser(user);
        order.setShippingAddress(shippingAddress);
        order.setPhoneNumber(phoneNumber);
        order.setNote(note);
        order.setStatus("PENDING");

        // Tính tổng tiền và tạo chi tiết đơn hàng
        double totalAmount = 0;
        for (Cart cartItem : cartItems) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setProduct(cartItem.getProduct());
            orderDetail.setQuantity(cartItem.getSoLuong());
            orderDetail.setPrice(cartItem.getProduct().getGiaBan().doubleValue());
            orderDetail.setSubtotal(cartItem.getProduct().getGiaBan().doubleValue() * cartItem.getSoLuong());
            totalAmount += orderDetail.getSubtotal();
            order.getOrderDetails().add(orderDetail);
        }
        order.setTotalAmount(totalAmount);

        // Lưu đơn hàng và xóa giỏ hàng
        order = orderRepository.save(order);
        cartRepository.deleteAll(cartItems);

        return order;
    }
} 