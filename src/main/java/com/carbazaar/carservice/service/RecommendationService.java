package com.carbazaar.carservice.service;

import com.carbazaar.carservice.pojo.CarDto;
import com.carbazaar.carservice.pojo.RecommendationQueryDto;

import java.util.List;

public interface RecommendationService {

    List<CarDto> getRecommendedCars(List<RecommendationQueryDto> recommendationList,String city);
}
