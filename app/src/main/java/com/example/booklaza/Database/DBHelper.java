package com.example.booklaza.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.booklaza.models.BookInfo;
import com.example.booklaza.models.User;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    // DB name,  version
    private static final String DB_NAME = "usersDB";
    private static final int DB_VERSION = 1;

    // Tables names
    private static final String TABLE_USERS = "users";
    private static final String TABLE_FAV = "favourites";

    // column names for users table
    private static final String EMAIL = "email";
    private static final String F_NAME = "fname";
    private static final String L_NAME = "lname";
    private static final String PHONE = "phone";
    private static final String PASS = "pass";

    // column names for favourites table
    private static final String ID = "id";
    private static final String U_EMAIL = "email";
    private static final String TITLE = "title";
    private static final String AUTHORS = "authors";
    private static final String DESCRIPTION = "description";
    private static final String CATEGORIES = "categories";
    private static final String PDATE = "pdate";
    private static final String INFO = "info";
    private static final String PREVIEW = "preview";
    private static final String THUMBNAIL = "thumbnail";

    // create table query
    private static final String CREATE_TABLE_USER = "CREATE TABLE "
            + TABLE_USERS + "(" + EMAIL + " TEXT PRIMARY KEY, "
            + F_NAME + " TEXT," + L_NAME + " TEXT," + PHONE + " TEXT," + PASS
            + " TEXT" + ")";

    private static final String CREATE_TABLE_FAVOURITES = "CREATE TABLE "
            + TABLE_FAV + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            U_EMAIL + " TEXT," + TITLE + " TEXT," + AUTHORS + " TEXT," +
            DESCRIPTION + " TEXT," + CATEGORIES + " TEXT," + PDATE + " TEXT," +
            INFO + " TEXT," + PREVIEW + " TEXT," + THUMBNAIL + " TEXT" + ")";

    public DBHelper(Context context) {
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // CREATING REQUIRED TABLES
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_FAVOURITES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // upgrade tables; drop older tables
        db.execSQL(" DROP TABLE IF EXISTS "+TABLE_USERS);
        db.execSQL(" DROP TABLE IF EXISTS "+TABLE_FAV);

        // create new tables
        onCreate(db);
    }

    // creating new user - add data to users table
    public long addUSer(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EMAIL,user.getEmail());
        values.put(F_NAME,user.getfName());
        values.put(L_NAME,user.getlName());
        values.put(PHONE,user.getpNumber());
        values.put(PASS, user.getPass());

        // insert into new row
        long success = db.insert(TABLE_USERS,null,values);

        return success;
    }

    // check user details
    public Boolean getUser(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Boolean flag = false;

        Cursor cursor = db.rawQuery(
                "select * from "+TABLE_USERS +" where "+ EMAIL+ " = ? "+" and "+ PASS+" = ? ", new String[]{email, password});
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() == 1){
            flag=true;
        }
        else
        {
            flag=false;
        }

        return flag;

    }

    // add data to favourites
    public long addFavourites(String email,String title, String author, String desc, String categories, String pDate, String info, String preview, String thumbnail){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EMAIL,email);
        values.put(TITLE,title);
        values.put(AUTHORS,author);
        values.put(DESCRIPTION,desc);
        values.put(CATEGORIES,categories);
        values.put(PDATE,pDate);
        values.put(INFO,info);
        values.put(PREVIEW,preview);
        values.put(THUMBNAIL,thumbnail);

        // insert into new row
        long success = db.insert(TABLE_FAV,null,values);

        return success;
    }

    // Get favourites data
    public ArrayList<BookInfo> getFavData(String email){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(
                "select * from "+TABLE_FAV +" where "+ EMAIL+ " = ? ", new String[]{email});

        ArrayList<BookInfo> bookInfoArrayList = new ArrayList<>();
        // Move to first row
        if (c.moveToFirst()){
            do {
                BookInfo bookInfo = new BookInfo();
                bookInfo.setmTitle(c.getString(c.getColumnIndex(TITLE)));
                String authors = c.getString(c.getColumnIndex(AUTHORS));
                ArrayList<String> authorsList = new ArrayList<>();
                authorsList.add(authors);
                bookInfo.setmAuthors(authorsList);
                bookInfo.setmDescription(c.getString(c.getColumnIndex(DESCRIPTION)));
                bookInfo.setmCategories(c.getString(c.getColumnIndex(CATEGORIES)));
                bookInfo.setmPublishedDate(c.getString(c.getColumnIndex(PDATE)));
                bookInfo.setmInfo(c.getString(c.getColumnIndex(INFO)));
                bookInfo.setmPreview(c.getString(c.getColumnIndex(PREVIEW)));
                bookInfo.setmThumbnail(c.getString(c.getColumnIndex(THUMBNAIL)));
                bookInfoArrayList.add(bookInfo);

            }while (c.moveToNext());
        }


                return bookInfoArrayList;

    }

    // check existing data in favourites
    public Boolean checkFavData(String title){
        Boolean flag = false;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(
                "select * from "+TABLE_FAV +" where "+ TITLE+ " = ? ", new String[]{title});
        // check if present or not
        if (c.moveToFirst()){
            flag = true;
        }
        return flag;
    }

}
