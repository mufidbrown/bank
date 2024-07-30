package com.dbs.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {
    
    private int status;
    private boolean success;
    private String message;
    @JsonIgnoreProperties({"pageable", "sort"})
    private T data;

    public static <T> BaseResponse<T> ok(int status, String message, T data) {
        return BaseResponse.<T>builder()
                .status(status)
                .success(true)
                .message(StringUtils.isNotBlank(message) ? message : "OK")
                .data(data)
                .build();
    }

    public static <T> BaseResponse<T> ok(T data) {
        return ok(200, "OK", data);
    }

    public static <T> BaseResponse<T> ok(String message, T data) {
        return ok(200, message, data);
    }

    public static <T> BaseResponse<T> error(int status, String message) {
        return BaseResponse.<T>builder()
                .status(status)
                .success(false)
                .message(StringUtils.isNotBlank(message) ? message : "Error")
                .data(null)
                .build();
    }

    public static <T> BaseResponse<T> error(String message) {
        return error(400, message);
    }
}