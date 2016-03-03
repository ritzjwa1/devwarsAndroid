package com.example.bukbukbukh.movierating;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class mainProfilePage extends AppCompatActivity {

    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_profile_page);
        Intent intent = getIntent();
        username = intent.getStringExtra("USER_NAME");
        Log.d("UserName", username);

        String url = "https://pandango.herokuapp.com/showProfile/" + username;
        String response = new LoginTask().execute(url).toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_profile_page, menu);
        return true;
    }

    private class LoginTask extends AsyncTask<String, Long, String> {
        protected String doInBackground(String... urls) {
            try {
                HttpRequest request = HttpRequest.get(urls[0]);
                String result = null;
                if (request.ok()) {
                    result = request.body();
                }
                return result;
            } catch (HttpRequest.HttpRequestException exception) {
                return null;
            }
        }

        protected void onProgressUpdate(Long... progress) {
            //Log.d("MyApp", "Downloaded bytes: " + progress[0]);
        }

        protected void onPostExecute(String file) {
            if (file != null) {
                try {
                    JSONArray jsArray = new JSONArray(file);
                    JSONObject obj = jsArray.getJSONObject(0);
                    TextView ed1 = (TextView) findViewById(R.id.name_main);
                    TextView ed2 = (TextView) findViewById(R.id.major_main);
                    TextView ed3 = (TextView) findViewById(R.id.bio_main);
                    ed1.setText(obj.getString("name"));
                    ed2.setText(obj.getString("major"));
                    ed3.setText(obj.getString("bio"));
                } catch(JSONException e) {

                }
            }
            else {
                Log.d("MyApp", "Download failed");
            }
        }
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
}
