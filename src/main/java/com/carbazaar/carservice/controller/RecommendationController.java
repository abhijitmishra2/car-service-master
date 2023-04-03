package com.carbazaar.carservice.controller;

import com.carbazaar.carservice.entity.Car;
import com.carbazaar.carservice.pojo.CarDto;
import com.carbazaar.carservice.pojo.RecommendationQueryDto;
import com.carbazaar.carservice.pojo.ResponseDto;
import com.carbazaar.carservice.service.RecommendationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class RecommendationController {

    @Autowired
    RecommendationService recommendationService;

    @PostMapping("/recommendation")
    @ApiOperation("Recommendation API")
    public ResponseDto<List<CarDto>> getRecommendedCars(@RequestBody List<RecommendationQueryDto> recommendationList,@RequestParam String city){
        System.out.println("Recommendation Query"+recommendationList);
       // recommendationService.getRecommendedCars(recommendationList);
        return ResponseDto.success("Recommendation Details Fetched",recommendationService.getRecommendedCars(recommendationList,city));
    }

}
