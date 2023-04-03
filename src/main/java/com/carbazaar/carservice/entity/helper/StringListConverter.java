package com.carbazaar.carservice.entity.helper;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {
    private static final String SPLIT_CHAR = "~";

    @Override
    public String convertToDatabaseColumn(List<String> stringList) {
        return CollectionUtils.isEmpty(stringList) ?
                null : String.join(SPLIT_CHAR, stringList);
    }

    @Override
    public List<String> convertToEntityAttribute(String s) {
        return StringUtils.isBlank(s) ? Collections.emptyList() : Arrays.asList(s.split(SPLIT_CHAR));
    }
}
