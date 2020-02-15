package com.example.snake;

public class Item {
    private int imageId;
    private String writer;
    private String text;

    public Item(String name ,String text,int imageId) {
        this.writer = name;
        this.text = text;
        this.imageId = imageId;
    }

    public String getWriter() {
        return writer;
    }

    public int getImageId() {
        return imageId;
    }

    public String getText() {
        return text;
    }

}
