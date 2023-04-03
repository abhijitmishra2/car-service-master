package com.carbazaar.carservice.pojo;

import com.carbazaar.carservice.entity.Variant;
import com.carbazaar.carservice.enums.BodyType;
import com.carbazaar.carservice.enums.FuelType;
import com.carbazaar.carservice.enums.TransmissionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonPropertyOrder({"id","name","brand","imageUrlList","ManufacturingYear","seatCapacity","variants"})
public class CarDto {

    private Long id;
    private String name;
    private BrandDto brand;
    private List<String> imageUrlList;
    private Integer manufacturingYear;
    private List<Variant> variants;
    private Integer seatCapacity;
}
