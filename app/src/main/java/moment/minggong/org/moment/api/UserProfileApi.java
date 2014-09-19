package moment.minggong.org.moment.api;

import java.io.IOException;

import moment.minggong.org.moment.model.User;
import moment.minggong.org.moment.network.NetworkUtil;

public class UserProfileApi extends AbsApi<User> {

    private final static String URL = "http://thoughtworks-ios.herokuapp.com/user/jsmith";

    @Override
    public ApiCallResponse<User> call() {
        ApiCallResponse<User> response = new ApiCallResponse<User>(this);
        try {
            User user = (User) NetworkUtil.call(URL, User.class);
            response.setData(user);
        } catch (IOException e) {
            e.printStackTrace();
            response.setErrorMessage(e.getMessage().toString());
        } finally {
            return response;
        }
    }
}
