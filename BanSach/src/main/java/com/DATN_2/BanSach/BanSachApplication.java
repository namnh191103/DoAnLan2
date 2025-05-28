// Khai báo package cho lớp ứng dụng chính
package com.DATN_2.BanSach; // Đây là nơi tổ chức các file Java theo nhóm, giúp quản lý dễ dàng hơn

// Import các thư viện cần thiết từ Spring Boot và JPA
import org.springframework.boot.SpringApplication; // Thư viện để khởi động ứng dụng Spring Boot
import org.springframework.boot.autoconfigure.SpringBootApplication; // Annotation để đánh dấu đây là ứng dụng Spring Boot
import org.springframework.data.jpa.repository.config.EnableJpaRepositories; // Annotation để kích hoạt các repository JPA
import org.springframework.boot.autoconfigure.domain.EntityScan; // Annotation để cấu hình quét các entity JPA

/**
 * Lớp ứng dụng chính cho hệ thống BanSach (Cửa hàng sách)
 * Lớp này đóng vai trò là điểm khởi đầu cho ứng dụng Spring Boot
 * 
 * @SpringBootApplication - Đánh dấu lớp này là một ứng dụng Spring Boot
 * scanBasePackages - Chỉ định các package cơ sở để quét tìm các component (controller, service, ...)
 * 
 * @EnableJpaRepositories - Kích hoạt các repository JPA (làm việc với database)
 * basePackages - Chỉ định nơi tìm các interface repository
 * 
 * @EntityScan - Cấu hình các package cơ sở để quét tìm các entity JPA (các lớp ánh xạ với bảng dữ liệu)
 * basePackages - Chỉ định nơi tìm các lớp entity
 */
@SpringBootApplication(scanBasePackages = {"com.DATN_2.BanSach", "com.myapp"}) // Đánh dấu đây là ứng dụng Spring Boot và chỉ định nơi quét các thành phần
@EnableJpaRepositories(basePackages = "com.myapp.repository") // Kích hoạt JPA repository, chỉ định nơi chứa các interface repository
@EntityScan(basePackages = "com.myapp.model") // Chỉ định nơi chứa các entity (lớp ánh xạ với bảng dữ liệu)
public class BanSachApplication { // Định nghĩa lớp chính của ứng dụng

	/**
	 * Phương thức main đóng vai trò là điểm khởi đầu cho ứng dụng
	 * Phương thức này khởi tạo ứng dụng Spring Boot
	 * 
	 * @param args Các tham số dòng lệnh được truyền vào ứng dụng
	 */
	public static void main(String[] args) { // Hàm main, là điểm bắt đầu khi chạy chương trình Java
		SpringApplication.run(BanSachApplication.class, args); // Lệnh này sẽ khởi động toàn bộ ứng dụng Spring Boot
	}

}
