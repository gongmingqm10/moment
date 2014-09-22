package moment.minggong.org.moment.activity;

import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHttpResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import java.io.UnsupportedEncodingException;

import moment.minggong.org.moment.R;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.robolectric.Robolectric.buildActivity;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {

    private final ActivityController<MainActivity> controller = buildActivity(MainActivity.class);

    @Test
    public void shouldContainHeaderAndFooterView() {
        MainActivity mainActivity = controller.create().get();
        ListView listView = (ListView) mainActivity.findViewById(R.id.listView);
        assertNotNull(listView);
        assertThat(listView.getAdapter().getCount(), is(2));
    }

    @Test
    public void shouldShowTitle() {
        MainActivity mainActivity = controller.create().start().resume().get();
        TextView textView = (TextView) mainActivity.findViewById(R.id.title);
        assertThat(textView.getText().toString(), is("朋友圈"));
    }

}