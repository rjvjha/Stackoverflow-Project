package com.example.rajeevjha.stackoverflow.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TagList {

    @SerializedName("items")
    private List<Tag> tagList;

    public TagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    public List<Tag> getTagList() {
        return tagList;
    }
}
