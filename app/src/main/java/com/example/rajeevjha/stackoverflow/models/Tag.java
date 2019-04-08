package com.example.rajeevjha.stackoverflow.models;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


// Custom Tag Class
public class Tag {


    // contains user selected tag
    public static ArrayList<String> userSelectedTags = new ArrayList<>(4);

    @SerializedName("name")
    private String name;

    public Tag(String name) {
        this.name = name;
    }

    public static void removeTag(String name) {
        userSelectedTags.remove(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addTag() {
        if (userSelectedTags.size() <= 3) {
            userSelectedTags.add(this.name);
        }
    }
}
