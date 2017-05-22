package com.weixin.entiy;

/**
 * Created by laowang on 2017/5/22.
 */
public class VoiceMessage extends Base {
    private String mediaid;
    private String format;
    private String recognition;//语音识别

    public String getRecognition() {
        return recognition;
    }

    public void setRecognition(String recognition) {
        this.recognition = recognition;
    }

    public String getMediaid() {
        return mediaid;
    }

    public void setMediaid(String mediaid) {
        this.mediaid = mediaid;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
