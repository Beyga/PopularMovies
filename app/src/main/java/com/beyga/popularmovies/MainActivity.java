package com.beyga.popularmovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private List<Movie> moviesList = new ArrayList<>();
    private GridView gridView;
    private GridViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.grid_view);
        adapter = new GridViewAdapter(this, moviesList);
        gridView.setAdapter(adapter);
        downloadData(Constants.POPULAR_MOVIES_URL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.popular_movies_menu_item){
            downloadData(Constants.POPULAR_MOVIES_URL);
            setTitle("Popular Movies");
        }else{
            downloadData(Constants.TOP_RATED_MOVIES_URL);
            setTitle("Top rated");
        }
        return true;
    }

    private void downloadData(final String dataURL){
        new Thread(new Runnable() {
            @Override
            public void run() {
                moviesList.clear();
                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, dataURL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("results");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject movieJsonObject = jsonArray.getJSONObject(i);
                                ObjectMapper mapper = new ObjectMapper();
                                Movie movie = mapper.readValue(movieJsonObject.toString(), Movie.class);
                                moviesList.add(movie);
                            }
                        } catch (JSONException | IOException e) {
                            Toast.makeText(MainActivity.this, "catch error: " + e.toString(), Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                requestQueue.add(stringRequest);
            }
        }).start();

        adapter.notifyDataSetChanged();
    }
}
