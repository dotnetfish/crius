package cn.cloudartisan.crius.service.adapter;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import cn.cloudartisan.crius.app.URLConstant;
import cn.cloudartisan.crius.bean.ProductInfo;

public class ProductAdapter implements Adapter<ProductInfo> {
    @Override
    public ProductInfo fromJson(JSONObject jsonObject) throws JSONException {
       // {"sub_title":"","goods_no":"",
        // "stock_quantity":"100",
        // "market_price":"45.00"
        // ,"sell_price":"60.00",
        // "point":"0","order_price":"12.00"},
        // "albums":[],"attach":[],
        // "group_price":[{"id":65,
        // "channel_id":2,"article_id":107,
        // "group_id":1,"price":60.00},
        // {"id":66,"channel_id":2,"article_id":107,"group_id":2,"price":59.40},
        // {"id":67,"channel_id":2,"article_id":107,
        // "group_id":3,"price":58.80
        ProductInfo info=new ProductInfo();
        info.setTitle(jsonObject.getString("title"));

        info.setBrief(jsonObject.getString("zhaiyao"));
        info.setLink((String) jsonObject.get("link_url"));
        //info.setComments(jsonObject.getInteger("comments"));
        info.setImgThumbs(URLConstant.DOMAIN+jsonObject.getString("img_url"));
        info.setId(jsonObject.getInteger("id"));
        info.setPublishTime(jsonObject.getString("add_time"));

        JSONObject fileds=jsonObject.getJSONObject("fields");
        //info.setSale_price(fileds.getDouble("sell_price"));
        if(fileds.getDouble("order_price")!=null) {
            info.setBuy_price(fileds.getDouble("order_price"));
        }
        if(fileds.getDouble("sell_price")!=null) {
            info.setSale_price(fileds.getDouble("sell_price"));
        }
        if(fileds.getDouble("market_price")!=null) {
            info.setMarket_price(fileds.getDouble("market_price"));
        }
        //info.setMarket_price(fileds.getDouble("market_price"));
        info.setNum(fileds.getInteger("stock_quantity"));
        info.setContent(jsonObject.getString("content"));
        return info;
    }
}
