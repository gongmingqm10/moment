package moment.minggong.org.moment.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

import moment.minggong.org.moment.R;
import moment.minggong.org.moment.api.ApiCallResponse;
import moment.minggong.org.moment.api.TweetListApi;
import moment.minggong.org.moment.api.UserProfileApi;
import moment.minggong.org.moment.model.Moment;
import moment.minggong.org.moment.model.User;
import moment.minggong.org.moment.network.NetworkMgr;
import moment.minggong.org.moment.view.TweetListAdapter;

public class MainActivity extends Activity implements NetworkMgr.OnApiCallFinishListener {

    private static final int COUNT_PER_PAGE = 5;
    private TweetListAdapter listAdapter;
    private TextView userName;
    private ImageView profileImage;
    private ImageView userAvatar;
    private View footerView;
    private List<Moment> momentList;

    private PullToRefreshListView pullToRefreshListView;
    private ListView listView;

    private int currentPage = 0;
    private AbsListView.OnScrollListener onScrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView absListView, int i) {

        }

        @Override
        public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0) {
                loadMoments(++currentPage);
            }
        }
    };

    private PullToRefreshBase.OnRefreshListener onRefreshListener = new PullToRefreshBase.OnRefreshListener() {
        @Override
        public void onRefresh(PullToRefreshBase refreshView) {
            String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                    DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
            refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
            startSync();
        }
    };

    private UserProfileApi userProfileApi;
    private TweetListApi tweetListApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        startSync();
    }

    private void init() {
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.listView);
        pullToRefreshListView.setOnRefreshListener(onRefreshListener);
        listView = pullToRefreshListView.getRefreshableView();

        View headerView = getLayoutInflater().inflate(R.layout.tweet_header_view, null);
        profileImage = (ImageView) headerView.findViewById(R.id.profileImage);
        userName = (TextView) headerView.findViewById(R.id.userName);
        userAvatar = (ImageView) headerView.findViewById(R.id.userAvatar);
        listView.addHeaderView(headerView, null, false);

        footerView = getLayoutInflater().inflate(R.layout.loading_layout, null);
        listView.addFooterView(footerView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        NetworkMgr.getInstance().addOnApiCallFinishListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        NetworkMgr.getInstance().removeOnApiCallFinishListener(this);
    }

    private void startSync() {
        currentPage = 0;
        listAdapter = new TweetListAdapter(this);
        listView.setAdapter(listAdapter);

        userProfileApi = new UserProfileApi();
        tweetListApi = new TweetListApi();
        NetworkMgr.getInstance().startSync(userProfileApi);
        NetworkMgr.getInstance().startSync(tweetListApi);

    }

    private void loadMoments(int page) {
        int fromIndex = page * COUNT_PER_PAGE;
        int toIndex = Math.min(page * COUNT_PER_PAGE + COUNT_PER_PAGE, momentList.size());
        if (fromIndex >= toIndex) {
            listView.removeFooterView(footerView);
            return;
        }
        List<Moment> currentPageData = momentList.subList(fromIndex, toIndex);
        listAdapter.addData(currentPageData);
    }

    @Override
    public void onApiCallFinish(ApiCallResponse<?> response) {
        if (response == null) return;
        if (response.getAbsApi() == userProfileApi) {
            if (response.isSuccess()) {
                User user = (User) response.getData();
                Picasso.with(MainActivity.this).load(user.getProfileImage()).into(profileImage);
                Picasso.with(MainActivity.this).load(user.getAvatar()).into(userAvatar);
                userName.setText(user.getNick());
            } else {
                Toast.makeText(this, response.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        } else if (response.getAbsApi() == tweetListApi) {
            if (response.isSuccess()) {
                MainActivity.this.momentList = Arrays.asList((Moment[]) response.getData());
                loadMoments(0);
                listView.setOnScrollListener(onScrollListener);
            } else {
                Toast.makeText(this, response.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
            if (pullToRefreshListView.isRefreshing()) pullToRefreshListView.onRefreshComplete();
        }
    }
}
