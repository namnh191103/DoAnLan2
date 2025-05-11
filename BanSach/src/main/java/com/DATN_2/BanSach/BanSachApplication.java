// Khai báo package cho lớp ứng dụng chính
package com.DATN_2.BanSach;

// Import các thư viện cần thiết từ Spring Boot và JPA
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * Lớp ứng dụng chính cho hệ thống BanSach (Cửa hàng sách)
 * Lớp này đóng vai trò là điểm khởi đầu cho ứng dụng Spring Boot
 * 
 * @SpringBootApplication - Đánh dấu lớp này là một ứng dụng Spring Boot
 * scanBasePackages - Chỉ định các package cơ sở để quét tìm các component
 * 
 * @EnableJpaRepositories - Kích hoạt các repository JPA
 * basePackages - Chỉ định nơi tìm các interface repository
 * 
 * @EntityScan - Cấu hình các package cơ sở để quét tìm các entity JPA
 * basePackages - Chỉ định nơi tìm các lớp entity
 */
@SpringBootApplication(scanBasePackages = {"com.DATN_2.BanSach", "com.myapp"})
@EnableJpaRepositories(basePackages = "com.myapp.repository")
@EntityScan(basePackages = "com.myapp.model")
public class BanSachApplication {

	/**
	 * Phương thức main đóng vai trò là điểm khởi đầu cho ứng dụng
	 * Phương thức này khởi tạo ứng dụng Spring Boot
	 * 
	 * @param args Các tham số dòng lệnh được truyền vào ứng dụng
	 */
	public static void main(String[] args) {
		SpringApplication.run(BanSachApplication.class, args);
	}

}
