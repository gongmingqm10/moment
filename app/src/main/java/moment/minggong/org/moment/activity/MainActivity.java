package moment.minggong.org.moment.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import moment.minggong.org.moment.R;
import moment.minggong.org.moment.model.Moment;
import moment.minggong.org.moment.model.User;
import moment.minggong.org.moment.network.NetworkUtil;
import moment.minggong.org.moment.view.TweetListAdapter;

public class MainActivity extends Activity {

    private static final int COUNT_PER_PAGE = 5;
    private static final int HTTP_PROFILE_REQUEST_CODE = 100;
    private static final int HTTP_TWEET_REQUEST_CODE = 200;
    private static final String TWEET_LIST_API = "http://thoughtworks-ios.herokuapp.com/user/jsmith/tweets";
    private static final String PROFILE_API = "http://thoughtworks-ios.herokuapp.com/user/jsmith";
    private TweetListAdapter listAdapter;
    private TextView userName;
    private ImageView profileImage;
    private ImageView userAvatar;
    private View footerView;
    private ListView listView;
    private List<Moment> momentList;
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
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == HTTP_PROFILE_REQUEST_CODE && msg.obj != null) {
                User user = (User) msg.obj;
                Picasso.with(MainActivity.this).load(user.getProfileImage()).into(profileImage);
                Picasso.with(MainActivity.this).load(user.getAvatar()).into(userAvatar);
                userName.setText(user.getNick());
            } else if (msg.what == HTTP_TWEET_REQUEST_CODE && msg.obj != null) {
                MainActivity.this.momentList = Arrays.asList((Moment[]) msg.obj);
                loadMoments(0);
                listView.setOnScrollListener(onScrollListener);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initCmp();
        startHttpRequest();
    }

    private void initCmp() {
        listView = (ListView) findViewById(R.id.listView);
        listAdapter = new TweetListAdapter(this);
        View headerView = getLayoutInflater().inflate(R.layout.tweet_header_view, null);
        profileImage = (ImageView) headerView.findViewById(R.id.profileImage);
        userName = (TextView) headerView.findViewById(R.id.userName);
        userAvatar = (ImageView) headerView.findViewById(R.id.userAvatar);
        listView.addHeaderView(headerView, null, false);
        footerView = getLayoutInflater().inflate(R.layout.loading_layout, null);
        listView.addFooterView(footerView);
        listView.setAdapter(listAdapter);
    }

    private void startHttpRequest() {
        requestUserProfile();
        requestTweetList();
    }

    private void requestTweetList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Moment[] moments = (Moment[]) NetworkUtil.call(TWEET_LIST_API, Moment[].class);
                    Message message = new Message();
                    message.what = HTTP_TWEET_REQUEST_CODE;
                    message.obj = moments;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void requestUserProfile() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    User user = (User) NetworkUtil.call(PROFILE_API, User.class);
                    Message message = new Message();
                    message.what = HTTP_PROFILE_REQUEST_CODE;
                    message.obj = user;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
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

}
