package com.carbazaar.carservice.controller;

import com.carbazaar.carservice.entity.Car;
import com.carbazaar.carservice.entity.Variant;
import com.carbazaar.carservice.pojo.CarDto;
import com.carbazaar.carservice.pojo.CarListingDto;
import com.carbazaar.carservice.pojo.ResponseDto;
import com.carbazaar.carservice.pojo.SearchDto;
import com.carbazaar.carservice.pojo.SearchResultsDto;
import com.carbazaar.carservice.service.CarService;
import com.carbazaar.carservice.service.VariantService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@CrossOrigin
public class CarController {

    private final CarService carService;
    private final VariantService variantService;

    @GetMapping("/cars")
    @ApiOperation(value = "Fetch list of cars")
    public ResponseDto<List<CarDto>> getCars(@RequestParam(value = "brandId", required = false) Long brandId) {
        return ResponseDto.success("Cars fetched", carService.getCars(brandId));
    }

    @GetMapping("/cars/{id}")
    @ApiOperation(value = "Fetch car by ID")
    public ResponseDto<CarDto> getCarById(@PathVariable Long id,@RequestParam String city) {
        if(StringUtils.isEmpty(city))
        {
            city = "Bhubaneswar";
        }
        return ResponseDto.success("Successfully Fetched Cars", carService.getCarById(id,city));
    }

    @PostMapping("/cars")
    @ApiOperation(value = "Save car(s) details")
    public ResponseDto<List<CarDto>> saveCarDetails(@RequestBody List<CarDto> carDtoList) {
        return ResponseDto.success("Car data saved successfully", carService.saveCarDetails(carDtoList));
    }

    @GetMapping("/cars/compare")
    @ApiOperation(value = "Compare cars by IDs")
    public ResponseDto<List<CarDto>> getCarsByIds(@RequestParam List<Long> carIdList) {
        return ResponseDto.success("Fetched cars data", carService.getCarsByIds(carIdList));
    }

    @GetMapping("/cars/latest")
    @ApiOperation(value = "Get list of latest cars")
    public ResponseDto<List<CarListingDto>> getLatestCars() {
        return ResponseDto.success("Latest cars fetched", carService.fetchLatestCars());
    }

    //search cars
    @PostMapping("/cars/search")
    @ApiOperation(value = "Search car(s) using filters")
    public ResponseDto<List<SearchResultsDto>> searchCarDetails(@RequestBody SearchDto searchDto)
    {
        return ResponseDto.success("Car Search Results", carService.searchCarDetails(searchDto));
    }

    @PostMapping("/cars/variant/{id}")
    @ApiOperation(value = "Add car variants")
    public ResponseDto<List<Variant>> addCarVariants(@RequestBody List<Variant> variants,@PathVariable Long id)
    {
        return ResponseDto.success("Variants Added",variantService.saveVariants(variants,id));
    }

    @GetMapping("/cars/variant")
    @ApiOperation(value = "Get Variant details by car name and variant name")
    public ResponseDto<Variant> findCarByVariantAndModel(@RequestParam("carName") String carName, @RequestParam("variantName") String variantName)
    {
        return ResponseDto.success("Variant Details Fetched",carService.getVariantDetailsByCarNameAndVariantName(carName,variantName));
    }

    //find all cars in your city
    @PostMapping("/cars/new")
    @ApiOperation(value = "Find all new cars in your city")
    public ResponseDto<List<Car>> findAllCarsInCity(@RequestBody List<String> brandNames)
    {
        System.out.println(brandNames);
        return ResponseDto.success("New Car Details In City Fetched", carService.findAllNewCarsInCity(brandNames));
    }

    //find all latest cars available in your city
    @PostMapping("/cars/latest")
    @ApiOperation(value = "Find all latest cars in city")
    public ResponseDto<List<Car>> findAllLatestCarsInCity(@RequestBody List<String> brandNames)
    {
        return ResponseDto.success("Car comparison results fetched",carService.findAllLatestCars(brandNames));
    }

    //find all similar cars
    @GetMapping("/cars/similar")
    @ApiOperation(value = "Find Similar Cars")
    public ResponseDto<List<CarDto>> findAllSimilarCars(@RequestParam long variantId)
    {
        System.out.println("Similar car data...");
        return ResponseDto.success("Similar Car Response Fetched",carService.findAllSimilarCars(variantId));
    }


    @GetMapping("/car/images")
    @ApiOperation(value = "Find Car Images")
    public ResponseDto<List<String>> getCarImages(@RequestParam String carName)
    {
        return ResponseDto.success("Car Images Fetched",carService.getCarImages(carName));
    }

    @PutMapping("/cars/{id}/image")
    @ApiOperation("Update Car Images by Id")
    public ResponseDto<List<String>> putCarImages(@PathVariable Long id, @RequestBody List<String> imagePathList) {
        return ResponseDto.success("Updated Car Images", carService.updateCarImages(id, imagePathList));
    }
}
