package moment.minggong.org.moment.activity;

import android.widget.ListView;

import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHttpResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.util.ActivityController;

import java.io.UnsupportedEncodingException;

import moment.minggong.org.moment.R;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.robolectric.Robolectric.buildActivity;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {

    private final ActivityController<MainActivity> controller = buildActivity(MainActivity.class);
    private ProtocolVersion httpProtocolVersion;
    private final static String PROFILE_URL = "http://thoughtworks-ios.herokuapp.com/user/jsmith";
    private static final String TWEET_LIST_URL = "http://thoughtworks-ios.herokuapp.com/user/jsmith/tweets";

    @Before
    public void setUp() {
        httpProtocolVersion = new ProtocolVersion("HTTP", 1, 1);
    }

    @Test
    public void shouldShowTipsWhenNoDataRequested() throws UnsupportedEncodingException {
        HttpResponse emptyResponse = new BasicHttpResponse(httpProtocolVersion, 200, "OK");
        emptyResponse.setEntity(new StringEntity("[]"));
        Robolectric.addHttpResponseRule(TWEET_LIST_URL, emptyResponse);
        MainActivity mainActivity = controller.create().start().resume().get();
        ListView listView = (ListView)mainActivity.findViewById(R.id.listView);
        assertThat(listView.getAdapter().getCount(), is(0));
    }

}