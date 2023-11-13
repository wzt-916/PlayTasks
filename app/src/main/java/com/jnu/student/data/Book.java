package com.jnu.student.data;

public class Book {

    public String getTitle() {
        return title;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    private final String title;
    private final int imageResourceId;
    public Book(String title_,int imageId_) {
        this.title = title_;
        this.imageResourceId = imageId_;
    }
}
