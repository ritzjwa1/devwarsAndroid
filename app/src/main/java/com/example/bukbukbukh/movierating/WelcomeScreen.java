package com.example.bukbukbukh.movierating;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class WelcomeScreen extends AppCompatActivity {

    MainDatabase mDB;

    private class TestTask extends AsyncTask<String, Long, String> {
        protected String doInBackground(String... urls) {
            try {
                HttpRequest request = HttpRequest.get(urls[0]);
                String file = null;
                if (request.ok()) {
                    file = request.body();
                }
                return file;
            } catch (HttpRequest.HttpRequestException exception) {
                return null;
            }
        }

        protected void onProgressUpdate(Long... progress) {
            //Log.d("MyApp", "Downloaded bytes: " + progress[0]);
        }

        protected void onPostExecute(String file) {
            if (file != null)
                Log.d("symbol", file);
            else
                Log.d("MyApp", "Download failed");
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        mDB = MainDatabase.getInstance(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome_screen, menu);
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

    // Go to Login Screen
    // Checks if someone is already logged in and redirects to the profile page if so
    public void goLoginScreen(View view) {

        String userName = mDB.getLoginStatusTrue();
        if (userName != null) {
            Intent intent = new Intent(this, MainMenu.class);
            intent.putExtra("USERNAME", userName);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, login_screen.class);
            startActivity(intent);
        }

    }

    // Goes to Register screen
    public void goRegisterScreen(View view) {
        Intent intent = new Intent(this, register_screen.class);
        startActivity(intent);
    }
}
