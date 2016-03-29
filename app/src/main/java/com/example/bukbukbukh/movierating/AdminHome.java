package com.example.bukbukbukh.movierating;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AdminHome extends AppCompatActivity {

    String username;
    String major;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        /**
         * This code in the oncreate method stores the username to reference the database for any further information
         */
        Intent intent = getIntent();
        username = intent.getStringExtra("USER_NAME");
        major = intent.getStringExtra("MAJOR");
        TextView dispUserName = (TextView) findViewById(R.id.disp_username);
        dispUserName.setText("Welcome " + username);
        //Log.d("anyString", username);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin_home, menu);
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

    private class Logout extends AsyncTask<String, Long, String> {
        protected String doInBackground(String... urls) {
            try {

                HttpRequest request = HttpRequest.post(urls[0]);
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
                Intent intent = new Intent(AdminHome.this, WelcomeScreen.class);
                startActivity(intent);
            }
            else {
                Log.d("MyApp", "Download failed");
            }
        }
    }


    /**
     * Making an http request to change the loginstatus to 0 after the logout button is pressed
     * @param view
     */
    public void logout(View view) {
        new Logout().execute("https://pandango.herokuapp.com/changeLoginStatus/" + username);
    }


    /**
     * Transitions to the profile page
     * @param view
     */
    public void goProfilePage(View view) {
        Intent intent = new Intent(this, mainProfilePage.class);
        intent.putExtra("USER_NAME", username);
        startActivity(intent);
    }

    public void goToSearchPages(View view) {
        Intent intent = new Intent(this, SearchPages.class);
        intent.putExtra("USER_NAME", username);
        intent.putExtra("MAJOR", major);
        startActivity(intent);
    }

    public void goRatePage(View view) {
        Intent intent = new Intent(this, RateMovie.class);
        intent.putExtra("USER_NAME", username);
        startActivity(intent);
    }

    public void goRecentlyRated(View view) {
        Intent intent = new Intent(this, RecentlyRated.class);
        intent.putExtra("USER_NAME", username);
        startActivity(intent);
    }

    public void userMod(View view) {
        Intent intent = new Intent(this, UserMod.class);
        intent.putExtra("USER_NAME", username);
        intent.putExtra("MAJOR", major);
        startActivity(intent);
    }
}
