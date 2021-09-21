package com.example.booklaza;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.booklaza.recycleradapters.LinearRecyclerAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    // creating variables for request queue
    // array list, progressbar,and recycler view.
    public RequestQueue mRequestQueue;
    private ArrayList<BookInfo> bookInfoList;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private EditText et_search;
    private Button btn_search;
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        et_search = findViewById(R.id.et_search);
        btn_search = findViewById(R.id.btb_search);
        recyclerView = findViewById(R.id.rv_search);

        // action bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Search");

        // onclick search button
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar = findViewById(R.id.progress_search);
                String search_key = et_search.getText().toString();
                String url = "https://www.googleapis.com/books/v1/volumes?q="+search_key+"&key=AIzaSyBPQzP_WaKpdMMs691mrT0rYFWZ0VqEZA4";
                if (TextUtils.isEmpty(search_key)){
                    Toast.makeText(SearchActivity.this, "Please enter some keywords.", Toast.LENGTH_SHORT).show();
                }
                else {
                    // search books
                    if (Config.checkNetworkStatus(SearchActivity.this)) {
                        progressBar.setVisibility(View.VISIBLE);
                        search_books(url);
                    }else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(SearchActivity.this, "No internet connection. Please connect to the internet.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private void search_books(String s_url) {
        // new arraylist
        bookInfoList = new ArrayList<>();
        // initialize request queue
        mRequestQueue = Volley.newRequestQueue(SearchActivity.this);
        // clear cache for data update
        mRequestQueue.getCache().clear();
        // URL for getting data
        String url = s_url;
        // create a new requeest queue
        RequestQueue requestQueue = Volley.newRequestQueue(SearchActivity.this);

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
//                        for (int j = 0; j < authorsArray.length(); j++) {
//                            authorsArrayList.add(authorsArray.optString(i));
//                        }
                        }
                        else {
                            authorsArrayList.add("N/A");
                        }
                        // saving data in modal class
                        BookInfo bookInfo = new BookInfo(title,authorsArrayList,description,categories,publishedDate,infoLink,previewLink,thumbnail);
                        bookInfoList.add(bookInfo);

                        // pass arraylist to adapter
                        LinearRecyclerAdapter linearRecyclerAdapter = new LinearRecyclerAdapter(bookInfoList,SearchActivity.this);
                        progressBar.setVisibility(View.GONE);
                        // Add layout manageer for recycler view
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchActivity.this);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setAdapter(linearRecyclerAdapter);


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(SearchActivity.this, "No Data Found" + e, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SearchActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });
        // adding json obj request in request queue
        requestQueue.add(bookRequest);
    }
}