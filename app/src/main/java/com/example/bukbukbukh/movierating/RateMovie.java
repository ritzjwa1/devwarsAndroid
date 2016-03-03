package com.example.bukbukbukh.movierating;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class RateMovie extends AppCompatActivity {

    private RatingBar rB;
    TextView text;
    String username;
    String moviename;
    float ratingM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_movie);
        Intent intent = getIntent();
        Movie movie = (Movie) intent.getSerializableExtra("MOVIE");
        username = intent.getStringExtra("USER_NAME");
        TextView movieName = (TextView) findViewById(R.id.movie_name);
        movieName.setText(movie.getTitle());
        moviename = movie.getTitle();
        addListenerOnRatingBar();
    }

    public void addListenerOnRatingBar() {
        rB = (RatingBar) findViewById(R.id.ratingBar);
        text = (TextView) findViewById(R.id.movie_name);

        //if rating value is changed,
        //display the current rating value in the result (textview) automatically
        rB.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                ratingM = rating;
                new AddRating().execute("https://pandango.herokuapp.com/addRating");
                /*Intent intent = new Intent(RateMovie.this, Home.class);
                intent.putExtra("USER_NAME", username);
                startActivity(intent);*/

            }
        });
    }

    private class AddRating extends AsyncTask<String, Long, String> {
        protected String doInBackground(String... urls) {
            try {
                Map<String, String> keyValuePairs = new HashMap<String, String>();
                //keyValuePairs.put("id", Integer.toString(globUser.getNumOfUsers()));
                keyValuePairs.put("username", username);
                keyValuePairs.put("moviename", moviename);
                keyValuePairs.put("rating", Float.toString(ratingM));
                HttpRequest request = HttpRequest.post(urls[0]).form(keyValuePairs);
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
                Log.d("RESULT", file);
                Intent intent = new Intent(RateMovie.this, Home.class);
                intent.putExtra("USER_NAME", username);
                startActivity(intent);
            }
            else {
                Log.d("MyApp", "Download failed");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rate_movie, menu);
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
}
