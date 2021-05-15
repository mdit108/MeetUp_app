package com.example.android.meetup.Model;

import java.util.ArrayList;

public class User {

    private String id;
    private String username;
    private String email;
    private String imageURL;
    private String status;
    private String search;
    private ArrayList<String> interests;

    public User(String id, String username,String email, String imageURL,ArrayList<String> interests, String status, String search) {
        this.id = id;
        this.username = username;
        this.email= email;
        this.imageURL = imageURL;
        this.status = status;
        this.search = search;
        this.interests = interests;
    }

    public User() {

    }
    public User(User user){
        this.id=user.id;
        this.username =user.username;
        this.email= user.email;
        this.imageURL = user.imageURL;
        this.status = user.status;
        this.search = user.search;
        this.interests = user.interests;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public ArrayList<String> getInterests(){return interests; }

    public void setInterests(ArrayList<String> interests){ this.interests=interests; }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}