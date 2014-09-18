package moment.minggong.org.moment.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;

import moment.minggong.org.moment.R;
import moment.minggong.org.moment.model.User;
import moment.minggong.org.moment.network.NetworkUtil;


public class MainActivity extends Activity{

    private ListView listView;
    private TextView intro;


    private static final int HTTP_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initCmp();
        startHttpRequest();
    }

    private void initCmp() {
        listView = (ListView) findViewById(R.id.listView);
        intro = (TextView) findViewById(R.id.intro);
    }


    private void startHttpRequest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    User user = (User) NetworkUtil.call("http://thoughtworks-ios.herokuapp.com/user/jsmith", User.class);
                    Message message = new Message();
                    message.what = HTTP_REQUEST_CODE;
                    message.obj = user;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == HTTP_REQUEST_CODE) {
                User user = (User) msg.obj;
                intro.setText(user.toString());
            }
        }
    };

}
