package org.gongming.moment.activity;

import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.gongming.moment.R;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;


import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.robolectric.Robolectric.buildActivity;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {

    private MainActivity mainActivity;

    @Before
    public void setUp() {
        mainActivity = buildActivity(MainActivity.class).create().get();
    }

    @Test
    public void shouldContainHeaderAndFooterView() {
        PullToRefreshListView pullToRefreshListView = (PullToRefreshListView) mainActivity.findViewById(R.id.listView);
        ListView listView = pullToRefreshListView.getRefreshableView();
        assertNotNull(listView);
        assertThat(listView.getHeaderViewsCount(), is(2)); // Another extra header is caused by PullToRefreshListView
        assertThat(listView.getFooterViewsCount(), is(1));

    }

    @Test
    public void shouldShowTitle() {
        TextView textView = (TextView) mainActivity.findViewById(R.id.title);
        assertThat(textView.getText().toString(), is("Moment"));
    }

}