package com.eop.baseservice.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PagingRequest {

    @NotNull
    private Integer page = 0;

    @NotNull
    private Integer pageSize = 10;

    private List<SortBy> sortBy;

}