package ar.com.beehive.beehivedemoapp;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class GraphExample extends ActionBarActivity {

    private static final String DEBUG_TAG = "Beehive-GraphExample";
    private ArrayList<Entry> valuesSeries1 = new ArrayList<Entry>();
    private ArrayList<String> labelsSeries1 = new ArrayList<String>();
    Handler handler;
    NetworkActivity networkActivity = new NetworkActivity();


    {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String value = (String) msg.obj;
                Log.d(DEBUG_TAG, "Result: " + value);

                processData(value);
                drawGraph();

            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_example);

        networkActivity.setHandler(handler);
        networkActivity.checkNetworkStatus(this);
        getGraphDataFromCloud();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_graph_example, menu);
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

    private void processData(String valuesString){

        //String valuesString = "1,14;2,23;3,21;4,21;5,20;6,20;7,16;8,11;9,17;10,23;";
        String[] valuesArray = valuesString.split(";");

        int i = 0;
        for (String valueString : valuesArray){

            String[] valueArray = valueString.split(",");
            valuesSeries1.add(new Entry(Float.valueOf(valueArray[1]), i));
            labelsSeries1.add(valueArray[0]);
            i++;

        }


        Log.d(DEBUG_TAG, "Data Procesed");

    }

    private void getGraphDataFromCloud(){

        String getGraphDataURL = "http://192.168.40.26/Codiad-v.2.4.1/workspace/beehive/Android/beehiveCloud.php?action=Graph";
        networkActivity.contactCloud(getGraphDataURL);

    }

    private void drawGraph(){

        // in this example, a LineChart is initialized from xml
        LineChart chart = (LineChart) findViewById(R.id.chart);
        chart.setDrawBorders(Boolean.FALSE);
        chart.setAutoScaleMinMaxEnabled(Boolean.TRUE);
        chart.setDrawGridBackground(Boolean.FALSE);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getXAxis().setDrawGridLines(false);
/*
        //Create a limit line
        YAxis hotLimit = chart.getAxisLeft();
        LimitLine lh = new LimitLine(27f, "Too Hot");
        lh.setLineColor(Color.RED);
        lh.setLineWidth(1f);
        lh.setTextColor(Color.BLACK);
        lh.setTextSize(8f);
        //hotLimit.addLimitLine(lh);

        //Create a limit line
        YAxis coldLimit = chart.getAxisLeft();
        LimitLine lc = new LimitLine(15f, "Too Cold");
        lc.setLineColor(Color.BLUE);
        lc.setLineWidth(1f);
        lc.setTextColor(Color.BLACK);
        lc.setTextSize(8f);
        //coldLimit.addLimitLine(lc);

        //Create the values
        ArrayList<Entry> valsComp1 = new ArrayList<Entry>();
        Entry c1e1 = new Entry(10.1f, 0); // 0 == quarter 1
        valsComp1.add(c1e1);
        Entry c1e2 = new Entry(14.5f, 1); // 1 == quarter 2 ...
        valsComp1.add(c1e2);
        Entry c1e3 = new Entry(20.2f, 2); // 1 == quarter 2 ...
        valsComp1.add(c1e3);
        Entry c1e4 = new Entry(26.9f, 3); // 1 == quarter 2 ...
        valsComp1.add(c1e4);
        valsComp1.add(new Entry(23.9f, 4));
*/

        LineDataSet setComp1 = new LineDataSet(valuesSeries1, "Sensor 1");
        setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);
        setComp1.setLineWidth(3f);
        setComp1.setColor(Color.BLUE);
        setComp1.setCircleColor(Color.BLUE);
        setComp1.setCircleSize(6f);

        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(setComp1);
/*
        //Create the labels
        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("10am"); xVals.add("11am"); xVals.add("12am"); xVals.add("01pm"); xVals.add("02pm");
*/
        //Draw the graph
        LineData data = new LineData(labelsSeries1, dataSets);
        chart.setData(data);
        //chart.invalidate(); // refresh

        //Change the view
        chart.moveViewToY(20f,YAxis.AxisDependency.LEFT);
        chart.invalidate();

    }
}
