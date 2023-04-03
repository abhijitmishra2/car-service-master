package com.carbazaar.carservice.repository;

import com.carbazaar.carservice.entity.Car;
import com.carbazaar.carservice.entity.Variant;
import com.carbazaar.carservice.pojo.RecommendationQueryObj;

import java.util.List;

public interface RecommendationRepository {
    List<Variant> getRecommendedCars(RecommendationQueryObj recommendationQueryObj);
}
