package cn.cloudartisan.crius.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by kenqu on 2016/1/28.
 */
@DatabaseTable(tableName="t_cirus_newsinfo")
public class NewsInfo
        implements Serializable {
    @DatabaseField
    private int id;
    @DatabaseField
    private  String title;
    @DatabaseField
    private  String imgThumbs;
    @DatabaseField
    private  String link;
    @DatabaseField
    private  String brief;
    @DatabaseField
    private  int comments;
    @DatabaseField
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

    public void setComments(Integer comments) {
        if(comments!=null) {
            this.comments = comments;
        }
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
