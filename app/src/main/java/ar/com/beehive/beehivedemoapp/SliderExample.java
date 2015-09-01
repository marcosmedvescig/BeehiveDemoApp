package ar.com.beehive.beehivedemoapp;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.ToggleButton;

public class SliderExample extends ActionBarActivity {

    private static final String DEBUG_TAG = "Beehive-SliderExample";
    private SeekBar seekBar;
    Handler handler;
    NetworkActivity networkActivity = new NetworkActivity();


    {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String value = (String) msg.obj;
                Log.d(DEBUG_TAG, "Result: " + value);

                try{
                    int num = Integer.parseInt(value);
                    seekBar.setProgress(num);
                } catch (NumberFormatException e) {
                    // not an integer!
                }

            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider_example);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        //set change listener
        seekBar.setOnSeekBarChangeListener(

                new SeekBar.OnSeekBarChangeListener() {

                    @Override
                    public void onProgressChanged(SeekBar seekBar,int progresValue, boolean fromUser) {

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        // Do something here,

                        //if you want to do anything at the start of
                        // touching the seekbar
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        // Display the value in textview
                        int actualValue = seekBar.getProgress();

                        String changeUrl = "http://192.168.40.26/Codiad-v.2.4.1/workspace/beehive/Android/beehiveCloud.php?action=ChangeSeekBar&value=" + actualValue;
                        networkActivity.contactCloud(changeUrl);
                    }
                });





        networkActivity.setHandler(handler);
        networkActivity.checkNetworkStatus(this);
        getStatus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_slider_example, menu);
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

        String statusURL = "http://192.168.40.26/Codiad-v.2.4.1/workspace/beehive/Android/beehiveCloud.php?action=SeekBarStatus";
        networkActivity.contactCloud(statusURL);

    }

}
