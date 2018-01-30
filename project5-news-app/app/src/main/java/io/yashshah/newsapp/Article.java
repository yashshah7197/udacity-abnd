package io.yashshah.newsapp;

/**
 * Created by yashshah on 17/01/17.
 */

public class Article {

    private String mTitle;

    private String mSection;

    private String mUrl;

    public Article(String title, String section, String url) {
        this.mTitle = title;
        this.mSection = section;
        this.mUrl = url;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getSection() {
        return mSection;
    }

    public String getUrl() {
        return mUrl;
    }
}
