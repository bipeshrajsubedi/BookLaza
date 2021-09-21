package com.example.booklaza.models;

import java.util.ArrayList;

public class BookInfo {
    // Variables for book informatioin
    private String mTitle;
    private ArrayList<String> mAuthors;
    private String mDescription;
    private String mCategories;
    private String mPublishedDate;
    private String mInfo;
    private String mPreview;
    private String mThumbnail;

    public BookInfo(){

    }

    public BookInfo(String mTitle, ArrayList<String> mAuthors, String mDescription, String mCategories, String mPublishedDate, String mInfo, String mPreview, String mThumbnail) {
        this.mTitle = mTitle;
        this.mAuthors = mAuthors;
        this.mDescription = mDescription;
        this.mCategories = mCategories;
        this.mPublishedDate = mPublishedDate;
        this.mInfo = mInfo;
        this.mPreview = mPreview;
        this.mThumbnail = mThumbnail;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public ArrayList<String> getmAuthors() {
        return mAuthors;
    }

    public void setmAuthors(ArrayList<String> mAuthors) {
        this.mAuthors = mAuthors;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmCategories() {
        return mCategories;
    }

    public void setmCategories(String mCategories) {
        this.mCategories = mCategories;
    }

    public String getmPublishedDate() {
        return mPublishedDate;
    }

    public void setmPublishedDate(String mPublishedDate) {
        this.mPublishedDate = mPublishedDate;
    }

    public String getmInfo() {
        return mInfo;
    }

    public void setmInfo(String mInfo) {
        this.mInfo = mInfo;
    }

    public String getmPreview() {
        return mPreview;
    }

    public void setmPreview(String mPreview) {
        this.mPreview = mPreview;
    }

    public String getmThumbnail() {
        return mThumbnail;
    }

    public void setmThumbnail(String mThumbnail) {
        this.mThumbnail = mThumbnail;
    }
}
