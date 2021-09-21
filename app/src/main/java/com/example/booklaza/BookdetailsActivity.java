package com.example.booklaza;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.booklaza.Database.DBHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookdetailsActivity extends AppCompatActivity {

    private String title, desc,categories,publishDate, info, preview, thumbnail;
    private ArrayList<String> authors;
    ImageView iv_thumbnail;
    TextView tv_title, tv_authors, tv_desc, tv_cat, tv_pdate,tv_info,tv_preview;
    DBHelper helper;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate menu
        getMenuInflater().inflate(R.menu.menu_item,menu);
        MenuItem item = menu.findItem(R.id.menu_search);
        item.setVisible(false);
        return true;
    }
    // handle menu actions
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_fav){
            receiveData();
            helper = new DBHelper(this);

            if (helper.checkFavData(title)){
                Toast.makeText(this, "Already available on Favourites.", Toast.LENGTH_SHORT).show();
            }else {
                // add to favourites database
                String author = "";
                if (authors != null) {
                    for (int i = 0; i < authors.size(); i++) {
                        author += authors.get(i) + " ";
                    }
                } else {
                    author = "N/A";
                }

                long success = helper.addFavourites(Config.USER_ID, title, author, desc, categories, publishDate, info, preview, thumbnail);
                if (success == -1) {
                    Toast.makeText(this, "Failed to add to favourites.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Added to favourites.", Toast.LENGTH_SHORT).show();
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookdetails);
        // action bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initializn views
        iv_thumbnail = findViewById(R.id.iv_bookdetails);
        tv_title = findViewById(R.id.tv_title);
        tv_authors = findViewById(R.id.tv_authors);
        tv_desc = findViewById(R.id.tv_description);
        tv_cat = findViewById(R.id.tv_cat);
        tv_pdate = findViewById(R.id.tv_pdate);
        tv_info = findViewById(R.id.tv_info);
        tv_preview = findViewById(R.id.tv_preview);

            //receive data from previous activity
            receiveData();

            // set Title action bar
            setTitle(title);
            // setting data
            Picasso.get().load(thumbnail.replace("http://","https://"))
                    .into(iv_thumbnail);
            tv_title.setText(title);

            String authStr = "";
            for(int i=0; i<authors.size();i++){
                authStr+= authors.get(i)+" ";
            }
            tv_authors.setText(authStr);
            tv_desc.setText(desc);
            tv_cat.setText(categories);
            tv_pdate.setText("Published on: "+publishDate);

            tv_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(BookdetailsActivity.this,WebviewActivity.class);
                    intent.putExtra("url",info.replace("http://","https://"));
                    intent.putExtra("title",title);
                    startActivity(intent);
                    //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(info)));
                }
            });

            //read online
            tv_preview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(BookdetailsActivity.this,WebviewActivity.class);
                    intent.putExtra("url",preview.replace("http://","https://"));
                    intent.putExtra("title",title);
                    startActivity(intent);
                    //startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(preview)));
                }
            });
        }

    private void receiveData() {
        // Receive intent data
        Bundle data = getIntent().getExtras();

        if (data != null){
            title = data.getString("title");
            authors = data.getStringArrayList("authors");
            desc = data.getString("description");
            categories = data.getString("categories");
            publishDate = data.getString("pDate");
            info = data.getString("info");
            preview = data.getString("preview");
            thumbnail = data.getString("thumbnail");
    }
        //System.out.println(title+" "+authors+" "+desc+" "+categories+" "+publishDate+" " +" "+info+" "+preview+" "+thumbnail);

    }
}