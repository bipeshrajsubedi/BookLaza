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

import com.example.booklaza.BookdetailsActivity;
import com.example.booklaza.R;
import com.example.booklaza.SearchActivity;
import com.example.booklaza.WebviewActivity;
import com.example.booklaza.models.BookInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LinearRecyclerAdapter extends RecyclerView.Adapter<LinearRecyclerAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<BookInfo> mBookInfoList;

    public LinearRecyclerAdapter(ArrayList<BookInfo> mBookInfoList, Context mContext){
        this.mBookInfoList = mBookInfoList;
        this.mContext = mContext;
    }
    @NonNull
    @Override
    public LinearRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourites_each_item,parent,false);
        return new LinearRecyclerAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LinearRecyclerAdapter.MyViewHolder holder, int position) {
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
        String authStr = "";
        for(int i=0; i<bookInfo.getmAuthors().size();i++){
            authStr+= bookInfo.getmAuthors().get(i)+" ";
        }
        holder.tv_authors.setText(authStr);
        holder.tv_cat.setText(bookInfo.getmCategories());
        holder.tv_pdate.setText(bookInfo.getmPublishedDate());
        if (mContext.getClass() == SearchActivity.class){
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
        }else {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // send data to books details page
                    Intent intent = new Intent(mContext, WebviewActivity.class);
                    intent.putExtra("title", bookInfo.getmTitle());
                    intent.putExtra("url", bookInfo.getmPreview().replace("http://", "https://"));

                    mContext.startActivity(intent);
                    System.out.println(mContext);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mBookInfoList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView iv_thumbnail;
        TextView tv_title,tv_authors,tv_cat,tv_pdate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_thumbnail = itemView.findViewById(R.id.iv_fav);
            tv_title = itemView.findViewById(R.id.tv_title_fav);
            tv_authors = itemView.findViewById(R.id.tv_fav_authors);
            tv_cat = itemView.findViewById(R.id.tv_cat_fav);
            tv_pdate = itemView.findViewById(R.id.tv_fav_pdate);
        }
    }
}
