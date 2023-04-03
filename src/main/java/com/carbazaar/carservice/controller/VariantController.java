package com.carbazaar.carservice.controller;

import com.carbazaar.carservice.entity.Variant;
import com.carbazaar.carservice.pojo.ResponseDto;
import com.carbazaar.carservice.service.VariantService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/variant")
@CrossOrigin
public class VariantController {

    @Autowired
    VariantService variantService;

    @GetMapping("/{id}")
    @ApiOperation(value = "Get Variant Details By Id")
    public ResponseDto<Variant> getVariantById(@PathVariable Long id,@RequestParam String cityName)
    {
        return ResponseDto.success("Variant Details Fetched Successfully",variantService.getVariant(id));
    }

    @GetMapping("/all")
    @ApiOperation(value = "Find All Variants")
    public ResponseDto<List<Variant>> findAllVariants()
    {
        return ResponseDto.success("All Variants Fetched",variantService.findAllVariants());
    }

    @PostMapping("/compare")
    @ApiOperation(value = "Compare Variants")
    public ResponseDto<List<Variant>> compareVariants(@RequestBody List<Long> variantIds)
    {
        return ResponseDto.success("Comparision Results Fetched Successfully",variantService.compareVariants(variantIds));
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "Delete Variant By ID")
    public ResponseDto<Variant> deleteVariant(@RequestParam long variantId)
    {
        return ResponseDto.success("Variant Deleted Successfully",variantService.deleteVariant(variantId));
    }

    @GetMapping("/all/carId/{carId}")
    @ApiOperation(value = "Find All Variants By CarId")
    public ResponseDto<List<Variant>> findAllVariantsByCarID(@PathVariable long carId)
    {
        return ResponseDto.success("Variants Data Fetched Successfully",variantService.findAllVariantsByCarId(carId));
    }

    @GetMapping("/car/id/")
    @ApiOperation(value = "Find VariantID By CarName & Variant name")
    public long getVariantID(@RequestParam("carName") String carName, @RequestParam("variantName") String variantName)
    {
        return variantService.findVariantID(carName,variantName);
    }
}
