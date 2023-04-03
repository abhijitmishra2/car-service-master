package com.carbazaar.carservice.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter

public enum TransmissionType {
    AUTOMATIC("Automatic"), MANUAL("Manual") , BOTH("Both");

    private String transmissionType;
}
