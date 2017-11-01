package com.example.anando.hciapp.adapters;

/**
 * Created by anando on 02-Nov-17.
 */
public class newsContainer {
    private String headLine;
    private String image;
    private String newsLink;
    public newsContainer(String headLine, String image, String newsLink){
        this.headLine = headLine;
        this.image = image;
        this.newsLink = newsLink;
    }

    public String getHeadLine() {
        return headLine;
    }

    public String getImage() {
        return image;
    }

    public String getNewsLink() {
        return newsLink;
    }
}
