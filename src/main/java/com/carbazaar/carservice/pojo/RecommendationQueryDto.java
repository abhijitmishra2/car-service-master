package com.carbazaar.carservice.pojo;

import com.carbazaar.carservice.entity.helper.AbstractJpaEntity;
import com.carbazaar.carservice.enums.FuelType;
import com.carbazaar.carservice.enums.TransmissionType;

import lombok.*;

import javax.persistence.Entity;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RecommendationQueryDto{
    private String key;
    private int count;
}
