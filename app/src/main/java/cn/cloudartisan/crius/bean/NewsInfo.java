package cn.cloudartisan.crius.bean;

/**
 * Created by kenqu on 2016/1/28.
 */
public class NewsInfo {
    private  String title;

    private  String imgThumbs;

    private  String link;

    private  String brief;

    private  int comments;

    private String publishTime;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgThumbs() {
        return imgThumbs;
    }

    public void setImgThumbs(String imgThumbs) {
        this.imgThumbs = imgThumbs;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }
}
