package com.example.bukbukbukh.movierating;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchPages extends AppCompatActivity implements AdapterView.OnItemClickListener{


    String response;
    private RequestQueue queue;
    private String[] arrSTR;
    String username;
    String major;
    ArrayList<Movie> mainMovieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_pages);
        Intent intent = getIntent();
        username = intent.getStringExtra("USER_NAME");
        major = intent.getStringExtra("MAJOR");
        queue = Volley.newRequestQueue(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_pages, menu);
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
                            ListView lv = (ListView) findViewById(R.id.list_movies);
                            mainMovieList = new ArrayList<Movie>();
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
                            lv.setAdapter(new ArrayAdapter<String>(SearchPages.this,
                                    R.layout.list_item_movies, R.id.movieName, nameMovies));
                            lv.setOnItemClickListener(SearchPages.this);

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
                        ListView lv = (ListView) findViewById(R.id.list_movies);
                        mainMovieList = new ArrayList<Movie>();
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
                        lv.setAdapter(new ArrayAdapter<String>(SearchPages.this,
                                R.layout.list_item_movies, R.id.movieName, nameMovies));
                        lv.setOnItemClickListener(SearchPages.this);

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
                        ListView lv = (ListView) findViewById(R.id.list_movies);
                        mainMovieList = new ArrayList<Movie>();
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
                        lv.setAdapter(new ArrayAdapter<String>(SearchPages.this,
                                R.layout.list_item_movies, R.id.movieName, nameMovies));
                        lv.setOnItemClickListener(SearchPages.this);

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

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        Intent intent = new Intent(this, RateMovie.class);
        String cursor = (String) parent.getItemAtPosition(position);
        Movie mainMovie = new Movie();
        for (int i = 0; i < mainMovieList.size(); i++) {
            if (cursor.equals(mainMovieList.get(i).getTitle())) {
                mainMovie = mainMovieList.get(i);
                break;
            }
        }
        Log.d("MAINMOVIE", mainMovie.getTitle());
        intent.putExtra("MOVIE", mainMovie);
        intent.putExtra("USER_NAME", username);
        intent.putExtra("MAJOR", major);
        startActivity(intent);
    }

    public void searchMajor(View view) {
        new SearchByMajor().execute("https://pandango.herokuapp.com/getMovieByMajor/" + major);

    }

    private class SearchByMajor extends AsyncTask<String, Long, String> {
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
                try {
                    JSONArray arr = new JSONArray(file);
                    ArrayList<String> list = new ArrayList<String>();
                    JSONObject obj = null;
                    mainMovieList = new ArrayList<Movie>();
                    for (int i = 0; i < arr.length(); i++) {
                        obj = arr.getJSONObject(i);
                        list.add(obj.getString("movie_name"));
                        Movie movie = new Movie(obj.getString("movie_name"), 0);
                        Log.d("MOVIE DETALS", movie.getTitle());
                        mainMovieList.add(movie);
                        //mainMovieList.add(movie);
                    }
                    ListView lv = (ListView) findViewById(R.id.list_movies);
                    lv.setAdapter(new ArrayAdapter<String>(SearchPages.this,
                            R.layout.list_item_movies, R.id.movieName, list));
                    lv.setOnItemClickListener(SearchPages.this);
                } catch(JSONException j) {

                }
            }
            else {
                Log.d("MyApp", "Download failed");
            }
        }
    }
}
