package com.example.api_at;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    RecyclerView recyclerView;
    URL url = null;
    HttpURLConnection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new calltask().execute();
    }

    private class calltask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                url = new URL("https://jsonplaceholder.typicode.com/posts");
                URLConnection connection = url.openConnection();
                conn = (HttpURLConnection) connection;
                conn.getResponseCode();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-length", "0");
                conn.setUseCaches(true);
                conn.setAllowUserInteraction(true);
                conn.connect();


            } catch (Exception e) {
                Log.d("error", e.toString());
            }
            //http://androidcss.com/android/fetch-json-data-android/
            try {
                int response_code = conn.getResponseCode();
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    return (result.toString());

                } else {

                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String result) {

            //this method will be running on UI thread


            List<AndroidVersion> data = new ArrayList<>();
            Log.d("result", result);

            try {

                JSONArray jArray = new JSONArray(result);

                // Extract data from json and store into ArrayList as class objects
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json_data = jArray.getJSONObject(i);

                    AndroidVersion fishData = new AndroidVersion();
                    fishData.userId = json_data.getString("userId");
                    fishData.id = json_data.getString("id");
                    fishData.title = json_data.getString("title");
                    fishData.body = json_data.getString("body");
                    data.add(fishData);
                }
                recyclerView = findViewById(R.id.rv1);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                DataAdapter dataAdapter=new DataAdapter(getApplicationContext(), (ArrayList<AndroidVersion>) data);
                recyclerView.setAdapter(dataAdapter);

            } catch (JSONException e) {
                Log.d("er", e.getMessage());
            }

        }
    }
}
