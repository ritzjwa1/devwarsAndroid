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
import android.widget.RatingBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RateMovie extends AppCompatActivity {

    private RatingBar rB;
    TextView text;
    String username;
    String moviename;
    float ratingM;
    int numOfRates;
    double newRating;
    String major;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_movie);
        Intent intent = getIntent();
        Movie movie = (Movie) intent.getSerializableExtra("MOVIE");
        username = intent.getStringExtra("USER_NAME");
        major = intent.getStringExtra("MAJOR");
        new DispCurrentRating().execute("https://pandango.herokuapp.com/getCurrRating");

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

            }
        });
    }

    private class DispCurrentRating extends AsyncTask<String, Long, String> {
        protected String doInBackground(String... urls) {
            try {
                Map<String, String> keyValuePairs = new HashMap<String, String>();
                //keyValuePairs.put("id", Integer.toString(globUser.getNumOfUsers()));
                keyValuePairs.put("moviename", moviename);
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
                try {
                    if (file.equals("No data")) {
                        TextView view = (TextView) findViewById(R.id.disp_average_rating);
                        view.setText("Not Rated Yet...");
                    } else {
                        JSONArray arr = new JSONArray(file);
                        JSONObject obj = arr.getJSONObject(0);
                        double rating = obj.getDouble("rating");
                        TextView view = (TextView) findViewById(R.id.disp_average_rating);
                        view.setText("Rating: " + Double.toString(rating));
                    }
                } catch (JSONException j) {

                }
            }
            else {
                Log.d("MyApp", "Download failed");
            }
        }
    }

    private class GetCurrentRating extends AsyncTask<String, Long, String> {
        protected String doInBackground(String... urls) {
            try {
                Map<String, String> keyValuePairs = new HashMap<String, String>();
                //keyValuePairs.put("id", Integer.toString(globUser.getNumOfUsers()));
                keyValuePairs.put("moviename", moviename);
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
                try {
                    if (file.equals("No data")) {
                        newRating = ratingM;
                        numOfRates = 1;
                    } else {
                        JSONArray arr = new JSONArray(file);
                        JSONObject obj = arr.getJSONObject(0);
                        double rating = obj.getDouble("rating");
                        numOfRates = obj.getInt("num_of_ratings");
                        double numSum = numOfRates * rating;
                        newRating = (numSum + ratingM) / (numOfRates + 1);
                        numOfRates = numOfRates + 1;
                        Log.d("RATING", Double.toString(newRating));
                    }
                    new UpdateMovieAverage().execute("https://pandango.herokuapp.com/updateAverage");
                } catch (JSONException j) {

                }
            }
            else {
                Log.d("MyApp", "Download failed");
            }
        }
    }

    private class UpdateMovieAverage extends AsyncTask<String, Long, String> {
        protected String doInBackground(String... urls) {
            try {
                Map<String, String> keyValuePairs = new HashMap<String, String>();
                //keyValuePairs.put("id", Integer.toString(globUser.getNumOfUsers()));
                keyValuePairs.put("moviename", moviename);
                keyValuePairs.put("rating", Double.toString(newRating));
                keyValuePairs.put("numOfRates", Integer.toString(numOfRates));
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
                intent.putExtra("MAJOR", major);
                startActivity(intent);

            }
            else {
                Log.d("MyApp", "Download failed");
            }
        }
    }

    private class AddRating extends AsyncTask<String, Long, String> {
        protected String doInBackground(String... urls) {
            try {
                Map<String, String> keyValuePairs = new HashMap<String, String>();
                //keyValuePairs.put("id", Integer.toString(globUser.getNumOfUsers()));
                keyValuePairs.put("username", username);
                keyValuePairs.put("moviename", moviename);
                keyValuePairs.put("rating", Float.toString(ratingM));
                keyValuePairs.put("major", major);
                Log.d("MAJOR", major);
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
                /*Log.d("RESULT", file);
                Intent intent = new Intent(RateMovie.this, Home.class);
                intent.putExtra("USER_NAME", username);
                startActivity(intent);*/
                new GetCurrentRating().execute("https://pandango.herokuapp.com/getCurrRating");
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

    public void recMov(View view) {

    }

    private class PostNot extends AsyncTask<String, Long, String> {
        protected String doInBackground(String... urls) {
            try {
                Map<String, String> keyValuePairs = new HashMap<String, String>();
                //keyValuePairs.put("id", Integer.toString(globUser.getNumOfUsers()));
                keyValuePairs.put("username", username);
                keyValuePairs.put("moviename", moviename);
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
                /*Log.d("RESULT", file);
                Intent intent = new Intent(RateMovie.this, Home.class);
                intent.putExtra("USER_NAME", username);
                startActivity(intent);*/
                new GetCurrentRating().execute("https://pandango.herokuapp.com/getCurrRating");
            }
            else {
                Log.d("MyApp", "Download failed");
            }
        }
    }
}
