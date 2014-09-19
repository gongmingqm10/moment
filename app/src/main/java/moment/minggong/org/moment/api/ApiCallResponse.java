package moment.minggong.org.moment.api;

import java.io.Serializable;

import moment.minggong.org.moment.api.AbsApi;

/**
 * Created by minggong on 9/18/14.
 */
public class ApiCallResponse<T extends Object> implements Serializable {
    private T data;
    private String errorMessage;
    private AbsApi<T> absApi;
    private boolean isSuccess;

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

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public AbsApi<T> getAbsApi() {
        return absApi;
    }

    public void setAbsApi(AbsApi<T> absApi) {
        this.absApi = absApi;
    }
}
