package io.rohithram.networking;

/**
 * Created by rohithram on 19/5/17.
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Divyanshu on 5/12/17.
 */

public class NetworkHelper {

    public static List<Earthquake> fetchEarthquakeData(String requestUrl){
        URL url = null;
        try {
            url = new URL(requestUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        String response = null;

        try {
            response = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return extractEarthquakeData(response);
    }


    private static String makeHttpRequest(URL url) throws IOException{
        String response = "";

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(15000);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                response = getStringResponse(inputStream);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
            inputStream.close();
        }

        return response;
    }

    private static String getStringResponse(InputStream inputStream) throws IOException{

        StringBuilder builder = new StringBuilder();

        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line = bufferedReader.readLine();

        while (line != null){
            builder.append(line);
            line = bufferedReader.readLine();
        }

        return builder.toString();
    }

    private static List<Earthquake> extractEarthquakeData(String response) {
        List<Earthquake> earthquakeList = new ArrayList<>();


        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray features = jsonObject.getJSONArray("features");

            for (int i = 0; i < features.length(); ++i){
                JSONObject data = features.getJSONObject(i);
                JSONObject properties = data.getJSONObject("properties");

                Earthquake earthquake = new Earthquake(
                        properties.getString("place"),
                        properties.getLong("time"),
                        properties.getDouble("mag")
                );

                earthquakeList.add(earthquake);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return earthquakeList;
    }
}