package com.carbazaar.carservice.service;

import com.carbazaar.carservice.entity.Car;
import com.carbazaar.carservice.entity.Variant;
import com.carbazaar.carservice.pojo.CarDto;
import com.carbazaar.carservice.pojo.CarListingDto;
import com.carbazaar.carservice.pojo.SearchDto;
import com.carbazaar.carservice.pojo.SearchResultsDto;

import java.util.List;

public interface CarService {
    CarDto getCarById(Long id,String city);

    List<CarDto> saveCarDetails(List<CarDto> carDtoList);

    List<CarDto> getCars(Long brandId);

    List<CarDto> getCarsByIds(List<Long> carIdList);

    List<CarListingDto> fetchLatestCars();

    List<SearchResultsDto> searchCarDetails(SearchDto searchDto);

    Variant getVariantDetailsByCarNameAndVariantName(String carName, String variantName);

    List<Car> findAllNewCarsInCity(List<String> brandNames);

    List<Car> findAllLatestCars(List<String> brandNames);

    List<CarDto> findAllSimilarCars(long variantId);

    List<String> updateCarImages(Long id, List<String> filePath);
    List<String> getCarImages(String carName);
}
