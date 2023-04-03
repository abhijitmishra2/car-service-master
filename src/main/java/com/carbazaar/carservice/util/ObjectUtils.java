package com.carbazaar.carservice.util;

import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanUtils;

import java.util.Objects;

@UtilityClass
public class ObjectUtils {

    public <T> T createObjectByCopying(Object source, T destination) {
        if(Objects.isNull(source)) {
            return null;
        }
        BeanUtils.copyProperties(source, destination);
        return destination;
    }

}
