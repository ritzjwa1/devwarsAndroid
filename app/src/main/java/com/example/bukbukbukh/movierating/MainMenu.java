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

import java.util.HashMap;
import java.util.Map;

public class MainMenu extends AppCompatActivity {

    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        Intent intent = getIntent();
        username = intent.getStringExtra("USERNAME");
        TextView dispUserName = (TextView) findViewById(R.id.disp_username);
        dispUserName.setText("Welcome " + username);
        Log.d("anyString", username);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
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
                Intent intent = new Intent(MainMenu.this, WelcomeScreen.class);
                startActivity(intent);
            }
            else {
                Log.d("MyApp", "Download failed");
            }
        }
    }

    public void logout(View view) {
        new Logout().execute("https://pandango.herokuapp.com/changeLoginStatus/" + username);
    }

    public void goProfilePage(View view) {
        Intent intent = new Intent(this, ProfilePage.class);
        intent.putExtra("USER_NAME", username);
        startActivity(intent);
    }
}
