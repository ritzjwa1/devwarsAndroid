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

public class ProfilePage extends AppCompatActivity {
    /**
     * Storing the username and other details to use in other methods within this class
     */
    String username;
    String password;
    String bio;
    String major;

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

    /**
     * An async call that helps in changing password, bio and major
     */
    private class ChangeProfileTask extends AsyncTask<String, Long, String> {
        protected String doInBackground(String... urls) {
            try {
                Map<String, String> map = new HashMap<String, String>();
                map.put("bio", bio);
                map.put("major", major);
                HttpRequest request = HttpRequest.post(urls[0]).form(map);
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
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
            else {
                Log.d("MyApp", "Download failed");
            }
        }
    }

    /**
     * Allows you to change password and edit bio and major
     * Makes a http call to the database
     * @param view
     */
    public void changePassword(View view) {
        EditText password1 = (EditText) findViewById(R.id.change_password);
        EditText password2 = (EditText) findViewById(R.id.change_password2);
        EditText bioT = (EditText) findViewById(R.id.bio);
        EditText majorT = (EditText) findViewById(R.id.major);
        password = password1.getText().toString();
        bio = bioT.getText().toString();
        major = majorT.getText().toString();
        if (password.equals(password2.getText().toString())) {
            new ChangeProfileTask().execute("https://pandango.herokuapp.com/editProfile/" + username + "/" + password);
        } else {

        }
        /*profileChange login = profileChange.newInstance(R.string.changepassword, username);
        login.show(getFragmentManager(), "dialog");*/
    }
}
