package com.carbazaar.carservice.pojo;

import com.carbazaar.carservice.entity.Brand;
import com.carbazaar.carservice.enums.BodyType;
import com.carbazaar.carservice.enums.FuelType;
import com.carbazaar.carservice.enums.TransmissionType;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchDto {
    private int minPrice=0;
    private int maxPrice=2000000;
    private List<Integer> seatCapacity;
    private List<TransmissionType> transmissionType;
    private double safetyRatings=0;
    private double mileage=0;
    //car model
    private String carName;
    //brand
    private String brandName;
    private List<FuelType> fuelType;
    private int airBags=0;
    private List<BodyType> bodyType;
    private List<String> brandNames;
}
