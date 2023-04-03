package com.carbazaar.carservice.pojo;

import com.carbazaar.carservice.enums.FuelType;
import com.carbazaar.carservice.enums.TransmissionType;
import lombok.*;

@Builder
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecommendationResponseDto {
    private long carId;
    private String carName;
    private long brandId;
    private String brandName;
    private String exShowRoomPrice;
    private Integer engine;
    private FuelType fuelType;
    private TransmissionType transmissionType;
}
