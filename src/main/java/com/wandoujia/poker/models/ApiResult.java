package com.wandoujia.poker.models;

import lombok.Data;

/**
 * @author chentian
 */
@Data
public class ApiResult {

    private boolean isSuccess;

    private String errorInfo;

    public ApiResult(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public ApiResult(boolean isSuccess, String errorInfo) {
        this.isSuccess = isSuccess;
        this.errorInfo = errorInfo;
    }
}
