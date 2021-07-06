package com.example.technewsportal;

import android.graphics.drawable.Drawable;

import java.util.Comparator;
import java.util.Date;

public class NewsItem {
    private String category;
    private Drawable photo;
    private String title;
    private String website;
    private Date date;

    public String getCategory() {
        return category;
    }

    public Drawable getPhoto() {
        return photo;
    }

    public String getTitle() {
        return title;
    }

    public String getWebsite() {
        return website;
    }

    public Date getDate() {
        return date;
    }

    public NewsItem(String category, Drawable photo, String title, String website, Date date) {
        this.category = category;
        this.photo = photo;
        this.title = title;
        this.website = website;
        this.date = date;
    }
}
