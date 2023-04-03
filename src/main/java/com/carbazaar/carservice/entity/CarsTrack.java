package com.carbazaar.carservice.entity;

import com.carbazaar.carservice.entity.helper.AbstractJpaEntity;
import com.carbazaar.carservice.pojo.RecommendationQueryDto;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CarsTrack extends AbstractJpaEntity {
    private String cityName;
    @OneToMany(cascade = CascadeType.ALL)
    private List<RecommendationEntity> cars;
}
