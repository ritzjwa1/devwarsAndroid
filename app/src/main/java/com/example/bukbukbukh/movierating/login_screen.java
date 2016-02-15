package com.example.bukbukbukh.movierating;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class login_screen extends AppCompatActivity {

    int loginAttempt;
    String username;
    String password;
    String loginAt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        loginAttempt = 3;
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
                if (loginAttempt > 0) {
                    if (!file.equals("0")) {
                        Intent intent = new Intent(login_screen.this, MainMenu.class);
                        intent.putExtra("USERNAME", file);
                        startActivity(intent);
                    } else {
                        LoginStatus login = LoginStatus.newInstance(R.string.loginFailure);
                        login.show(getFragmentManager(), "dialog");
                        loginAttempt--;
                    }
                }
            }
            else {
                Log.d("MyApp", "Download failed");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login_screen, menu);
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

    public void checkLoginIn(View view) {
        if (loginAttempt > 0) {
            EditText ed = (EditText) findViewById(R.id.user_name);
            EditText ed2 = (EditText) findViewById(R.id.password);
            username = ed.getText().toString();
            password = ed2.getText().toString();
            String url = "https://pandango.herokuapp.com/" + username + "/" + password;
            String response = new LoginTask().execute(url).toString();
        } else {
            LoginStatus login = LoginStatus.newInstance(R.string.StopLogin);
            login.show(getFragmentManager(), "dialog");
        }
    }

    public void cancelButton(View view) {
        Intent intent = new Intent(this, WelcomeScreen.class);
        startActivity(intent);
    }
}
