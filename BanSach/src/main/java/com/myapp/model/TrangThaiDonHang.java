package com.myapp.model;

// Enum đại diện cho các trạng thái của đơn hàng trong hệ thống
// Lưu ý: Nếu thêm trạng thái mới, cần kiểm tra các nơi xử lý trạng thái đơn hàng để tránh bug logic
// Ví dụ: service, controller, UI hiển thị trạng thái
public enum TrangThaiDonHang {
    CHO_XAC_NHAN, // Đơn hàng vừa được tạo, chờ xác nhận từ hệ thống hoặc admin
    DANG_GIAO,    // Đơn hàng đang trong quá trình giao cho khách
    DA_GIAO,      // Đơn hàng đã giao thành công cho khách
    DA_HUY,       // Đơn hàng đã bị hủy bởi khách hoặc hệ thống
    CHO_THANH_TOAN, // Đơn hàng chờ khách thanh toán (nếu có hỗ trợ thanh toán sau)
    DA_THANH_TOAN,  // Đơn hàng đã được thanh toán thành công
    THANH_TOAN_THAT_BAI // Thanh toán thất bại, cần xử lý lại hoặc thông báo cho khách
    // Nếu thêm trạng thái mới, cần kiểm tra thêm
} 