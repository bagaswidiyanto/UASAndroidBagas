package com.example.uasbagaswidiyanto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainAppActivity extends AppCompatActivity {
    DataAdapter adapter;
    List<Drink> movies;
    SwipeRefreshLayout srl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_app_activity);
        // Lookup the swipe container view
        srl = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                MainActivity.db.dataDao().deleteAll();
                load();

                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        // Stop animation (This will be after 1 seconds)
                        srl.setRefreshing(false);
                    }
                }, 1000); // Delay in millis
            }
        });
        // Configure the refreshing colors
        srl.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        showRecycleView();
    }

    private void showRecycleView() {
        RecyclerView view = (RecyclerView) findViewById(R.id.rv_drink);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this,2);
        view.setLayoutManager(mLayoutManager);

        movies = MainActivity.db.dataDao().getAll(); //Ambil semua data
        adapter = new DataAdapter(movies, this);
        view.setAdapter(adapter);
    }

    private void load() {
        // ambil data berupa json dari themealdb
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://www.thecocktaildb.com/api/json/v1/1/filter.php?c=Cocktail";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("cek json: ", response.toString());

                        String id, nama, image;

                        try {
                            JSONArray jsonArray = response.getJSONArray("drinks");
                            if (jsonArray.length() != 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject data = jsonArray.getJSONObject(i);

                                    nama = data.getString("strDrink").toString().trim();
                                    image = data.getString("strDrinkThumb").toString().trim();

                                    // masukkan melalui dao
                                    MainActivity.db.dataDao().insertAll(new Drink(nama, image));
                                }
                                showRecycleView();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error : ", error.toString());
                    }
                }
        );

        queue.add(jsonObjectRequest);
    }

}