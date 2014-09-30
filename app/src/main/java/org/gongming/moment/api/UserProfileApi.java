package org.gongming.moment.api;

import java.io.IOException;

import org.gongming.moment.model.Moment;
import org.gongming.moment.model.User;
import org.gongming.moment.network.NetworkUtil;

public class UserProfileApi extends AbsApi<User> {

    private final static String URL = "http://thoughtworks-ios.herokuapp.com/user/jsmith";

    @Override
    public ApiCallResponse<User> call() {
        ApiCallResponse<User> response = new ApiCallResponse<User>(this);
        Object responseObject = NetworkUtil.call(URL, User.class);
        if (responseObject instanceof String) {
            response.setErrorMessage((String)responseObject);
        } else {
            response.setData((User) responseObject);
        }
        return response;
    }
}
