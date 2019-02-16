package cn.cloudartisan.crius.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by kenqu on 2016/1/28.
 */
@DatabaseTable(tableName="t_cirus_products")
public class ProductInfo
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
    @DatabaseField
    private double market_price;
    @DatabaseField
    private double sale_price;
    @DatabaseField
    private double buy_price;
    @DatabaseField
    private int num;
    @DatabaseField
    private  int sales_count;


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

    public double getMarket_price() {
        return market_price;
    }

    public void setMarket_price(double market_price) {
        this.market_price = market_price;
    }

    public double getSale_price() {
        return sale_price;
    }

    public void setSale_price(double sale_price) {
        this.sale_price = sale_price;
    }

    public double getBuy_price() {
        return buy_price;
    }

    public void setBuy_price(double buy_price) {

            this.buy_price = buy_price;

    }

    public int getSales_count() {
        return sales_count;
    }

    public void setSales_count(int sales_count) {
        this.sales_count = sales_count;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
