package com.carbazaar.carservice.service;

import com.carbazaar.carservice.pojo.BrandDto;

import java.util.List;

public interface BrandService {
    List<BrandDto> getAllBrandsList();

    List<BrandDto> saveBrand(List<BrandDto> brandDtoList);

    String updateBrandImage(Long id, String filePath);
}
