package com.carbazaar.carservice.pojo;

import com.carbazaar.carservice.entity.Brand;
import com.carbazaar.carservice.entity.Variant;
import com.carbazaar.carservice.enums.BodyType;
import com.carbazaar.carservice.enums.FuelType;
import com.carbazaar.carservice.enums.TransmissionType;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchResultsDto {

    private Long id;
    private String variantName;
    private BigDecimal exShowroomPrice;
    private FuelType fuelType;
    private BodyType bodyType;
    private Double safetyRating;
    private TransmissionType transmissionType;
    private Double mileage;
    private Integer airbags;
    private int engine;
    private Long carId;
    private String carName;
    private Brand carBrand;
    private List<String> imageUrlList;
    private Integer manufacturingYear;
    private Integer seatCapacity;
}
