package ar.com.beehive.beehivedemoapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ToggleButton;

public class ButtonExample extends ActionBarActivity {

    private static final String DEBUG_TAG = "Beehive-ButtonExample";
    private ToggleButton toggle;
    Handler handler;

    {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String value = (String) msg.obj;
                Log.d(DEBUG_TAG, "Result: " + value);

            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_example);
        toggle = (ToggleButton) findViewById(R.id.toggleButton);
        getStatus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_button_example, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getStatus () {

        String statusURL = "http://192.168.40.26/Codiad-v.2.4.1/workspace/beehive/Android/beehiveCloud.php?action=Status";

        NetworkActivity networkActivity = new NetworkActivity();
        networkActivity.setHandler(handler);
        networkActivity.contactCloud(statusURL);

    }


}
