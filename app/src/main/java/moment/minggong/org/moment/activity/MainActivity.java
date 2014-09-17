package moment.minggong.org.moment.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import moment.minggong.org.moment.R;


public class MainActivity extends Activity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initCmp();
    }

    private void initCmp() {
        listView = (ListView) findViewById(R.id.listView);
    }



}
