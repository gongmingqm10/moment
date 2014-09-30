package org.gongming.moment.api;

import java.io.IOException;

import org.gongming.moment.model.Moment;
import org.gongming.moment.network.NetworkUtil;

public class TweetListApi extends AbsApi<Moment[]> {

    private static final String URL = "http://thoughtworks-ios.herokuapp.com/user/jsmith/tweets";

    @Override
    public ApiCallResponse<Moment[]> call() {
        ApiCallResponse<Moment[]> response = new ApiCallResponse<Moment[]>(this);
        Object responseObject = NetworkUtil.call(URL, Moment[].class);
        if (responseObject instanceof String) {
            response.setErrorMessage((String)responseObject);
        } else {
            response.setData((Moment[]) responseObject);
        }
        return response;
    }
}
