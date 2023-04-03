package com.carbazaar.carservice.controller;

import com.carbazaar.carservice.pojo.BrandDto;
import com.carbazaar.carservice.pojo.ResponseDto;
import com.carbazaar.carservice.service.BrandService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@CrossOrigin
public class BrandController {

    private final BrandService brandService;

    @GetMapping("/brands/list")
    @ApiOperation("Fetch list of all brands")
    public ResponseDto<List<BrandDto>> getBrandList() {
        return ResponseDto.success("Brand list fetched successfully", brandService.getAllBrandsList());
    }

    @PostMapping("/brands")
    @ApiOperation("Add brand(s) to database")
    public ResponseDto<List<BrandDto>> saveBrand(@RequestBody List<BrandDto> brandDtoList) {
        return ResponseDto.success("Brand Saved Successfully", brandService.saveBrand(brandDtoList));
    }

    @PutMapping("/brands/{id}/images")
    @ApiOperation("Update Brand Image")
    public ResponseDto<String> updateBrandImage(@PathVariable Long id,
                                                @RequestParam String filePath) {
        return ResponseDto.success("Brand Image updated successfully", brandService.updateBrandImage(id, filePath));
    }


}
