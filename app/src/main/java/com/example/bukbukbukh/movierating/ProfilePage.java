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

public class ProfilePage extends AppCompatActivity {

    String username;
    String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        Intent intent = getIntent();
        username = intent.getStringExtra("USER_NAME");
        Log.d("user", username);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile_page, menu);
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

    private class ChangeProfileTask extends AsyncTask<String, Long, String> {
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
                Intent intent = new Intent(ProfilePage.this, MainMenu.class);
                startActivity(intent);
            }
            else {
                Log.d("MyApp", "Download failed");
            }
        }
    }

    public void changePassword(View view) {
        EditText password1 = (EditText) findViewById(R.id.change_password);
        EditText password2 = (EditText) findViewById(R.id.change_password2);
        password = password1.getText().toString();
        if (password.equals(password2.getText().toString())) {
            new ChangeProfileTask().execute("https://pandango.herokuapp.com/editProfile/" + username + "/" + password);
        } else {

        }
    }
}
