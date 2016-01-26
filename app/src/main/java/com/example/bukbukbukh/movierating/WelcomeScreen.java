package com.example.bukbukbukh.movierating;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class WelcomeScreen extends AppCompatActivity {

    MainDatabase mDB;

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

    public void goRegisterScreen(View view) {
        Intent intent = new Intent(this, register_screen.class);
        startActivity(intent);
    }
}
