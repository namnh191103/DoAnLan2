package com.myapp.controller.user;

import com.myapp.dto.ProductDTO;
import com.myapp.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller xử lý các request liên quan đến sản phẩm cho người dùng
 * 
 * Sử dụng annotation @Controller để đánh dấu đây là một Spring MVC Controller
 * @RequiredArgsConstructor: tự động tạo constructor với các tham số bắt buộc
 * @RequestMapping("/user/products"): Tất cả các request trong controller này đều bắt đầu bằng "/user/products"
 */
@Controller
@RequestMapping("/user/products")
@RequiredArgsConstructor
public class UserProductController {
    /**
     * Service xử lý logic liên quan đến sản phẩm
     * Được inject thông qua constructor
     */
    private final ProductService productService;

    /**
     * Xử lý request GET để hiển thị danh sách sản phẩm
     * 
     * @param page Số trang hiện tại (mặc định: 0)
     * @param size Số sản phẩm trên mỗi trang (mặc định: 12)
     * @param sortBy Trường để sắp xếp (mặc định: "ngayCapNhat")
     * @param sortDir Hướng sắp xếp (mặc định: "desc")
     * @param model Model để truyền dữ liệu đến view
     * @return Tên view template "user/products/list"
     * 
     * URL mapping: "/user/products"
     * Chức năng:
     * - Lấy danh sách sản phẩm theo phân trang
     * - Sắp xếp sản phẩm theo trường và hướng chỉ định
     * - Thêm thông tin phân trang và sắp xếp vào model
     */
    @GetMapping
    public String listProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "ngayCapNhat") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            Model model) {
        
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<ProductDTO> products = productService.findAll(pageable);
        
        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("totalItems", products.getTotalElements());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);
        
        return "user/products/list";
    }

    /**
     * Xử lý request GET để tìm kiếm sản phẩm
     * 
     * @param keyword Từ khóa tìm kiếm
     * @param page Số trang hiện tại (mặc định: 0)
     * @param size Số sản phẩm trên mỗi trang (mặc định: 12)
     * @param model Model để truyền dữ liệu đến view
     * @return Tên view template "user/products/search"
     * 
     * URL mapping: "/user/products/search"
     * Chức năng:
     * - Tìm kiếm sản phẩm theo từ khóa
     * - Phân trang kết quả tìm kiếm
     * - Thêm thông tin tìm kiếm và phân trang vào model
     */
    @GetMapping("/search")
    public String searchProducts(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDTO> products = productService.search(keyword, pageable);
        model.addAttribute("products", products);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("totalItems", products.getTotalElements());
        // Trả về view danh sách sản phẩm chuẩn
        return "user/products/list";
    }

    /**
     * Xử lý request GET để hiển thị chi tiết sản phẩm
     * 
     * @param duongDan Đường dẫn thân thiện của sản phẩm
     * @param model Model để truyền dữ liệu đến view
     * @return 
     * - Nếu tìm thấy sản phẩm: Tên view template "user/product-detail"
     * - Nếu không tìm thấy hoặc sản phẩm chưa xuất bản: Tên view template "user/products/not-found"
     * 
     * URL mapping: "/user/products/{duongDan}"
     * Chức năng:
     * - Tìm sản phẩm theo đường dẫn thân thiện
     * - Kiểm tra trạng thái xuất bản của sản phẩm
     * - Thêm thông tin sản phẩm hoặc thông báo lỗi vào model
     */
    @GetMapping("/{duongDan}")
    public String getProductBySlug(@PathVariable String duongDan, Model model) {
        ProductDTO product = null;
        try {
            product = productService.findByDuongDan(duongDan);
        } catch (Exception e) {
            // Không tìm thấy sản phẩm
        }
        if (product == null || Boolean.FALSE.equals(product.getDaXuatBan())) {
            model.addAttribute("message", "Sản phẩm không còn tồn tại hoặc đã bị gỡ.");
            return "user/products/not-found";
        }
        model.addAttribute("product", product);
        return "user/product-detail";
    }
} 