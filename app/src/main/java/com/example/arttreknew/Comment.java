package com.example.arttreknew;



public class Comment{

    private String comments ;
    private String publisher;


    public Comment(String comments, String publisher){
        this.comments = comments;
        this.publisher = publisher;

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


}
