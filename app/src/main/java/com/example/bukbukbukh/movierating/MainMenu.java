package com.example.bukbukbukh.movierating;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainMenu extends AppCompatActivity {

    String username;
    String response;
    private RequestQueue queue;
    private String[] arrSTR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        /**
         * This code in the oncreate method stores the username to reference the database for any further information
         */
        Intent intent = getIntent();
        username = intent.getStringExtra("USERNAME");
        TextView dispUserName = (TextView) findViewById(R.id.disp_username);
        dispUserName.setText("Welcome " + username);
        Log.d("anyString", username);
        queue = Volley.newRequestQueue(this);

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
        Intent intent = new Intent(this, ProfilePage.class);
        intent.putExtra("USER_NAME", username);
        startActivity(intent);
    }

    public void searchMoviesPage(View view) {
        EditText searchStr = (EditText) findViewById(R.id.searchMovie);
        if (searchStr.getText().toString().equals("")) {
            LoginStatus login = LoginStatus.newInstance(R.string.no_movie_String);
            login.show(getFragmentManager(), "dialog");
        } else {
            String sStr = searchStr.getText().toString();
            String url = "http://api.rottentomatoes.com/api/public/v1.0/movies.json?apikey=yedukp76ffytfuy24zsqk7f5&q=" + sStr + "&page_limit=10";

            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject resp) {

                            //handle a valid response coming back.  Getting this string mainly for debug
                            response = resp.toString();
                            JSONArray arr = null;
                            try {
                                arr = resp.getJSONArray("movies");
                                response = arr.toString();
                                Log.d("MAIN", response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            assert arr != null;
                            ListView lv = (ListView) findViewById(R.id.movieSearchResult);
                            ArrayList<Movie> mainMovieList = new ArrayList<Movie>();
                            ArrayList<String> nameMovies = new ArrayList<String>();
                            JSONObject obj1 = null;
                            for (int i = 0; i < arr.length(); i++) {
                                try {
                                    obj1 = arr.getJSONObject(i);
                                    Movie movie = new Movie(obj1.getString("title"), obj1.getInt("year"));
                                    movie.setRuntime(obj1.getInt("runtime"));
                                    movie.setSynopsis(obj1.getString("synopsis"));
                                    mainMovieList.add(movie);
                                    nameMovies.add(movie.getTitle());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            lv.setAdapter(new ArrayAdapter<String>(MainMenu.this,
                                    R.layout.list_item_movies, R.id.movieName, nameMovies));

                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            response = "JSon Request Failed!!";
                            Log.d("ERROR", response);
                        }
                    });
            //this actually queues up the async response with Volley
            queue.add(jsObjRequest);
        }

    }

    public void searchNewRel(View view) {

        String url = "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/in_theaters.json?apikey=yedukp76ffytfuy24zsqk7f5&page_limit=16";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject resp) {

                        //handle a valid response coming back.  Getting this string mainly for debug
                        response = resp.toString();
                        JSONArray arr = null;
                        try {
                            arr = resp.getJSONArray("movies");
                            response = arr.toString();
                            Log.d("MAIN", response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        assert arr != null;
                        ListView lv = (ListView) findViewById(R.id.movieSearchResult);
                        ArrayList<Movie> mainMovieList = new ArrayList<Movie>();
                        ArrayList<String> nameMovies = new ArrayList<String>();
                        JSONObject obj1 = null;
                        for (int i = 0; i < arr.length(); i++) {
                            try {
                                obj1 = arr.getJSONObject(i);
                                Movie movie = new Movie(obj1.getString("title"), obj1.getInt("year"));
                                movie.setRuntime(obj1.getInt("runtime"));
                                movie.setSynopsis(obj1.getString("synopsis"));
                                mainMovieList.add(movie);
                                nameMovies.add(movie.getTitle());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        lv.setAdapter(new ArrayAdapter<String>(MainMenu.this,
                                R.layout.list_item_movies, R.id.movieName, nameMovies));

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        response = "JSon Request Failed!!";
                        Log.d("ERROR", response);
                    }
                });
        //this actually queues up the async response with Volley
        queue.add(jsObjRequest);

    }

    public void searchNewDVD(View view) {

        String url = "http://api.rottentomatoes.com/api/public/v1.0/lists/dvds/new_releases.json?apikey=yedukp76ffytfuy24zsqk7f5&page_limit=16";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject resp) {

                        //handle a valid response coming back.  Getting this string mainly for debug
                        response = resp.toString();
                        JSONArray arr = null;
                        try {
                            arr = resp.getJSONArray("movies");
                            response = arr.toString();
                            Log.d("MAIN", response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        assert arr != null;
                        ListView lv = (ListView) findViewById(R.id.movieSearchResult);
                        ArrayList<Movie> mainMovieList = new ArrayList<Movie>();
                        ArrayList<String> nameMovies = new ArrayList<String>();
                        JSONObject obj1 = null;
                        for (int i = 0; i < arr.length(); i++) {
                            try {
                                obj1 = arr.getJSONObject(i);
                                Movie movie = new Movie(obj1.getString("title"), obj1.getInt("year"));
                                movie.setRuntime(obj1.getInt("runtime"));
                                movie.setSynopsis(obj1.getString("synopsis"));
                                mainMovieList.add(movie);
                                nameMovies.add(movie.getTitle());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        lv.setAdapter(new ArrayAdapter<String>(MainMenu.this,
                                R.layout.list_item_movies, R.id.movieName, nameMovies));

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        response = "JSon Request Failed!!";
                        Log.d("ERROR", response);
                    }
                });
        //this actually queues up the async response with Volley
        queue.add(jsObjRequest);

    }
}
