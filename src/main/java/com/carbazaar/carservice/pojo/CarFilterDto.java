package com.carbazaar.carservice.pojo;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarFilterDto {
    private boolean usedCar;
    private boolean newCar;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Long brandId;
    private Long carId;
}
