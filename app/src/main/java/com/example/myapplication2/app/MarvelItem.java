package com.example.myapplication2.app;

/**
 * Created by Milton on 5/13/2015.
 */
public class MarvelItem {

    private String mTitle = new String();
    private String mDescription = new String();

    public MarvelItem(String mTitle, String mDescription)
    {
        this.mTitle = mTitle;
        this.mDescription = mDescription;

    }

    public String getmDescription() {
        return mDescription;
    }

    public String getmTitle() {
        return mTitle;
    }
}
