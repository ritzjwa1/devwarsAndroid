package com.example.bukbukbukh.movierating;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecentlyRated extends AppCompatActivity {

    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recently_rated);
        Intent intent = getIntent();
        username = intent.getStringExtra("USER_NAME");
        new DispRating().execute("https://pandango.herokuapp.com/dispRecentRated/" + username);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recently_rated, menu);
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

    private class DispRating extends AsyncTask<String, Long, String> {
        protected String doInBackground(String... urls) {
            try {
                HttpRequest request = HttpRequest.get(urls[0]);
                String result = null;
                if (request.ok()) {
                    result = request.body();
                }
                return result;
            } catch (HttpRequest.HttpRequestException exception) {
                Log.d("PROBLEM", "PROBLEM");
                return null;
            }
        }

        protected void onProgressUpdate(Long... progress) {
            //Log.d("MyApp", "Downloaded bytes: " + progress[0]);
        }

        protected void onPostExecute(String file) {
            if (file != null) {
                Log.d("RESULT", file);
                ListView lv = (ListView) findViewById(R.id.list_recently_rated);
                try {
                    JSONArray jsA = new JSONArray(file);
                    ArrayList<String> mainMovieList = new ArrayList<String>();
                    JSONObject obj1 = null;
                    for (int i = 0; i < jsA.length(); i++) {
                        try {
                            obj1 = jsA.getJSONObject(i);
                            mainMovieList.add(obj1.getString("movie_name"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    lv.setAdapter(new ArrayAdapter<String>(RecentlyRated.this,
                            R.layout.list_item_movies, R.id.movieName, mainMovieList));
                } catch (JSONException e) {
                    Log.d("NULL", "NULL");
                }

            }
            else {
                Log.d("MyApp", "Download failed");
            }
        }
    }

    public void goBackHome(View view) {
        Intent intent = new Intent(this, Home.class);
        intent.putExtra("USER_NAME", username);
        startActivity(intent);
    }
}
