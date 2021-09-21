package com.example.booklaza;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.booklaza.Database.DBHelper;
import com.example.booklaza.models.BookInfo;
import com.example.booklaza.recycleradapters.LinearRecyclerAdapter;

import java.util.ArrayList;

public class FavouritesActivity extends AppCompatActivity {

    DBHelper helper;
    RecyclerView recyclerView;
    private ArrayList<BookInfo> bookInfoList;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        // action bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Favourites");

        // Initializing views
        recyclerView = findViewById(R.id.rv_favourites);

        // populating data
        populateData();
    }

    @Override
    protected void onResume() {
        populateData();
        super.onResume();
    }

    private void populateData() {
        helper = new DBHelper(FavouritesActivity.this);
        bookInfoList = new ArrayList<>();
        bookInfoList = helper.getFavData(Config.USER_ID);

        if (bookInfoList != null){
            // pass arraylist to adapter
            LinearRecyclerAdapter linearRecyclerAdapter = new LinearRecyclerAdapter(bookInfoList,FavouritesActivity.this);

            // Add layout manageer for recycler view
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FavouritesActivity.this);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(linearRecyclerAdapter);
        }
        else {
            Toast.makeText(FavouritesActivity.this, "No items in favourites.", Toast.LENGTH_SHORT).show();
        }
    }
}
