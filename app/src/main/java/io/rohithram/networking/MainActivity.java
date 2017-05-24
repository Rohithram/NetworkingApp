package io.rohithram.networking;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView tv_display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_display = (TextView) findViewById(R.id.tv_display);

        CustomTask customTask = new CustomTask();

        customTask.execute("https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2016-01-01&endtime=2016-05-02&minfelt=50&minmagnitude=5");

    }

    class CustomTask extends AsyncTask<String, Void, List<Earthquake>>{

        @Override
        protected void onPostExecute(List<Earthquake> earthquakes) {
            tv_display.setText("");
            for (int i = 0; i < earthquakes.size(); ++i) {
                Earthquake earthquake = earthquakes.get(i);
                tv_display.append(
                        earthquake.getLocation() + " - "
                                + earthquake.getMagnitude() + " - "
                                + earthquake.getTime()
                                + "\n\n"
                );
            }
        }

        @Override
        protected List<Earthquake> doInBackground(String... params) {
            List<Earthquake> earthquakeList = NetworkHelper.fetchEarthquakeData(params[0]);
            Log.e("logcat1", "Downloading");
            return earthquakeList;
        }
    }
}
