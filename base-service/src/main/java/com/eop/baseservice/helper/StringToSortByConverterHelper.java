package com.eop.baseservice.helper;

import com.eop.baseservice.common.response.SortBy;
import com.eop.baseservice.common.response.SortByDirection;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToSortByConverterHelper implements Converter<String, SortBy> {

    @Override
    public SortBy convert(String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }

        SortBy sortBy = new SortBy();
        if (source.contains(":")) {
            String[] parts = source.split(":");
            sortBy.setPropertyName(parts[0].trim());
            sortBy.setDirection(SortByDirection.valueOf(parts[1].trim().toUpperCase()));
        } else {
            sortBy.setPropertyName(source.trim());
            sortBy.setDirection(SortByDirection.ASC);
        }

        return sortBy;
    }
}
