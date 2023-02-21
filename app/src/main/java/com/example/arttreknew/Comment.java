package com.example.arttreknew;



public class Comment{

    private String comments ;
    private String publisher;

    private String fullname;


    public Comment(String comments, String publisher, String fullname){
        this.comments = comments;
        this.publisher = publisher;
        this.fullname = fullname;

    }

    public Comment(){

    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }


}
