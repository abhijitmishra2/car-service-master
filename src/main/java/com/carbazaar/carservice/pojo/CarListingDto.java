package com.carbazaar.carservice.pojo;

import com.carbazaar.carservice.enums.TransmissionType;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarListingDto {
    private Long id;
    private String name;
    private List<String> imageUrlList;
    private TransmissionType transmissionType;
    private String mileage;
    private String brandName;
}
