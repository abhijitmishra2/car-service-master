package com.carbazaar.carservice.repository;

import com.carbazaar.carservice.entity.Car;
import com.carbazaar.carservice.entity.Variant;
import com.carbazaar.carservice.pojo.SearchDto;

import java.util.List;

public interface CarSearchRepository {
    List<Variant> findVariants(SearchDto searchDto);
    Variant findVariant(String carName, String variantName);
    List<Car> findAllCarsInCity(List<String> brandNames);
    List<Car> findAllLatestCarsInCity(List<String> brandNames);
    List<Variant> findAllVariantsByCarId(long carId);
}
