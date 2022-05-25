package com.example.cpen321_amberbhatt_m1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SurpriseActivity extends AppCompatActivity {

    private Button getMemeButton;
    private ImageView randomMeme;
    public static String randomMemeUrl = "https://static.boredpanda.com/blog/wp-content/uploads/2014/07/cat-waiting-window-60.jpg";
    public final static String memeApiEndpoint = "https://meme-api.herokuapp.com/gimme/wholesomememes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surprise);

        getMemeButton = findViewById(R.id.get_fact_button);
        randomMeme = findViewById(R.id.meme_image);
        Glide.with(this).load(randomMemeUrl).into(randomMeme);

        getMemeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetDataTask getDataTask = new GetDataTask();
                getDataTask.execute();
                Glide.with(SurpriseActivity.this).load(randomMemeUrl).into(randomMeme);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SurpriseActivity.this, MainActivity.class));
        finish();
    }

}

class GetDataTask extends AsyncTask<String, String, String> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // display a progress dialog to show the user what is happening
    }

    @Override
    protected String doInBackground(String... params) {

        // Fetch data from the API in the background.

        String result = "";
        try {
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(SurpriseActivity.memeApiEndpoint);
                //open a URL coonnection

                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();

                InputStreamReader isw = new InputStreamReader(in);

                int data = isw.read();

                while (data != -1) {
                    result += (char) data;
                    data = isw.read();

                }

                // return the data to onPostExecute method
                return result;

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Exception: " + e.getMessage();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s) {

        try {
            JSONObject jsonObject = new JSONObject(s);
            SurpriseActivity.randomMemeUrl = jsonObject.getString("url");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}