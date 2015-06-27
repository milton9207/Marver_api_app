package com.example.myapplication2.app;

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created by Milton on 5/13/2015.
 */
public class MarvelItem {

    private String mTitle = new String();
    private String mDescription = new String();
    private Bitmap image;
//    private String Url = new String();

    public MarvelItem(String mTitle, String mDescription, Bitmap image)
    {
        this.mTitle = mTitle;
        this.mDescription = mDescription;
//        this.Url = Url;
        this.image = image;

    }

    public String getmDescription() {
        return mDescription;
    }

    public String getmTitle() {
        return mTitle;
    }

    public Bitmap getImage() {
        return image;
    }
}
