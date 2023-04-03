package com.carbazaar.carservice.entity;

import com.carbazaar.carservice.entity.helper.AbstractJpaEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "brand")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Brand extends AbstractJpaEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "logo_url")
    private String logoUrl;

}
