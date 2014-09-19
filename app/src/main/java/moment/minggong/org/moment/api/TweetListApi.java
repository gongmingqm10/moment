package moment.minggong.org.moment.api;

import java.io.IOException;

import moment.minggong.org.moment.api.AbsApi;
import moment.minggong.org.moment.api.ApiCallResponse;
import moment.minggong.org.moment.model.Moment;
import moment.minggong.org.moment.network.NetworkUtil;

/**
 * Created by minggong on 9/19/14.
 */
public class TweetListApi extends AbsApi<Moment[]> {

    private static final String URL = "http://thoughtworks-ios.herokuapp.com/user/jsmith/tweets";

    @Override
    public ApiCallResponse<Moment[]> call() {
        ApiCallResponse<Moment[]> response = new ApiCallResponse<Moment[]>(this);
        try {
            Moment[] moments = (Moment[]) NetworkUtil.call(URL, Moment[].class);
            response.setData(moments);
        } catch (IOException e) {
            e.printStackTrace();
            response.setErrorMessage(e.getMessage().toString());
        } finally {
            return response;
        }
    }
}
