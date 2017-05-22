package com.weixin.entiy;

/**
 * Created by laowang on 2017/5/22.
 */
public class LinkMessage extends Base {
    private String title;
    private String description;
    private String Url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
