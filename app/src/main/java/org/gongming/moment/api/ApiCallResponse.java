package org.gongming.moment.api;

import java.io.Serializable;

public class ApiCallResponse<T extends Object> implements Serializable {
    private T data;
    private String errorMessage;
    private AbsApi<T> absApi;
    private boolean isSuccess;

    public ApiCallResponse() {
    }

    public ApiCallResponse(AbsApi<T> absApi) {
        this.absApi = absApi;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
        isSuccess = true;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        isSuccess = false;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public AbsApi<T> getAbsApi() {
        return absApi;
    }

    public void setAbsApi(AbsApi<T> absApi) {
        this.absApi = absApi;
    }

}
