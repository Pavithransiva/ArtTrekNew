package com.example.arttreknew;

public class UserGetSet {

    private String email;
    private String fullname;
    private String imageurl;
    private String posts;
    private String title;

    public UserGetSet(String email, String fullname, String imageurl, String posts, String title) {
        this.email = email;
        this.fullname = fullname;
        this.imageurl = imageurl;
        this.posts = posts;
        this.title = title;
    }

    public UserGetSet(){
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getPosts() {
        return posts;
    }

    public void setPosts(String posts) {
        this.posts = posts;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
