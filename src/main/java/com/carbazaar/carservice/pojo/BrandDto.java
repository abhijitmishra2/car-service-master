package com.carbazaar.carservice.pojo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BrandDto {
    private Long id;
    private String name;
    private String logoUrl;
}
