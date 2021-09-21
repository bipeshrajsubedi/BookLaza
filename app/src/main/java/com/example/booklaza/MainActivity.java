package com.example.booklaza;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.booklaza.models.BookInfo;
import com.example.booklaza.recycleradapters.BrowseBooksRecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // creating variables for request queue
    // array list, progressbar,and recycler view.
    public  RequestQueue mRequestQueue;
    private ArrayList<BookInfo> bookInfoList;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Home");

        // view initialization
        recyclerView = findViewById(R.id.browsebooks_rv);
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        // fetch books details
        if (Config.checkNetworkStatus(MainActivity.this)) {
            fetchBookData();
        }
        else {
            Toast.makeText(this, "No internet connection. Please connect to the internet.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate menu
        getMenuInflater().inflate(R.menu.menu_item,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_fav){
            startActivity(new Intent(MainActivity.this,FavouritesActivity.class));
        }
        if (id == R.id.menu_search){
            startActivity(new Intent(MainActivity.this,SearchActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchBookData() {

        // new arraylist
        bookInfoList = new ArrayList<>();
        // initialize request queue
        mRequestQueue = Volley.newRequestQueue(MainActivity.this);
        // clear cache for data update
        mRequestQueue.getCache().clear();
        // URL for getting data
        String url = Config.API_BOOKS;
        // create a new requeest queue
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        // make json request and fetch data
        JsonObjectRequest bookRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray itemsArray = response.getJSONArray("items");
                    JSONArray authorsArray = null;
                    String categories="";
                    for (int i = 0; i < itemsArray.length(); i++) {

                        JSONObject itemsObj = itemsArray.getJSONObject(i);
                        JSONObject volumeObj = itemsObj.getJSONObject("volumeInfo");
                        String title = volumeObj.optString("title");
                        JSONArray tArray = volumeObj.optJSONArray("authors");
                        if (tArray != null) authorsArray = volumeObj.getJSONArray("authors");
                        String publishedDate = volumeObj.optString("publishedDate");
                        String description = volumeObj.optString("description");
                        JSONObject imageLinks = volumeObj.optJSONObject("imageLinks");
                        String thumbnail = imageLinks.getString("thumbnail");
                        String previewLink = volumeObj.optString("previewLink");
                        String infoLink = volumeObj.optString("infoLink");
                        JSONArray checkcategories = volumeObj.optJSONArray("categories");
                        if (checkcategories != null) categories = volumeObj.getJSONArray("categories").getString(0);
                        ArrayList<String> authorsArrayList = new ArrayList<>();
                        if (authorsArray.length() != 0) {
                            authorsArrayList.add(authorsArray.optString(0));
                        }
                        else {
                            authorsArrayList.add("N/A");
                        }
                        // saving data in modal class
                        BookInfo bookInfo = new BookInfo(title,authorsArrayList,description,categories,publishedDate,infoLink,previewLink,thumbnail);
                        bookInfoList.add(bookInfo);

                        // pass arraylist to adapter
                        BrowseBooksRecyclerViewAdapter browseBooksRecyclerViewAdapter = new BrowseBooksRecyclerViewAdapter(bookInfoList,MainActivity.this);
                        progressBar.setVisibility(View.GONE);
                        // Add layout manageer for recycler view
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this,2);
                        recyclerView.setLayoutManager(gridLayoutManager);
                        recyclerView.setAdapter(browseBooksRecyclerViewAdapter);


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "No Data Found" + e, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });
        // adding json obj request in request queue
        requestQueue.add(bookRequest);
    }
}