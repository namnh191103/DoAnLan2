package com.myapp.controller.admin;

import com.myapp.dto.KhachHangDTO;
import com.myapp.service.serviceinterface.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin/khach-hang")
public class KhachHangAdminController {
    @Autowired
    private KhachHangService khachHangService;

    @GetMapping
    public ResponseEntity<List<KhachHangDTO>> getAll() {
        return ResponseEntity.ok(khachHangService.getAllKhachHang());
    }

    @GetMapping("/{id}")
    public ResponseEntity<KhachHangDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(khachHangService.getKhachHangById(id));
    }

    @PostMapping
    public ResponseEntity<KhachHangDTO> create(@RequestBody KhachHangDTO dto) {
        return ResponseEntity.ok(khachHangService.createKhachHang(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<KhachHangDTO> update(@PathVariable Integer id, @RequestBody KhachHangDTO dto) {
        return ResponseEntity.ok(khachHangService.updateKhachHang(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        khachHangService.deleteKhachHang(id);
        return ResponseEntity.noContent().build();
    }
} 