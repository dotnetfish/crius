package cn.cloudartisan.crius.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.app.Global;
import cn.cloudartisan.crius.bean.NewsInfo;
import cn.cloudartisan.crius.bean.User;
import cn.cloudartisan.crius.component.WebImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenqu on 2016/1/28.
 */
public class NewsListAdapter  extends BaseAdapter {
    protected Context context;
    protected List<NewsInfo> list =new ArrayList<>();
    User self;
    public NewsListAdapter(Context c, List<NewsInfo> list) {
        context = c;
        this.list = list;
        self = Global.getCurrentUser();
    }

    public int getCount() {
        return list.size();
    }

    public NewsInfo getItem(int position) {
        return (NewsInfo)list.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null) {
            view = LayoutInflater.from(this.context).inflate(R.layout.news_item, null);
            viewHolder = new ViewHolder();
            viewHolder.news_comment_count = (TextView)view.findViewById(R.id.news_comment_count);
            viewHolder.news_title = (TextView)view.findViewById(R.id.title);
            viewHolder.news_body = (TextView)view.findViewById(R.id.news_body);
            viewHolder.news_date = (TextView)view.findViewById(R.id.news_date);
            viewHolder.news_thumbs = (WebImageView) view.findViewById(R.id.news_thumbs);
            viewHolder.news_source = (TextView)view.findViewById(R.id.linkTitle);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)view.getTag();
        }
        NewsInfo news=this.getItem(position);
        //viewHolder.logo1.load(news.getlogo());
        //String date=android.text.format.DateFormat.format("yyyy-MM-dd HH:mm",news.getPublishTime()).toString();
        viewHolder.news_date.setText(news.getPublishTime());
        viewHolder.news_body.setText(news.getBrief());
        viewHolder.news_comment_count.setText(String.valueOf(news.getComments()));
        viewHolder.news_title.setText(news.getTitle());
        return  view;

    }
    class ViewHolder {

        TextView news_comment_count ;
        TextView  news_title;
        TextView news_body;
        TextView news_date;
        WebImageView  news_thumbs ;
        TextView news_source ;
        //TextView news_date;
    }
}
