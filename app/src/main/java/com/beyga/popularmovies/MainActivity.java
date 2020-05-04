package com.beyga.popularmovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
    private GridViewAdapter adapter;
    private String currentCategory = Constants.POPULAR_MOVIES_URL;
    private int currentPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updateTitle();
        GridView gridView = findViewById(R.id.grid_view);
        adapter = new GridViewAdapter(this, moviesList);
        gridView.setAdapter(adapter);
        downloadData(currentCategory,currentPage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.popular_movies_menu_item:
                currentCategory = Constants.POPULAR_MOVIES_URL;
                currentPage = 1;
                downloadData(currentCategory,currentPage);
                updateTitle();
                break;
            case R.id.top_rated_menu_item:
                currentCategory = Constants.TOP_RATED_MOVIES_URL;
                currentPage = 1;
                downloadData(currentCategory,currentPage);
                updateTitle();
                break;
            case R.id.next_page:
                downloadData(currentCategory,++currentPage);
                updateTitle();

                break;
            case R.id.previous_page:
                if(currentPage>1)
                    currentPage--;
                updateTitle();
                downloadData(currentCategory,currentPage);
                break;
        }

        return true;
    }

    private void downloadData(final String dataURL, final int page){
        new Thread(new Runnable() {
            @Override
            public void run() {
                moviesList.clear();
                String url = dataURL + "&page=" + page;
                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url , new Response.Listener<String>() {
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
                        error.printStackTrace();
                    }
                });
                requestQueue.add(stringRequest);
            }
        }).start();

        adapter.notifyDataSetChanged();
    }

    private void updateTitle(){
        String title;
        if (currentCategory.equals(Constants.POPULAR_MOVIES_URL)) {
            title = "Popular movies";
        }else{
            title = "Top rated";
        }

        setTitle(title + " - " + currentPage);
    }
}
