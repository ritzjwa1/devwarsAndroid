package com.example.bukbukbukh.movierating;

/**
 * Created by bukbukbukh on 2/16/16.
 */
public class Movie {
    private String title;
    private int year;
    private int runtime;
    private String synopsis;

    public Movie(String title, int year) {
        this.title = title;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public int getRuntime() {
        return runtime;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setTitle(String str) {
        title = str;
    }

    public void setYear(int y) {
        year = y;
    }

    public void setRuntime(int y) {
        runtime = y;
    }

    public void setSynopsis(String str) {
        synopsis = str;
    }
}
