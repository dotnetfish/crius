/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package cn.cloudartisan.crius.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import java.util.List;

import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.app.Global;
import cn.cloudartisan.crius.app.URLConstant;
import cn.cloudartisan.crius.bean.Bottle;
import cn.cloudartisan.crius.bean.Message;
import cn.cloudartisan.crius.bean.NewsInfo;
import cn.cloudartisan.crius.bean.ProductInfo;
import cn.cloudartisan.crius.bean.User;
import cn.cloudartisan.crius.component.EmoticonsTextView;
import cn.cloudartisan.crius.component.WebImageView;
import cn.cloudartisan.crius.db.MessageDBManager;
import cn.cloudartisan.crius.util.AppTools;
import cn.cloudartisan.crius.util.FileURLBuilder;
import cn.cloudartisan.crius.util.StringUtils;

public class ProductListAdapter extends BaseAdapter {
    protected Context context;
    protected List<ProductInfo> list;
    User user;

    public ProductListAdapter(Context c, List<ProductInfo> list) {
        context = c;
        this.list = list;
        user = Global.getCurrentUser();
    }

    public int getCount() {
        return list.size();
    }

    public ProductInfo getItem(int position) {
        return (ProductInfo)list.get(position);
    }

    public long getItemId(int position) {
         return 0L;
    }

    public View getView(int index, View itemMessageView, ViewGroup parent) {
        ProductListAdapter.ViewHolder viewHolder = null;
        if(itemMessageView == null) {
            itemMessageView = LayoutInflater.from(context).inflate(R.layout.item_product, null);
         viewHolder = new ProductListAdapter.ViewHolder();
            viewHolder.p_num = (TextView)itemMessageView.findViewById(R.id.p_num);
           // viewHolder.status = (ImageView)itemMessageView.findViewById(R.id.ic);
            viewHolder.p_price = (TextView)itemMessageView.findViewById(R.id.p_price);
            viewHolder.p_s_price = (TextView) itemMessageView.findViewById(R.id.p_s_price);
            viewHolder.timeText = (TextView)itemMessageView.findViewById(R.id.timeText);
            viewHolder.icon=(WebImageView)itemMessageView.findViewById(R.id.p_img);
            viewHolder.title=(TextView)itemMessageView.findViewById(R.id.p_name);
            viewHolder.p_sale_count=(TextView)itemMessageView.findViewById(R.id.p_s_count);
            viewHolder.timeText=(TextView)itemMessageView.findViewById(R.id.timeText) ;
            itemMessageView.setTag(viewHolder);
        } else {
            viewHolder =(ProductListAdapter.ViewHolder) itemMessageView.getTag();
        }
        ProductInfo item=getItem(index);
       //Message message = MessageDBManager.getManager().queryLastMessage(getItem(index).getId().toString(), new String[] {"700"});
       // if(user.getAccount().equals(getItem(index).sender)) {
       //     viewHolder.icon.load(FileURLBuilder.getUserIconUrl(getItem(index).receiver), R.drawable.icon_head_default);
       // } else {
        if(getItem(index).getImgThumbs()!=null && StringUtils.isNotEmpty(getItem(index).getImgThumbs())){
            viewHolder.icon.load(URLConstant.DOMAIN+getItem(index).getImgThumbs(), R.drawable.icon_head_default);
        }

       // }
        viewHolder.title.setText(getItem(index).getTitle() == null ? "未标识商品" : getItem(index).getTitle());
       // viewHolder.msgPreview.setEmoticonValign(0);

        viewHolder.p_num.setText(getRes(R.string.stock_quantity,String.valueOf(item.getNum())));
        viewHolder.p_price.setText(getRes(R.string.price,String.valueOf(item.getMarket_price())));
        viewHolder.p_s_price.setText(getRes(R.string.sell_price,String.valueOf(item.getSale_price())));
        viewHolder.p_sale_count.setText(getRes(R.string.sale_out_count,String.valueOf(item.getSales_count())));

        viewHolder.timeText.setText(getItem(index).getPublishTime());
        //viewHolder.timeText.setText(AppTools.howTimeAgo(context, Long.valueOf(getItem(index).getPublishTime()).longValue()));
       // long noReadSum = MessageDBManager.getManager().countNewBySender(getItem(index).getId());
        //if(noReadSum > 0) {
          //  viewHolder.newMsgSumLabel.setVisibility(View.VISIBLE);
           // viewHolder.newMsgSumLabel.setText(noReadSum > 100 ? "99+" : String.valueOf(noReadSum));
        //} else {
          //  viewHolder.newMsgSumLabel.setVisibility(View.GONE);
       // }
        //viewHolder.status.setVisibility(View.GONE);
        //if((message != null) && ("-2".equals(message.status))) {
        //    viewHolder.status.setVisibility(View.VISIBLE);
       //     viewHolder.status.setImageResource(R.drawable.item_msg_state_sending);
       // }
       // if((message != null) && ("-3".equals(message.status))) {
       //     viewHolder.status.setVisibility(View.VISIBLE);
       //     viewHolder.status.setImageResource(R.drawable.item_msg_state_fail);
       // }
        return itemMessageView;
    }

    private String getRes(int resId,String s) {
        return context.getResources().getString(resId)+s;
    }

    public static String getPreviewText(Message message) {
        if ((message != null) && ("1".equals(message.fileType))) {
            return "[图片]";
        }
        if ((message != null) && ("3".equals(message.fileType))) {
            return "[文件]";
        }
        if ((message != null) && ("2".equals(message.fileType))) {
            return "[语音]";
        }
        if ((message != null) && ("4".equals(message.fileType))) {
            return "[地图]";
        }

        if((message != null) && (StringUtils.isEmpty(message.fileType)) || ("0".equals(message.fileType))) {
            try {
                String content = JSON.parseObject(message.content).getString("content");
                if(message.sender.equals(Global.getCurrentUser().getAccount())) {
                    return "我"+message.content;
                }
                return content;
            } catch(Exception localException1) {
                return "";
            }

        }
return "";
    }
    
    class ViewHolder {
        WebImageView icon;
        TextView p_price;
        TextView p_num;
        TextView p_s_price;
        TextView title;
        TextView p_sale_count;
        ImageView status;
        TextView timeText;
    }
}
