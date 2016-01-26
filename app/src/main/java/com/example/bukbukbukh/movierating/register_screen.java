package com.example.bukbukbukh.movierating;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class register_screen extends AppCompatActivity {

    MainDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);
        mDb = MainDatabase.getInstance(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register_screen, menu);
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

    public void registerUser(View view) {
        EditText ed1 = (EditText) findViewById(R.id.first_name);
        EditText ed2 = (EditText) findViewById(R.id.last_name);
        EditText ed3 = (EditText) findViewById(R.id.user_n);
        EditText ed4 = (EditText) findViewById(R.id.pass);
        EditText ed5 = (EditText) findViewById(R.id.conf_pass);
        if (ed5.getText().toString().equals(ed4.getText().toString())) {
            User newUser = new User(ed1.getText().toString(), ed2.getText().toString(), ed3.getText().toString(), ed4.getText().toString());
            boolean addUserTrue = mDb.addUser(newUser);
            if (!addUserTrue) {
                LoginStatus checkRegisterStatus = LoginStatus.newInstance(R.string.register_status2);
                checkRegisterStatus.show(getFragmentManager(), "dialog");
            } else {
                LoginStatus checkRegisterStatus = LoginStatus.newInstance(R.string.register_status3);
                checkRegisterStatus.show(getFragmentManager(), "dialog");
                ed1.setText("");
                ed2.setText("");
                ed3.setText("");
                ed4.setText("");
                ed5.setText("");
            }
        } else {
            LoginStatus checkRegisterStatus = LoginStatus.newInstance(R.string.register_status1);
            checkRegisterStatus.show(getFragmentManager(), "dialog");
        }

    }
}
