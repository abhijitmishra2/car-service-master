package com.carbazaar.carservice.entity;

import com.carbazaar.carservice.entity.helper.AbstractJpaEntity;
import lombok.*;

import javax.persistence.Entity;

@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RecommendationEntity extends AbstractJpaEntity {
    private String key;
    private int count;
}
