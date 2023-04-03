package com.carbazaar.carservice.pojo;

import com.carbazaar.carservice.enums.FuelType;
import com.carbazaar.carservice.enums.TransmissionType;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RecommendationQueryObj {

    private Set<String> brandNames;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Set<FuelType> fuelTypes;
    private Set<TransmissionType> transmissionTypes;
    private Set<Double> mileage;
}
