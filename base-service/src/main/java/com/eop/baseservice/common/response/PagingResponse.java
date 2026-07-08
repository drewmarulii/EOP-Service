package com.eop.baseservice.common.response;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PagingResponse {

    private Integer page;

    private Integer pageSize;

    private Integer totalPage;

    private Long totalItem;

    private List<SortBy> sortBy;

}

