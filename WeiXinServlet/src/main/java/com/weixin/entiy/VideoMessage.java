package com.weixin.entiy;

/**
 * Created by laowang on 2017/5/22.
 */
public class VideoMessage extends Base {
    private String mediaId;
    private String ThumbMediaId;

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getThumbMediaId() {
        return ThumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        ThumbMediaId = thumbMediaId;
    }
}
