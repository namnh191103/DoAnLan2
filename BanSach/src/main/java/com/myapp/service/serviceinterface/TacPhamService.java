package com.myapp.service.serviceinterface;

import com.myapp.dto.TacPhamDTO;
import java.util.List;

public interface TacPhamService {
    TacPhamDTO getTacPhamById(Integer id);
    List<TacPhamDTO> getAllTacPham();
    TacPhamDTO createTacPham(TacPhamDTO dto);
    TacPhamDTO updateTacPham(Integer id, TacPhamDTO dto);
    void deleteTacPham(Integer id);
} 