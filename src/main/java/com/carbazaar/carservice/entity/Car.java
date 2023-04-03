package com.carbazaar.carservice.entity;

import com.carbazaar.carservice.entity.helper.AbstractJpaEntity;
import com.carbazaar.carservice.entity.helper.StringListConverter;
import com.carbazaar.carservice.enums.BodyType;
import com.carbazaar.carservice.enums.FuelType;
import com.carbazaar.carservice.enums.TransmissionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@Table(name = "car")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Car extends AbstractJpaEntity {

    @Column(name = "name", nullable = false)
    private String name;
    @OneToOne
    private Brand brand;
    @Convert(converter = StringListConverter.class)
    @Column(columnDefinition = "TEXT")
    private List<String> imageUrlList;
    private Integer manufacturingYear;
    private Integer seatCapacity;

    @OneToMany(mappedBy = "car",fetch = FetchType.LAZY)
    private List<Variant> variants;
}
