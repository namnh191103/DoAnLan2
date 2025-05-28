// Khai báo package cho file cấu hình này
package com.myapp.config; // Giúp tổ chức các file Java theo nhóm, dễ quản lý

// Import các thư viện cần thiết
import org.springframework.context.annotation.Bean; // Annotation để đánh dấu một phương thức trả về một bean (thành phần) cho Spring quản lý
import org.springframework.context.annotation.Configuration; // Annotation để đánh dấu đây là một class cấu hình (configuration class)
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect; // Thư viện mở rộng cho Thymeleaf hỗ trợ Spring Security

// Đánh dấu đây là một class cấu hình cho Spring
@Configuration // Spring sẽ nhận biết đây là nơi cấu hình các bean
public class ThymeleafConfig { // Định nghĩa class cấu hình cho Thymeleaf
    
    // Định nghĩa một bean (thành phần) cho Spring quản lý
    @Bean // Spring sẽ tự động quản lý đối tượng trả về của hàm này
    public SpringSecurityDialect springSecurityDialect() { // Hàm này trả về một đối tượng SpringSecurityDialect
        return new SpringSecurityDialect(); // Đối tượng này giúp Thymeleaf sử dụng các tính năng bảo mật của Spring Security trong file HTML
    }
} 