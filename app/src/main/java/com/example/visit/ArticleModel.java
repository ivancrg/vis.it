package com.example.visit;

public class ArticleModel {
    private int id;
    private String title;
    private String subtitle;
    private String imageUrl;
    private String articleUrl;
    private String articleType;

    public ArticleModel(int id, String title, String subtitle, String articleUrl, String imageUrl, String articleType) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.imageUrl = imageUrl;
        this.articleUrl = articleUrl;
        this.articleType = articleType;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public String getArticleType() {
        return articleType;
    }
}
