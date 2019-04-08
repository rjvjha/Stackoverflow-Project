package com.example.rajeevjha.stackoverflow.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

// Model class used for Retrofit Gson and Room
@Entity
public class Question {

    @ColumnInfo(name = "sort_category")
    public String sortCategory;
    @PrimaryKey(autoGenerate = true)
    private int _ID;
    @ColumnInfo(name = "link")
    @SerializedName("link")
    private String link;
    @ColumnInfo(name = "title")
    @SerializedName("title")
    private String title;
    @ColumnInfo(name = "tag")
    private String tag;


    public Question(String link, String title) {
        this.link = link;
        this.title = title;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int get_ID() {
        return _ID;
    }

    public void set_ID(int _ID) {
        this._ID = _ID;
    }

    public String getSortCategory() {
        return sortCategory;
    }

    public void setSortCategory(String sortCategory) {
        this.sortCategory = sortCategory;
    }
}
