package com.example.bootcampandroid;

import com.google.gson.annotations.SerializedName;

/* Missing data for fetching pict for each movie
*
* */


public class Movie {

    @SerializedName("release_date")
    private String releseDate;

    @SerializedName("id")
    private  int id;

    @SerializedName("title")
    private String name;

    @SerializedName("overview")
    private String details;

    @SerializedName("original_title")
    private String originalName;

    public Movie (String _name, int _id, String _details, String _originalName, String _releaseDate){

        this.name = _name;
        this.id = _id;
        this.details = _details;
        this.originalName = _originalName;
        this.releseDate = _releaseDate;
    }

    public String getOriginalName() {
        return originalName;
    }

    public String getReleseDate() {
        return releseDate;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }

    public void setReleseDate(String releseDate) {
        this.releseDate = releseDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }
}
