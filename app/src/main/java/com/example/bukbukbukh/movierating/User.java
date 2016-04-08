package com.example.bukbukbukh.movierating;

/**
 * Created by bukbukbukh on 1/24/16.
 */
public class User {
    /**
     * Instance variables for class user
     */
    String first_name;
    String last_name;
    String user_name;
    String password;
    String bio;
    String major;
    private static int numOfUsers = 2;


    public User(String fname, String lname, String uname, String password) {
        first_name = fname;
        last_name = lname;
        user_name = uname;
        this.password = password;
        numOfUsers++;
    }

    public User() {
        first_name = "";
        last_name = "";
        user_name = "";
        this.password = "";
    }


    /**
     * A bunch of getter and setter methods
     */
    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public int getNumOfUsers() {
        return numOfUsers;
    }

    public String getPassword() {
        return password;
    }

    public void setFirst_name(String fname) {
        first_name = fname;
    }

    public void setLast_name(String lname) {
        last_name = lname;
    }

    public void setUser_name(String uname) {
        user_name = uname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String strongPassword() {
        if(password.length() > 8) {
            return "This password is okay";
        } else {
            return "Please type a stronger password"
        }
    }

}
