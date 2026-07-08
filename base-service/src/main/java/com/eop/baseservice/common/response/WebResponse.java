package com.eop.baseservice.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WebResponse<T> {

    @JsonProperty("code")
    private Integer code;

    @JsonProperty("status")
    private String status;

    @JsonProperty("column")
    private List<CustomColumn> column;

    @JsonProperty("data")
    private T data;

    @JsonProperty("paging")
    private PagingResponse paging;

    @JsonProperty("errors")
    private Map<String, List<String>> errors;

    @JsonProperty("metadata")
    private Map<String, Object> metadata;

    public static <T> WebResponse<T> failure(
            Integer code,
            Map<String, List<String>> errors) {

        return WebResponse.<T>builder()
                .code(code)
                .status("FAILED")
                .errors(errors)
                .build();
    }
}