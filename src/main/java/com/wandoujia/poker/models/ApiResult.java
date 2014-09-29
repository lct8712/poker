package com.wandoujia.poker.models;


/**
 * @author chentian
 */
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

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    @Override
    public String toString() {
        return "ApiResult{" +
                "isSuccess=" + isSuccess +
                ", errorInfo='" + errorInfo + '\'' +
                '}';
    }
}
