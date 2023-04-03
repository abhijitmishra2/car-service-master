package com.carbazaar.carservice.adapter;

import com.carbazaar.carservice.entity.Brand;
import com.carbazaar.carservice.entity.Car;
import com.carbazaar.carservice.entity.RecommendationEntity;
import com.carbazaar.carservice.entity.Variant;
import com.carbazaar.carservice.pojo.*;
import com.carbazaar.carservice.util.ObjectUtils;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DtoAdapter {

    public CarDto convertCarEntityToDto(Car car) {
        CarDto carDto = ObjectUtils.createObjectByCopying(car, new CarDto());
        carDto.setBrand(ObjectUtils.createObjectByCopying(car.getBrand(), new BrandDto()));
        return carDto;
    }

    public CarListingDto convertCarEntityToListingDto(Car car) {
        CarListingDto carListingDto = ObjectUtils.createObjectByCopying(car, new CarListingDto());
        carListingDto.setBrandName(car.getBrand().getName());
        return carListingDto;
    }

    public Car convertCarDtoToEntity(CarDto carDto, Brand brand) {
        Car car = ObjectUtils.createObjectByCopying(carDto, new Car());
        car.setId(null); //Automatically create new id for a new record
        car.setBrand(brand);
        return car;
    }

    public RecommendationQueryDto convertToRecommendationQueryDto(RecommendationEntity entity)
    {
        RecommendationQueryDto recommendationQueryDto = ObjectUtils.createObjectByCopying(entity, new RecommendationQueryDto());
        return recommendationQueryDto;
    }

    public BrandDto convertBrandEntityToDto(Brand brand) {
        return ObjectUtils.createObjectByCopying(brand, new BrandDto());
    }

    public SearchResultsDto convertVariantEntityToDto(Variant variant){
        return ObjectUtils.createObjectByCopying(variant,new SearchResultsDto());
    }


}
