package com.example.booklaza.recycleradapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.booklaza.BookdetailsActivity;
import com.example.booklaza.R;
import com.example.booklaza.models.BookInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BrowseBooksRecyclerViewAdapter extends RecyclerView.Adapter<BrowseBooksRecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<BookInfo> mBookInfoList;

    public BrowseBooksRecyclerViewAdapter(ArrayList<BookInfo> mBookInfoList, Context mContext){
        this.mBookInfoList = mBookInfoList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public BrowseBooksRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.browsebook_each_item,parent,false);
        return new BrowseBooksRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BrowseBooksRecyclerViewAdapter.MyViewHolder holder, int position) {

        final BookInfo bookInfo = mBookInfoList.get(position);

        // populating image and title
        if (bookInfo.getmThumbnail() !=null && !bookInfo.getmThumbnail().isEmpty()){
            Picasso.get()
                    .load(bookInfo.getmThumbnail().replace("http://","https://"))
                    .placeholder(R.drawable.ic_book)
                    .error(R.drawable.ic_book)
                    .fit().noFade()
                    .into(holder.iv_thumbnail);
        }

        holder.tv_title.setText(bookInfo.getmTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // send data to books details page
                Intent intent = new Intent(mContext, BookdetailsActivity.class);
                intent.putExtra("title",bookInfo.getmTitle());
                intent.putExtra("authors",bookInfo.getmAuthors());
                intent.putExtra("description",bookInfo.getmDescription());
                intent.putExtra("categories",bookInfo.getmCategories());
                intent.putExtra("pDate",bookInfo.getmPublishedDate());
                intent.putExtra("info",bookInfo.getmInfo());
                intent.putExtra("preview",bookInfo.getmPreview());
                intent.putExtra("thumbnail",bookInfo.getmThumbnail());

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBookInfoList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView iv_thumbnail;
        TextView tv_title;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_thumbnail = itemView.findViewById(R.id.br_books_rvitem_iv);
            tv_title = itemView.findViewById(R.id.br_books_rvitem_tv);
        }
    }

    }
