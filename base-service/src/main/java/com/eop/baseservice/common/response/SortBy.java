package com.eop.baseservice.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SortBy {

    private String propertyName;

    private SortByDirection direction;

}